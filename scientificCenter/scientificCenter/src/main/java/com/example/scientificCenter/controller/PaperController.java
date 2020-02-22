package com.example.scientificCenter.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.*;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.scientificCenter.domain.Coauthor;
import com.example.scientificCenter.domain.Comment;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.PDF;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.dto.CommentDTO;
import com.example.scientificCenter.dto.FormFieldsDTO;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.JournalDTO;
import com.example.scientificCenter.dto.PDFDTO;
import com.example.scientificCenter.dto.PDFURL;
import com.example.scientificCenter.dto.PaperDTO;
import com.example.scientificCenter.dto.RecenzentDTO;
import com.example.scientificCenter.dto.ScientificAreaDTO;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.PaperDocRejected;
import com.example.scientificCenter.repository.CommentRepository;
import com.example.scientificCenter.repository.PDFRepository;
import com.example.scientificCenter.repository.RecenzentRepository;
import com.example.scientificCenter.service.CoauthorService;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserRoleService;
import com.example.scientificCenter.service.UserService;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;
import com.example.scientificCenter.serviceInterface.PaperDocRejectedDAO;

@RestController
@RequestMapping("paper")
@CrossOrigin(origins = "https://localhost:4202")
public class PaperController {
	@Autowired
	IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private ScientificAreaService areaService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private CommentRepository commRep;

	@Autowired
	private PaperService paperService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;

	@Autowired
	private RecenzentRepository recRepository;

	@Autowired
	private CoauthorService coauthorService;

	@Value("${camunda.submittingPaperProcessKey}")
	private String submittingPaperProcessKey;

	@Autowired
	private PaperDocDAO resultRetriever;

	@Autowired
	private PaperDocRejectedDAO resultRetrieverRejected;

	@GetMapping("/getPapers")
    public ResponseEntity<?> getPaper() {

	 	System.out.println("get paper");
	 	List<Paper> papers = this.paperService.findAll();
	 	List<PaperDTO> papersDTO = new ArrayList<PaperDTO>();
	 	for(int i =0; i< papers.size(); i++) {
	 		papersDTO.add(new PaperDTO(papers.get(i)));
	 	}
	 	return new ResponseEntity<>(papersDTO, HttpStatus.OK);

    }
	
	@RequestMapping(value = "/create/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRegistrationForm(@PathVariable String email, @RequestBody JournalDTO journalDTO) {
		System.out.println("ZAPOCINJE PROCES Submitting paper");
		Map<String, Object> variables = new HashMap<String, Object>();
		String openAccess = journalDTO.getIsOpenAccess().toString();
		variables.put("openAccess", openAccess);
		variables.put("initiator", email);
		variables.put("issn", journalDTO.getIssn());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(submittingPaperProcessKey, variables);

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		this.taskService.saveTask(task);
		System.out.println("ZAPOCET TASK: " + task.getName() + task.getAssignee());

		return new ResponseEntity<>(new TaskDTO(task.getId(), task.getName(), task.getAssignee()), HttpStatus.OK);
	}

	// commentsRecenzentsToEditor

	@RequestMapping(value = "/commentsRecenzentsToEditor/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCommentsRecenzentsToEditor(@PathVariable String title) {
		Paper paper = this.paperService.findByTitle(title);
		if (paper != null) {
			List<Comment> comments = this.commRep.findAllByPaperId(paper.getId());
			List<CommentDTO> commentsDTO = new ArrayList<CommentDTO>();
			for (int i = 0; i < comments.size(); i++) {
				if (comments.get(i).isRecenzentCommentToEditor()) {
					commentsDTO.add(new CommentDTO(comments.get(i).getComment(), comments.get(i).getDecision()));
				}
			}
			return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping(path = "/getTaskForm/{taskId}", produces = "application/json")
	public @ResponseBody FormFieldsDTO get(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();

		return new FormFieldsDTO(task.getId(), properties, processInstanceId);

		// return new ResponseEntity(dtos, HttpStatus.OK);
	}

	@GetMapping(path = "/findById/{id}", produces = "application/json")
	public ResponseEntity<?> getPaperById(@PathVariable Long id) {
		Optional<Paper> paper = this.paperService.findById(id);
		if (paper.isPresent()) {
			PaperDTO paperDTO = new PaperDTO(paper.get());
			return new ResponseEntity(paperDTO, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/findRecenzentsByPaperId/{id}", produces = "application/json")
	public ResponseEntity<?> findRecenzentsByPaperId(@PathVariable Long id) {
		Optional<Paper> paper = this.paperService.findById(id);
		if (paper.isPresent()) {
			Journal journal = this.journalService.findByIssn(paper.get().getJournal().getIssn());
			List<Recenzent> recenzents = this.journalService.findAllRecenzentsByJournal(journal);
			List<RecenzentDTO> recenzentsDTO = new ArrayList<RecenzentDTO>();
			for (int i = 0; i < recenzents.size(); i++) {
				recenzentsDTO.add(new RecenzentDTO(recenzents.get(i)));
			}
			return new ResponseEntity(recenzentsDTO, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

	}

	// findRecenzentsByScientificArea
	@GetMapping(path = "/findRecenzentsByScientificArea/{id}", produces = "application/json")
	public ResponseEntity<?> findRecenzentsByScientificArea(@PathVariable Long id) {
		Optional<Paper> paper = this.paperService.findById(id);
		if (paper.isPresent()) {
			List<Recenzent> recenzents = filteredByScientificArea(paper.get().getJournal(), paper.get());
			List<RecenzentDTO> recenzentsDTO = new ArrayList<RecenzentDTO>();
			for (int i = 0; i < recenzents.size(); i++) {
				recenzentsDTO.add(new RecenzentDTO(recenzents.get(i)));
			}
			return new ResponseEntity(recenzentsDTO, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

	private List<Recenzent> filteredByScientificArea(Journal journal, Paper paper) {
		// TODO Auto-generated method stub
		ScientificArea sa = paper.getArea();
		System.out.println("casopis " + journal.getIssn());
		System.out.println("paper " + paper.getTitle());
		System.out.println("sa " + sa.getName());
		List<Recenzent> recenzents = this.recRepository.findAll();
		List<Recenzent> recenzentsOfJournal = new ArrayList<Recenzent>();
		for (Recenzent recenzent : recenzents) {
			if (recenzent.getJournal().contains(journal)) {
				recenzentsOfJournal.add(recenzent);
			}
		}
		List<Recenzent> recenzentsOfArea = new ArrayList<Recenzent>();
		for (Recenzent recenzent : recenzentsOfJournal) {
			if (recenzent.getAreas().contains(sa)) {
				recenzentsOfArea.add(recenzent);
			}
		}

		return recenzentsOfArea;
	}

	@GetMapping(path = "/paper/{id}", produces = "application/json")
	public ResponseEntity<?> getPaperURL(@PathVariable Long id) {
		Optional<Paper> paper = this.paperService.findById(id);
		if (paper.isPresent()) {
			PDFURL pdf = new PDFURL(paper.get().getPdf());
			return new ResponseEntity(pdf, HttpStatus.OK);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

	}

	@GetMapping(path = "/choosingReviewersFilteredByScientificArea/{taskId}", produces = "application/json")
	public @ResponseBody FormFieldsDTO getTaskFormChoosingReviewersFiltered(@PathVariable String taskId) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		List<Recenzent> recenzents = filteredByScientificArea(
				runtimeService.getVariable(processInstanceId, "title").toString(),
				runtimeService.getVariable(processInstanceId, "issn").toString());

		if (properties != null) {

			for (FormField field : properties) {
				System.out.println(field.getId());
				if (field.getId().equals("recenzent")) {
					@SuppressWarnings("unchecked")
					HashMap<String, String> items = (HashMap<String, String>) field.getType().getInformation("values");
					items.clear();
					for (Recenzent recenzent : recenzents) {
						items.put(recenzent.getEmail().toString(), recenzent.getName());
					}
				}
			}
		}
		return new FormFieldsDTO(task.getId(), properties, processInstanceId);
	}

	private List<Recenzent> filteredByScientificArea(String title, String issn) {
		// TODO Auto-generated method stub
		Journal journal = this.journalService.findByIssn(issn);
		Paper paper = this.paperService.findByTitle(title);
		ScientificArea sa = paper.getArea();
		System.out.println("casopis " + journal.getIssn());
		System.out.println("paper " + paper.getTitle());
		System.out.println("sa " + sa.getName());
		List<Recenzent> recenzents = this.recRepository.findAll();
		List<Recenzent> recenzentsOfJournal = new ArrayList<Recenzent>();
		for (Recenzent recenzent : recenzents) {
			if (recenzent.getJournal().contains(journal)) {
				recenzentsOfJournal.add(recenzent);
			}
		}
		List<Recenzent> recenzentsOfArea = new ArrayList<Recenzent>();
		for (Recenzent recenzent : recenzentsOfJournal) {
			if (recenzent.getAreas().contains(sa)) {
				recenzentsOfArea.add(recenzent);
			}
		}

		return recenzentsOfArea;
	}

	/*
	 * @RequestMapping(value = "/getPDF", method = RequestMethod.POST) public
	 * ResponseEntity<PDFDTO> getFile(@RequestBody PDFURL link) {
	 * System.out.print("pogodio pdf get "+link.getUrl()); ; byte[] bFile =
	 * readBytesFromFile(link.getUrl()); //byte[] array =
	 * Files.readAllBytes(Paths.get(link.getUrl())); PDFDTO pdf = new PDFDTO(bFile);
	 * return new ResponseEntity<>(pdf, HttpStatus.OK);
	 * 
	 * //return new ResponseEntity<>(null, HttpStatus.OK);
	 * 
	 * }
	 */

	@GetMapping(value = "/index/{id}", produces = "application/json")
	public ResponseEntity<?> index(@PathVariable Long id) {
		Optional<Paper> paper = this.paperService.findById(id);

		if (paper.isPresent()) {
			indexPaper(paper.get());
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/indexRejected/{id}", produces = "application/json")
    public ResponseEntity<?> indexRejected(@PathVariable Long id){
		Optional<Paper> paper = this.paperService.findById(id);
		
		if(paper.isPresent()) {
			PDFTextStripper pdfStripper = null;
			PDDocument pdDoc = null;
			COSDocument cosDoc = null;
			System.out.println(paper.get().getPdf());
			File file = new File(paper.get().getPdf());
			String parsedText = "";
			try {
				// PDFBox 2.0.8 require org.apache.pdfbox.io.RandomAccessRead
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity(  HttpStatus.BAD_REQUEST);
			}
			List<String> authors = new ArrayList<String>();
			authors.add(paper.get().getAuthor().getName()+" "+paper.get().getAuthor().getSurname());
			List<Coauthor> coauthors = this.coauthorService.findAllByPaper(paper.get().getId());
			if(coauthors != null) {
				for(int i =0; i < coauthors.size(); i++) {
					authors.add(coauthors.get(i).getName());
				}
			}
			
			List<Long> recenzentsId = new ArrayList<Long>();
			List<Recenzent> recenzents = this.journalService.findAllRecenzentsByJournal(paper.get().getJournal());
			if(recenzents != null) {
				for(int i =0; i < recenzents.size(); i++) {
					recenzentsId.add(recenzents.get(i).getId());
				}
			}
			PaperDocRejected paperDoc = new PaperDocRejected();
			paperDoc.setArea(paper.get().getArea().getName());
			paperDoc.setAuthors(authors);
			paperDoc.setRecenzentsId(recenzentsId);
			paperDoc.setIdPaper(paper.get().getId());
			paperDoc.setJournaltitle(paper.get().getJournal().getTitle());
			paperDoc.setKeywords(paper.get().getKeywords());
			paperDoc.setTitle(paper.get().getTitle());
			paperDoc.setStatus(paper.get().getStatus());
			paperDoc.setContent(parsedText);
			//System.out.println(parsedText);
			this.resultRetrieverRejected.add(paperDoc);

		}
		return new ResponseEntity(  HttpStatus.OK); 

	}

	public void indexPaper(Paper paper) {
		if (paper != null) {
			PDFTextStripper pdfStripper = null;
			PDDocument pdDoc = null;
			COSDocument cosDoc = null;
			System.out.println(paper.getPdf());
			// URL url;

			// url = new URL(paper.getPdf());

			File file = new File(paper.getPdf());
			String parsedText = "";
			try {
				// PDFBox 2.0.8 require org.apache.pdfbox.io.RandomAccessRead
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			List<String> authors = new ArrayList<String>();
			authors.add(paper.getAuthor().getName()+" "+paper.getAuthor().getSurname());
			List<Coauthor> coauthors = this.coauthorService.findAllByPaper(paper.getId());
			if(coauthors != null) {
				for(int i =0; i < coauthors.size(); i++) {
					authors.add(coauthors.get(i).getName());
				}
			}
			
			List<Long> recenzentsId = new ArrayList<Long>();
			List<Recenzent> recenzents = this.journalService.findAllRecenzentsByJournal(paper.getJournal());
			if(recenzents != null) {
				for(int i =0; i < recenzents.size(); i++) {
					recenzentsId.add(recenzents.get(i).getId());
				}
			}
			PaperDoc paperDoc = new PaperDoc();
			paperDoc.setArea(paper.getArea().getName());
			paperDoc.setAuthors(authors);
			paperDoc.setIdPaper(paper.getId());
			paperDoc.setJournaltitle(paper.getJournal().getTitle());
			paperDoc.setKeywords(paper.getKeywords());
			paperDoc.setTitle(paper.getTitle());
			paperDoc.setStatus(paper.getStatus());
			paperDoc.setContent(parsedText);
			paperDoc.setRecenzentsId(recenzentsId);
			//System.out.println(parsedText);
			this.resultRetriever.add(paperDoc);

		}

	}

	@RequestMapping(value = "/getPDF", method = RequestMethod.POST)
	public ResponseEntity<PDFDTO> getPDF(@RequestBody PDFURL link) {
		System.out.print("pogodio image get " +link.getUrl());
		PDF pdf = new PDF();
		pdf = this.pdfRep.findByName(link.getUrl());
		
		PDFDTO pdfDTO = new PDFDTO(pdf);
		return new ResponseEntity<>(pdfDTO, HttpStatus.OK);

	}

	@PostMapping(path="/download", produces = MediaType.APPLICATION_PDF_VALUE) // //new annotation since 4.3
    public ResponseEntity<?> download(@RequestBody PDFURL link) {
		
		System.out.print("pogodio image get " +link.getUrl());
		PDF pdf = new PDF();
		//pdf = this.pdfRep.findByName(link.getUrl());
		
		byte[] bFile = readBytesFromFile(link.getUrl());
		PDFDTO pdfDTO = new PDFDTO(pdf);
		String fileName = "employees.pdf";
//		HttpHeaders respHeaders = new HttpHeaders();
//		respHeaders.setContentLength(bytes.length);
//		respHeaders.setContentType(new MediaType("application", "pdf"));
//		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
////		return new ResponseEntity<byte[]>(bytes, respHeaders, HttpStatus.OK);
//		return new ResponseEntity<>(bytes, respHeaders, HttpStatus.OK);
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(bFile);

    }

	@RequestMapping(value = "/uploadPDF/", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("File") MultipartFile request) {
		System.out.print("pogodio pdf");
		String returnValue ="";
		try {
			returnValue = saveImage(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(returnValue, HttpStatus.OK);

	}

	@Autowired
	private PDFRepository pdfRep;

	public String saveImage(MultipartFile file) throws IOException {
		String folder = "files/";
		byte[] bytes = file.getBytes();
		Path path = Paths.get(folder + file.getOriginalFilename());
		System.out.println(path.toAbsolutePath());
		Files.write(path, bytes);
		
		
		
		Path path1 = Paths.get(folder + file.getOriginalFilename());
		System.out.println(path1.toAbsolutePath());
		ClassPathResource backImgFile = new ClassPathResource(path1.toAbsolutePath().toString());
		byte[] arrayPic = file.getBytes();
		// backImgFile.getInputStream().read(arrayPic);
		PDF blackImage = new PDF(path1.toAbsolutePath().toString(), arrayPic);
		
		//PDF saved=pdfRep.save(blackImage);
		return path.toAbsolutePath().toString();
	}

	private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;

		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return bytesArray;

	}

}