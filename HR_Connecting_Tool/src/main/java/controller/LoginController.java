package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import connectingToolService.User;
import repositories.UserRepository;

@Controller
@RequestMapping("/login")
@SessionAttributes("user")
public class LoginController {
	
	@Autowired
	private UserRepository userRepo;
	
	
	public LoginController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
	
	@ModelAttribute(name = "user")
	public User user() {
		return new User();
	}
	
	@GetMapping
	public String getLoginView(Model model) {										// Aufruf der Loginseite
		model.addAttribute("user", user());
		return "login";
	}
	
	
	
	@PostMapping
	public String checkLogin(Model model, @ModelAttribute("user") User user, Errors errors) {				// Überprüfung der Logindaten nach Bestätigung
		
		System.out.println(user.getName()+"     :      "+ user.getPassword());
		
		if(userRepo.validateUserLogin(user.getName(), user.getPassword())) {
			user = userRepo.findUserByName(user.getName());
			
			model.addAttribute("user", user);
			
			return "redirect:/home";
		}
		
		return "redirect:/login";
	}
	
	
	
	
	
	
	
	
}
