package com.example.scientificCenter.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.PDF;
import com.example.scientificCenter.dto.FormSubmissionDTO;
import com.example.scientificCenter.dto.JournalDTO;
import com.example.scientificCenter.dto.PDFDTO;
import com.example.scientificCenter.dto.PDFURL;
import com.example.scientificCenter.dto.TaskDTO;
import com.example.scientificCenter.repository.PDFRepository;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserRoleService;
import com.example.scientificCenter.service.UserService;


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
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Value("${camunda.submittingPaperProcessKey}")
	private String submittingPaperProcessKey;
	
	@RequestMapping(value = "/create/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRegistrationForm(@PathVariable String email,@RequestBody JournalDTO journalDTO)  {
		System.out.println("ZAPOCINJE PROCES Submitting paper");
		Map<String, Object> variables = new HashMap<String, Object>();
		String openAccess = journalDTO.getIsOpenAccess().toString();
		variables.put("openAccess",openAccess);
		variables.put("initiator", email);
		variables.put("issn", journalDTO.getIssn());
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(submittingPaperProcessKey,variables);
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		this.taskService.saveTask(task);
		System.out.println("ZAPOCET TASK: " + task.getName()+ task.getAssignee() );
		
		return new ResponseEntity<>(new TaskDTO(task.getId(), task.getName(),task.getAssignee()), HttpStatus.OK);
	}
	/*
	@RequestMapping(value = "/getPDF", method = RequestMethod.POST)
	public ResponseEntity<PDFDTO> getFile(@RequestBody PDFURL link) {
		System.out.print("pogodio pdf get "+link.getUrl());
		;
		byte[] bFile = readBytesFromFile(link.getUrl());
		//byte[] array = Files.readAllBytes(Paths.get(link.getUrl()));
		PDFDTO pdf = new PDFDTO(bFile);
		return new ResponseEntity<>(pdf, HttpStatus.OK);
		
		//return new ResponseEntity<>(null, HttpStatus.OK);

	}*/

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

            //read file into bytes[]
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