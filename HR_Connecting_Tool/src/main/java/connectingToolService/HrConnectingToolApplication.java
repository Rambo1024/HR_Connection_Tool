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
public class HrConnectingToolApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(HrConnectingToolApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/login").setViewName("login");
	}
	
	
	@GetMapping
	public String loginStartpage() {
		return "redirect:/login";
	}

}
