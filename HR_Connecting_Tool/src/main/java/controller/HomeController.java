package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import connectingToolService.Task;
import connectingToolService.User;
import repositories.JdbcUserRepository;


@Controller
@RequestMapping("/home")
@SessionAttributes("user")				// Session Attribut um die Daten des Benutzers über die gesamte Sitzung zu behalten
public class HomeController {

	
	private JdbcUserRepository userRepo;
	
	@Autowired
	public HomeController(JdbcUserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@GetMapping
	public String homeForm() {						// Aufruf der home.html Datei 
		return "home";
	}
	
	@PostMapping("/solveTask")							// Anfrage um eine Aufgabe als erledigt zu setzen
	public String solveTask(Model model, @ModelAttribute User user, Errors errors) {		
		
		Task task = null;
		try {
			task = userRepo.SolveTask(user);				// Dem Repository mitteilen dass die momentane Aufgabe als erledigt gesetzt werden kann 
			
		}catch(DataAccessException dataException) {
			user.setTaskError(true);						// Tritt ein Fehler auf wird der Nutzer wieder auf die Homeseite geleitet und eine Fehlermeldung angezeigt
		}
		if(task != null) {
		user.setEmployeeTask(task);
		user.setTaskError(false);
		
		model.addAttribute("user", user);
		}
		
		return "/home";
	}
	
	
}
