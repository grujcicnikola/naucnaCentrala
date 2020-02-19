package com.example.scientificCenter.delegate;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;


@Service
public class PrepareIndexing implements JavaDelegate{

	@Autowired
	IdentityService identityService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ScientificAreaService areaService;
	
	@Autowired
	private JournalService journalService;
	
	
	@Autowired
	private PaperService paperService;
	
	@Autowired
	private PaperDocDAO resultRetriever;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Paper paper = this.paperService.findByTitle(execution.getVariable("title").toString());
		paper.setStatus(true);
		this.paperService.save(paper);
	}

	public void indexPaper(Paper paper) {
		ClassLoader classLoader = getClass().getClassLoader();
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
			PaperDoc paperDoc = new PaperDoc();
			paperDoc.setArea(paper.getArea().getName());
			paperDoc.setAuthor(paper.getAuthor().getName() + " " + paper.getAuthor().getSurname());
			paperDoc.setIdPaper(paper.getId());
			paperDoc.setJournaltitle(paper.getJournal().getTitle());
			paperDoc.setKeywords(paper.getKeywords());
			paperDoc.setTitle(paper.getTitle());
			paperDoc.setStatus(paper.getStatus());
			paperDoc.setContent(parsedText);
			//System.out.println(parsedText);
			this.resultRetriever.add(paperDoc);

		}

	}
}