package com.example.scientificCenter.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.dto.RecenzentDTO;
import com.example.scientificCenter.elastic.dto.BooleanQueryDTO;
import com.example.scientificCenter.elastic.dto.CombineQueryDTO;
import com.example.scientificCenter.elastic.dto.ResponsePaperDTO;
import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.RecenzentDoc;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.UserService;
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

	@Autowired
	private PaperService paperService;
	
	@Autowired
	private UserService userService;

	private HighlightBuilder highlightBuilder = new HighlightBuilder().field("title", 50).field("journaltitle", 50)
			.field("author", 50).field("keywords", 50).field("area", 50).field("content", 50);

	public static final String INDEX_NAME_PAPER = "paperlibrary";
	public static final String TYPE_NAME_PAPER = "paper";
	
	public static final String INDEX_NAME_PAPER_REJECTED = "paperlibraryrejected";
	public static final String TYPE_NAME_PAPER_REJECTED = "paperrejected";

	@PostMapping(value = "/searchQuery", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity advancedQuery(@RequestBody CombineQueryDTO queryDTO) {
		if (queryDTO.getBooleanQueryies().size() == 1) {// basic search

			ArrayList<ResponsePaperDTO> retVal = new ArrayList<>();
			highlightBuilder
					.highlightQuery(QueryBuilders.queryStringQuery(queryDTO.getBooleanQueryies().get(0).getQuery()));
			if (queryDTO.getBooleanQueryies().get(0).getIsPhraze() != null
					&& queryDTO.getBooleanQueryies().get(0).getIsPhraze() == true) {
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
			} else {
				SearchRequestBuilder request = nodeClient.prepareSearch().setIndices(INDEX_NAME_PAPER)
						.setTypes(TYPE_NAME_PAPER)
						.setQuery(QueryBuilders.matchQuery(queryDTO.getBooleanQueryies().get(0).getArea(),
								queryDTO.getBooleanQueryies().get(0).getQuery()).analyzer("serbian"))
						.setSearchType(SearchType.DEFAULT).highlighter(highlightBuilder);
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

					if (dto.getIsPhraze() != null && dto.getIsPhraze() == true) {
						if (dto.getArea().equals("everything")) {
							booleanQuery.must(QueryBuilders.multiMatchQuery(dto.getQuery(), "title", "journaltitle",
									"author", "keywords", "area", "content").type("phrase").analyzer("serbian"));
						} else {
							booleanQuery.must(
									QueryBuilders.matchPhraseQuery(dto.getArea(), dto.getQuery()).analyzer("serbian"));
						}
					} else {
						if (dto.getArea().equals("everything")) {
							booleanQuery.must(QueryBuilders.multiMatchQuery(dto.getQuery(), "title", "journaltitle",
									"author", "keywords", "area", "content").analyzer("serbian"));
						} else {
							booleanQuery
									.must(QueryBuilders.matchQuery(dto.getArea(), dto.getQuery()).analyzer("serbian"));
						}
					}

				} else if (dto.getOperator().equals("OR")) {
					if (dto.getIsPhraze() != null && dto.getIsPhraze() == true) {
						if (dto.getArea().equals("everything")) {
							booleanQuery.should(QueryBuilders.multiMatchQuery(dto.getQuery(), "title", "journaltitle",
									"author", "keywords", "area", "content").type("phrase").analyzer("serbian"));
						} else {
							booleanQuery.should(
									QueryBuilders.matchPhraseQuery(dto.getArea().toLowerCase(), dto.getQuery()));
						}
					} else {
						if (dto.getArea().equals("everything")) {
							booleanQuery.should(QueryBuilders.multiMatchQuery(dto.getQuery(), "title", "journaltitle",
									"author", "keywords", "area", "content").analyzer("serbian"));
						} else {
							booleanQuery.should(QueryBuilders.matchQuery(dto.getArea().toLowerCase(), dto.getQuery())
									.analyzer("serbian"));
						}

					}
				}

			}
			highlightBuilder.highlightQuery(booleanQuery);
			SearchRequestBuilder request = nodeClient.prepareSearch().setIndices(INDEX_NAME_PAPER)
					.setTypes(TYPE_NAME_PAPER).setQuery(booleanQuery).setSearchType(SearchType.DEFAULT)
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

	@GetMapping(path = "/moreLikeThis/{id}", produces = "application/json")
	public ResponseEntity<?> findRecenzentsByScientificArea(@PathVariable Long id) {
		Optional<Paper> paper = this.paperService.findById(id);
		if (paper.isPresent()) {
			String[] likeThis = new String[1];
			likeThis[0] = this.getContent(paper.get());
			String[] fields = new String[1];
			fields[0] = "content";
			MoreLikeThisQueryBuilder query = new MoreLikeThisQueryBuilder(fields, likeThis, null);
			query.maxQueryTerms(12);
			query.minTermFreq(1);
			query.minimumShouldMatch("65%");
			query.minDocFreq(1);
			query.analyzer("serbian");
			
			SearchRequestBuilder request = nodeClient.prepareSearch().setIndices(INDEX_NAME_PAPER)
	                .setTypes(TYPE_NAME_PAPER)
	                .setQuery(query)
	                .setSearchType(SearchType.DEFAULT);
	        request.setFetchSource(null, "content");;
			System.out.println("REQUEST");
			System.out.println(request);
			SearchResponse response = request.get();
	        System.out.println(response.toString());
	        Set<Long> recenzents= new HashSet<Long>();
	        for (SearchHit hit : response.getHits().getHits()) {
				Gson gson = new Gson();
				PaperDoc object = gson.fromJson(hit.getSourceAsString(), PaperDoc.class);
				recenzents.addAll(object.getRecenzentsId());
	        }
	        
	        SearchRequestBuilder requestRejected = nodeClient.prepareSearch().setIndices(INDEX_NAME_PAPER_REJECTED)
	                .setTypes(TYPE_NAME_PAPER_REJECTED)
	                .setQuery(query)
	                .setSearchType(SearchType.DEFAULT);
	        request.setFetchSource(null, "content");;
			SearchResponse responseRejected = requestRejected.get();
	        System.out.println(responseRejected.toString());
	        for (SearchHit hit : responseRejected.getHits().getHits()) {
				Gson gson = new Gson();
				PaperDoc object = gson.fromJson(hit.getSourceAsString(), PaperDoc.class);
				recenzents.addAll(object.getRecenzentsId());
	        }
	        List<RecenzentDTO> recenzentsDTO = new ArrayList<RecenzentDTO>();
	        for(Long recenzent :recenzents) {
	        	recenzentsDTO.add(new RecenzentDTO(this.userService.findRecenzentById(recenzent)));
	        }
	        
	        
	        return new ResponseEntity(recenzentsDTO, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}
	/*
	 * String[] likeThis = new String[1]; likeThis[0]=getSadrzaj(r);
	 * System.out.println(likeThis[0]); String[] fields = new String[1]; fields[0] =
	 * "sadrzaj"; MoreLikeThisQueryBuilder mltqb=new
	 * MoreLikeThisQueryBuilder(fields, likeThis,null ); mltqb.maxQueryTerms(10);
	 * mltqb.minTermFreq(1); mltqb.minimumShouldMatch("65%"); mltqb.minDocFreq(1);
	 * mltqb.analyzer("serbian-analyzer"); bqb.must(mltqb); SearchRequestBuilder
	 * request = nodeClient.prepareSearch("naucnicasopis_recenzija")
	 * .setTypes("recenzija") .setQuery(bqb) .setSearchType(SearchType.DEFAULT);
	 * request.setFetchSource(null, "sadrzaj"); System.out.println(request);
	 * SearchResponse response = request.get();
	 * System.out.println(response.toString()); for(SearchHit hit :
	 * response.getHits().getHits()) { Gson gson = new Gson(); RecenzentIndexUnit
	 * rec =
	 * recenzentIndexUnitRepository.findById(gson.fromJson(hit.getSourceAsString(),
	 * RecenzijaIndexUnit.class).getIdRecenzenta()).get();
	 * if(!retval.stream().map(RecenzentIndexUnit::getId).filter(rec.getId()::equals
	 * ).findFirst().isPresent()) retval.add(rec); } System.out.println(retval);
	 */

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

	private String getContent(Paper paper) {

		if (paper != null) {
			PDFTextStripper pdfStripper = null;
			PDDocument pdDoc = null;
			COSDocument cosDoc = null;
			System.out.println(paper.getPdf());
			File file = new File(paper.getPdf());
			String parsedText = "";
			try {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
				PDFParser parser = new PDFParser(randomAccessFile);
				parser.parse();
				cosDoc = parser.getDocument();
				pdfStripper = new PDFTextStripper();
				pdDoc = new PDDocument(cosDoc);
				pdfStripper.setStartPage(1);
				pdfStripper.setEndPage(5);
				parsedText = pdfStripper.getText(pdDoc);
				pdDoc.close();
				return parsedText;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

}
