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

import connectingToolService.Task;
import connectingToolService.User;
import repositories.JdbcUserRepository;


@Controller
@RequestMapping("/home")
@SessionAttributes("user")
public class HomeController {

	
	private JdbcUserRepository userRepo;
	
	@Autowired
	public HomeController(JdbcUserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	
	@GetMapping
	public String homeForm() {
		return "home";
	}
	
	@PostMapping("/solveTask")
	public String solveTask(Model model, @ModelAttribute User user, Errors errors) {
		Task task;
		do {
			task = userRepo.SolveTask(user);
		}while (task == null);
		
		user.setEmployeeTask(task);
		user.setTaskIsSolved(false);
		user.setTaskSolvedWith("");
		
		model.addAttribute("user", user);
		return "/home";
	}
	
	
}
