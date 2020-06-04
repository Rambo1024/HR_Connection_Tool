package connectingToolService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {"controller, repositories"})
@RequestMapping("/")
public class HrConnectingToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrConnectingToolApplication.class, args);
	}
	
	
	// Weiterleitung einer Root Anfrage an den LoginController
	
	@GetMapping
	public String loginStartpage() {
		return "redirect:/login";
	}

}
