package com.example.scientificCenter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.service.JournalService;
import com.example.scientificCenter.service.PaperService;
import com.example.scientificCenter.service.ScientificAreaService;
import com.example.scientificCenter.service.UserService;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;

@Component
public class StartData {

	@Autowired
	private IdentityService identityService;

	private final static String RECENZENT_GROUP_ID = "recenzent";

	private final static String AUTHOR_GROUP_ID = "author";

	private final static String EDITOR_GROUP_ID = "editor";

	@Autowired
	private Client nodeClient;

	@Autowired
	private PaperDocDAO resultRetriever;

	@Autowired
	private UserService userService;

	@Autowired
	private ScientificAreaService areaService;

	@Autowired
	private JournalService journalService;

	@Autowired
	private PaperService paperService;

	@PostConstruct
	private void init() throws Exception {
		
		Optional<com.example.scientificCenter.domain.User> author1 = this.userService.getByEmail("author1@gmail.com");
		Optional<ScientificArea> area1 = this.areaService.findById(Long.parseLong("3"));
		Journal journal1 = this.journalService.findByIssn("1111-2222");
		Paper paper1 = new Paper("Astronomija", (Author) author1.get(),
				"Astronomija je nauka koja proučava objekte i pojave izvan Zemlje i njene\r\n" + "atmosfere.",
				area1.get(), true,
				"D://naucnacentrala-upp-novi//naucnaCentrala//scientificCenter//scientificCenter//files//astronomija.pdf",
				"astronomija, zvezde", journal1, "");
		Paper paper1Saved = this.paperService.save(paper1);
		indexPaper(paper1Saved);
		
		Optional<com.example.scientificCenter.domain.User> author2 = this.userService.getByEmail("author2@gmail.com");
		Optional<ScientificArea> area2 = this.areaService.findById(Long.parseLong("1"));
		Journal journal2 = this.journalService.findByIssn("2222-2222");
		Paper paper2 = new Paper("Čokolada", (Author) author2.get(),
				"Ta namirnica se dobija od prepečenih zrna kakaoa",
				area2.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\cokolada.pdf",
				"čokolada, slatkiši", journal2, "");
		Paper paper1Saved2 = this.paperService.save(paper2);
		indexPaper(paper1Saved2);
		
		Optional<com.example.scientificCenter.domain.User> author3 = this.userService.getByEmail("author2@gmail.com");
		Optional<ScientificArea> area3 = this.areaService.findById(Long.parseLong("1"));
		Journal journal3 = this.journalService.findByIssn("2222-2222");
		Paper paper3 = new Paper("Crna čokolada", (Author) author3.get(),
				"Crna čokolada ima najmanje 35 % kakaa zbog čega se i zove crna. ",
				area3.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\crnacokolada.pdf",
				"čokolada, slatkiši", journal3, "");
		Paper paper1Saved3 = this.paperService.save(paper3);
		indexPaper(paper1Saved3);
		
		
		Optional<com.example.scientificCenter.domain.User> author4 = this.userService.getByEmail("author1@gmail.com");
		Optional<ScientificArea> area4 = this.areaService.findById(Long.parseLong("3"));
		Journal journal4 = this.journalService.findByIssn("1111-2222");
		Paper paper4 = new Paper("Crna rupa", (Author) author4.get(),
				"Crna rupa je nebesko telo.",
				area4.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\crnarupa.pdf",
				"čokolada, slatkiši", journal4, "");
		Paper paper1Saved4 = this.paperService.save(paper4);
		indexPaper(paper1Saved4);
		
		Optional<com.example.scientificCenter.domain.User> author5 = this.userService.getByEmail("author1@gmail.com");
		Optional<ScientificArea> area5 = this.areaService.findById(Long.parseLong("1"));
		Journal journal5 = this.journalService.findByIssn("2222-2222");
		Paper paper5 = new Paper("Čuda prirode", (Author) author5.get(),
				"Postoje mnogo čuda prirode.",
				area5.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\cudaPrirode.pdf",
				"čokolada, slatkiši", journal5, "");
		Paper paper1Saved5 = this.paperService.save(paper5);
		indexPaper(paper1Saved5);
		
		Optional<com.example.scientificCenter.domain.User> author6 = this.userService.getByEmail("author1@gmail.com");
		Optional<ScientificArea> area6 = this.areaService.findById(Long.parseLong("1"));
		Journal journal6 = this.journalService.findByIssn("2222-2222");
		Paper paper6 = new Paper("Čuovište iz Loh Nesa", (Author) author5.get(),
				"Postoje mnogo čudovišta.",
				area6.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\cudoviste.pdf",
				"čudovište, Loh Nes", journal6, "");
		Paper paper1Saved6 = this.paperService.save(paper6);
		indexPaper(paper1Saved6);
		
		Optional<com.example.scientificCenter.domain.User> author7 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area7 = this.areaService.findById(Long.parseLong("3"));
		Journal journal7 = this.journalService.findByIssn("3333-2222");
		Paper paper7 = new Paper("Fizika", (Author) author7.get(),
				"Fizika je prirodna nauka koja proučava materiju",
				area7.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\fizika.pdf",
				"fizika, materija", journal7, "");
		Paper paper1Saved7 = this.paperService.save(paper7);
		indexPaper(paper1Saved7);
		
		Optional<com.example.scientificCenter.domain.User> author8 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area8 = this.areaService.findById(Long.parseLong("3"));
		Journal journal8 = this.journalService.findByIssn("1111-2222");
		Paper paper8 = new Paper("Galaksije", (Author) author8.get(),
				"Галаксија је велики скуп од више стотина хиљада или милијарди звезда у свемиру",
				area7.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\galaksije.pdf",
				"fizika, galaksije", journal8, "");
		Paper paper1Saved8 = this.paperService.save(paper8);
		indexPaper(paper1Saved8);
		
		Optional<com.example.scientificCenter.domain.User> author9 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area9 = this.areaService.findById(Long.parseLong("3"));
		Journal journal9 = this.journalService.findByIssn("1111-2222");
		Paper paper9 = new Paper("Komete", (Author) author9.get(),
				"Комете су мали, деформисани објекти пречника неколико километара",
				area9.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\kometa.pdf",
				"fizika, komete", journal9, "");
		Paper paper1Saved9= this.paperService.save(paper9);
		indexPaper(paper1Saved9);
		
		Optional<com.example.scientificCenter.domain.User> author10 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area10 = this.areaService.findById(Long.parseLong("3"));
		Journal journal10 = this.journalService.findByIssn("3333-2222");
		Paper paper10 = new Paper("Mehanicka energija", (Author) author10.get(),
				"Telo raspolaže energijom ukoliko je sposobno da vrši rad",
				area10.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\mehanickaenergija.pdf",
				"fizika, energija", journal10, "");
		Paper paper1Saved10 = this.paperService.save(paper10);
		indexPaper(paper1Saved10);
		
		Optional<com.example.scientificCenter.domain.User> author11 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area11 = this.areaService.findById(Long.parseLong("1"));
		Journal journal11= this.journalService.findByIssn("1111-2222");
		Paper paper11 = new Paper("Mesec", (Author) author11.get(),
				"Mesec je jedini Zemljin prirodni satelit i ujedno najbliže nebesko telo.",
				area11.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\mesec.pdf",
				"zvezde, mesec", journal11, "");
		Paper paper1Saved11 = this.paperService.save(paper11);
		indexPaper(paper1Saved11);
		
		Optional<com.example.scientificCenter.domain.User> author12 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area12 = this.areaService.findById(Long.parseLong("1"));
		Journal journal12 = this.journalService.findByIssn("1111-2222");
		Paper paper12 = new Paper("Mesečeve mene", (Author) author12.get(),
				"Mesečeve mene (ili Mesečeve faze) jesu različiti oblici Meseca koji se vide sa\r\n" + 
				"Zemlje",
				area12.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\mesecevemene.pdf",
				"fizika, mesec ,mena", journal12, "");
		Paper paper1Saved12 = this.paperService.save(paper12);
		indexPaper(paper1Saved12);
		
		Optional<com.example.scientificCenter.domain.User> author13 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area13 = this.areaService.findById(Long.parseLong("1"));
		Journal journal13 = this.journalService.findByIssn("3333-2222");
		Paper paper13 = new Paper("Crna, mlečna ili bela? Koja čokolada je najbolja za\r\n" + 
				"vaše zdravlje?\r\n" + 
				"", (Author) author13.get(),
				"Čokolada je dobra za protok krvi , što znači da je dobra za srce ",
				area13.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\mlecnaCokolada.pdf",
				"mleko, čokolada", journal13, "");
		Paper paper1Saved13 = this.paperService.save(paper13);
		indexPaper(paper1Saved13);
		
		Optional<com.example.scientificCenter.domain.User> author14 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area14 = this.areaService.findById(Long.parseLong("3"));
		Journal journal14 = this.journalService.findByIssn("1111-2222");
		Paper paper14 = new Paper("Mlečni put", (Author) author14.get(),
				"Наша галаксија, названа Млечни пут зато што личи на млаз млека на ноћном небу",
				area14.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\mlecniput.pdf",
				"fizika, zvezde, galaksije", journal14, "");
		Paper paper1Saved14 = this.paperService.save(paper14);
		indexPaper(paper1Saved14);
		
		Optional<com.example.scientificCenter.domain.User> author15 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area15 = this.areaService.findById(Long.parseLong("3"));
		Journal journal15 = this.journalService.findByIssn("3333-2222");
		Paper paper15 = new Paper("Okean", (Author) author15.get(),
				"Okean je ogromna jedinstvena masa slane vode",
				area15.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\okean.pdf",
				"more, okean", journal15, "");
		Paper paper1Saved15 = this.paperService.save(paper15);
		indexPaper(paper1Saved15);
		
		Optional<com.example.scientificCenter.domain.User> author16 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area16 = this.areaService.findById(Long.parseLong("1"));
		Journal journal16 = this.journalService.findByIssn("2222-2222");
		Paper paper16 = new Paper("Postojeće stanje šuma u Vojvodini", (Author) author16.get(),
				"Ukupna površina šuma i šumskog zemljišta iznosi 175.136,05 ha ",
				area16.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\sumevojvodine.pdf",
				"vojvodina, šume", journal16, "");
		Paper paper1Saved16 = this.paperService.save(paper16);
		indexPaper(paper1Saved16);
		
		Optional<com.example.scientificCenter.domain.User> author17 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area17= this.areaService.findById(Long.parseLong("1"));
		Journal journal17 = this.journalService.findByIssn("3333-2222");
		Paper paper17 = new Paper("Kako je Tihi okean dobio ime?", (Author) author17.get(),
				"Kako je Tihi okean dobio ime?",
				area17.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\tihiokean.pdf",
				"more, okean", journal17, "");
		Paper paper1Saved17 = this.paperService.save(paper17);
		indexPaper(paper1Saved17);
		
		Optional<com.example.scientificCenter.domain.User> author18 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area18 = this.areaService.findById(Long.parseLong("1"));
		Journal journal18 = this.journalService.findByIssn("2222-2222");
		Paper paper18 = new Paper("Voda", (Author) author18.get(),
				"Voda je hemijsko jedinjenje kiseonika i vodonika",
				area18.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\voda.pdf",
				"more, voda", journal18, "");
		Paper paper1Saved18 = this.paperService.save(paper18);
		indexPaper(paper1Saved18);
		
		Optional<com.example.scientificCenter.domain.User> author19 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area19 = this.areaService.findById(Long.parseLong("1"));
		Journal journal19 = this.journalService.findByIssn("2222-2222");
		Paper paper19 = new Paper("Vojvodina šume", (Author) author19.get(),
				"Vojvodina iz Fonda za šume dodeljuje 153 miliona dinara za pošumljavanje",
				area19.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\vojvodinasume.pdf",
				"šume, vojvodina", journal19, "");
		Paper paper1Saved19 = this.paperService.save(paper19);
		indexPaper(paper1Saved19);
		
		Optional<com.example.scientificCenter.domain.User> author20 = this.userService.getByEmail("author4@gmail.com");
		Optional<ScientificArea> area20 = this.areaService.findById(Long.parseLong("1"));
		Journal journal20 = this.journalService.findByIssn("2222-2222");
		Paper paper20 = new Paper("POJAM ŽIVOTNE SREDINE", (Author) author20.get(),
				"Životna sredina jeste skup prirodnih i stvorenih vrijednosti čiji kompleksni međusobni odnosi čine okruženje",
				area20.get(), true,
				"D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\zivotnaSredina.pdf",
				"život, okruženje", journal20, "");
		Paper paper1Saved20 = this.paperService.save(paper20);
		indexPaper(paper1Saved20);
		
		// trenutno nepotrebno
		/*
		 * saveCamundaUser("petarperic23252@gmail.com", "petar", "pera++5++", "peric",
		 * "petarperic"); saveCamundaUser("nikolagrujcic@gmail.com", "nikola",
		 * "nikola++5++", "grujcic", "nikolagrujcic"); saveCamundaUser("dana@gmail.com",
		 * "danica", "dana++5++", "markovic", "danicamarkovic");
		 * saveCamundaUser("ivana@gmail.com", "ivana", "ivana++5++", "blagojevic",
		 * "ivanablagojevic"); saveCamundaUser("igrujcic@gmail.com", "ivana",
		 * "ivana++5++", "grujcic", "ivanagrujcic"); saveCamundaUser("mira@gmail.com",
		 * "mira", "mira++5++", "grujcic", "miragrujcic");
		 * saveCamundaUser("ljuba@gmail.com", "ljuba", "ljuba++5++", "grujcic",
		 * "ljubagrujcic");
		 * 
		 * Group authorGroup = identityService.newGroup(AUTHOR_GROUP_ID);
		 * authorGroup.setName("Author"); authorGroup.setType(""); Group group =
		 * identityService.createGroupQuery().groupId(AUTHOR_GROUP_ID).singleResult();
		 * if(group != null) { identityService.saveGroup(authorGroup); }
		 * 
		 * Group rGroup = identityService.newGroup(RECENZENT_GROUP_ID);
		 * rGroup.setName("Author"); rGroup.setType(""); Group group1 =
		 * identityService.createGroupQuery().groupId(RECENZENT_GROUP_ID).singleResult()
		 * ; if(group1 != null) { identityService.saveGroup(rGroup); }
		 * 
		 * Group eGroup = identityService.newGroup(EDITOR_GROUP_ID);
		 * eGroup.setName("Author"); eGroup.setType(""); Group group2 =
		 * identityService.createGroupQuery().groupId(EDITOR_GROUP_ID).singleResult();
		 * if(group2 != null) { identityService.saveGroup(eGroup); }
		 */
	}

	public void saveCamundaUser(String email, String name, String password, String surname, String username) {
		if (this.identityService.getUserInfoKeys(email) == null) {
			User newUser = identityService.newUser(email);
			newUser.setEmail(email);
			newUser.setFirstName(name);
			newUser.setLastName(surname);
			newUser.setPassword(password);
			identityService.saveUser(newUser);
			System.out.println(newUser.getId());
		}

	}

	public void indexPaper(Paper paper) {
		/*
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

		}*/

	}

	

}