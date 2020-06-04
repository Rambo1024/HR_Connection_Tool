package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import connectingToolService.User;
import repositories.UserRepository;

@Controller
@RequestMapping("/login")
@SessionAttributes("user")				// Session Attribut um die Daten des Benutzers über die gesamte Sitzung zu behalten
public class LoginController {
	
	
	// Controllerklasse für Anfragen im LoginScreen
	
	
	@Autowired
	private UserRepository userRepo;
	
	
	public LoginController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@ModelAttribute(name = "user")													// Erzeugung des Session Attributes
	public User user() {
		return new User();
	}
	
	
	@GetMapping
	public String getLoginView(Model model, @ModelAttribute("user") User user) {										// Aufruf der Login.html Datei
		if(!user.getLoginError())
			model.addAttribute("user", user());
		return "login";
	}
	
	
	
	
	@PostMapping
	public String checkLogin(Model model, @ModelAttribute("user") User user, Errors errors) {				// Überprüfung der Logindaten nach Bestätigung
		
		if(userRepo.validateUserLogin(user.getName(), user.getPassword())) {
			user = userRepo.findUserByName(user.getName());
			user.setLoginError(false);
			
			model.addAttribute("user", user);
			
			return "redirect:/home";								// Bei erfolgreicher Überprüfung weiterleitung auf den HomeScreen 
		}
		user.setLoginError(true);
		user.setPassword("");
		model.addAttribute("user", user);
		
		return "/login";									// Bei Fehlern zurück an den LoginController
	}
	
	
	
	
	
	
	
	
}
