package com.example.scientificCenter.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.elastic.dto.BooleanQueryDTO;
import com.example.scientificCenter.elastic.dto.CombineQueryDTO;
import com.example.scientificCenter.elastic.dto.ResponsePaperDTO;
import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.RecenzentDoc;
import com.example.scientificCenter.serviceInterface.RecenzentDocDAO;
import com.google.gson.Gson;

@RestController
@RequestMapping("search")
@CrossOrigin(origins = "https://localhost:4202")
public class SearchController {

	@Autowired
	private RecenzentDocDAO resultRetriever;

	// private Client client = new
	// PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new
	// TransportAddress(InetAddress.getByName("localhost"), 9300));//new
	// RestHighLevelClient().addTransportAddress(new
	// TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
	@Autowired
	public Client nodeClient;

	private HighlightBuilder highlightBuilder = new HighlightBuilder().field("title", 50).field("journaltitle", 50)
			.field("author", 50).field("keywords", 50).field("area", 50).field("content", 50);

	public static final String INDEX_NAME_PAPER = "paperlibrary";
	public static final String TYPE_NAME_PAPER = "paper";

	@PostMapping(value = "/searchQuery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity advancedQuery(@RequestBody CombineQueryDTO queryDTO) {
		if (queryDTO.getBooleanQueryies().size() == 1) {// basic search

			ArrayList<ResponsePaperDTO> retVal = new ArrayList<>();
			highlightBuilder
					.highlightQuery(QueryBuilders.queryStringQuery(queryDTO.getBooleanQueryies().get(0).getQuery()));
			if(queryDTO.getBooleanQueryies().get(0).getIsPhraze()!=null && queryDTO.getBooleanQueryies().get(0).getIsPhraze()==true) {
				SearchRequestBuilder request = nodeClient.prepareSearch().setIndices(INDEX_NAME_PAPER)
						.setTypes(TYPE_NAME_PAPER)
						.setQuery(QueryBuilders.matchPhraseQuery(queryDTO.getBooleanQueryies().get(0).getArea(),
								queryDTO.getBooleanQueryies().get(0).getQuery()).analyzer("serbian"))
						.setSearchType(SearchType.DEFAULT).highlighter(highlightBuilder);
				SearchResponse response = request.get();
				// System.out.println(response.toString());
				retVal = getResponse(response);
				System.out.println("query: " + QueryBuilders.matchQuery(queryDTO.getBooleanQueryies().get(0).getArea(),
						queryDTO.getBooleanQueryies().get(0).getQuery()));
			}else {
				SearchRequestBuilder request = nodeClient.prepareSearch().setIndices(INDEX_NAME_PAPER)
						.setTypes(TYPE_NAME_PAPER)
						.setQuery(QueryBuilders.matchQuery(queryDTO.getBooleanQueryies().get(0).getArea(),
								queryDTO.getBooleanQueryies().get(0).getQuery()).analyzer("serbian"))
						.setSearchType(SearchType.DEFAULT)
						.highlighter(highlightBuilder);
				SearchResponse response = request.get();
				// System.out.println(response.toString());
				retVal = getResponse(response);
				System.out.println("query: " + QueryBuilders.matchQuery(queryDTO.getBooleanQueryies().get(0).getArea(),
						queryDTO.getBooleanQueryies().get(0).getQuery()));
			}
			
			return new ResponseEntity(retVal, HttpStatus.OK);
		} else {
			List<BooleanQueryDTO> query = queryDTO.getBooleanQueryies();
			ArrayList<ResponsePaperDTO> retVal = new ArrayList<>();

			BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();

			for (BooleanQueryDTO dto : query) {
				if (dto.getOperator().equals("AND")) {

					if (dto.getIsPhraze()!=null && dto.getIsPhraze()==true) {
						if (dto.getArea().equals("everything")) { 
							booleanQuery.must(
									QueryBuilders
											.multiMatchQuery(dto.getQuery(), "title", "journaltitle", "author", "keywords",
													"area", "content")
											.type("phrase").analyzer("serbian"));
						}else { 
							booleanQuery.must(QueryBuilders.matchPhraseQuery(dto.getArea(), dto.getQuery()).analyzer("serbian"));
						}				
					} else { 
						if (dto.getArea().equals("everything")) {
							booleanQuery.must(QueryBuilders.multiMatchQuery(dto.getQuery(), "title", "journaltitle", "author", "keywords",
									"area", "content").analyzer("serbian"));
						}else { 
							booleanQuery.must(QueryBuilders.matchQuery(dto.getArea(), dto.getQuery()).analyzer("serbian"));
						}
					}

				} else if (dto.getOperator().equals("OR")) {
					if (dto.getIsPhraze()!=null && dto.getIsPhraze()==true) {
						if (dto.getArea().equals("everything")) {
							booleanQuery.should(
									QueryBuilders
											.multiMatchQuery(dto.getQuery(),  "title", "journaltitle", "author", "keywords",
													"area", "content")
											.type("phrase").analyzer("serbian"));
						}else {
							booleanQuery.should(QueryBuilders.matchPhraseQuery(dto.getArea().toLowerCase(), dto.getQuery()));
						}
					} else {
						if (dto.getArea().equals("everything")) { 
							booleanQuery.should(QueryBuilders.multiMatchQuery(dto.getQuery(),  "title", "journaltitle", "author", "keywords",
									"area", "content").analyzer("serbian"));
						}else {
							booleanQuery.should(QueryBuilders.matchQuery(dto.getArea().toLowerCase(), dto.getQuery()).analyzer("serbian"));
						}

					}
				}
				
			}
			highlightBuilder.highlightQuery(booleanQuery);
			SearchRequestBuilder request = nodeClient.prepareSearch()
					.setIndices(INDEX_NAME_PAPER)
					.setTypes(TYPE_NAME_PAPER)
					.setQuery(booleanQuery)
					.setSearchType(SearchType.DEFAULT)
					.highlighter(highlightBuilder);
			System.out.println("BQB");
	        System.out.println(booleanQuery);
	        System.out.println("REQUEST");
	        System.out.println(request);
			SearchResponse response = request.get();
			// System.out.println(response.toString());
			retVal = getResponse(response);
			
			

			return new ResponseEntity(retVal, HttpStatus.OK);
		}

	}

	private ArrayList<ResponsePaperDTO> getResponse(SearchResponse response) {
		ArrayList<ResponsePaperDTO> retVal = new ArrayList<>();
		for (SearchHit hit : response.getHits().getHits()) {
			Gson gson = new Gson();
			ResponsePaperDTO basicQueryResponseDTO = new ResponsePaperDTO();
			PaperDoc object = gson.fromJson(hit.getSourceAsString(), PaperDoc.class);
			basicQueryResponseDTO.setPaper(object);

			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			String highlights = "...";
			for (Map.Entry<String, HighlightField> entry : highlightFields.entrySet()) {
				String value = Arrays.toString(entry.getValue().fragments());
				highlights += value.substring(1, value.length() - 1);
				highlights += "...";

			}

			highlights = highlights.replace("<em>", "<b>");
			highlights = highlights.replace("</em>", "</b>");
			basicQueryResponseDTO.setHighlights(highlights);
			retVal.add(basicQueryResponseDTO);
		}
		return retVal;
	}

}
