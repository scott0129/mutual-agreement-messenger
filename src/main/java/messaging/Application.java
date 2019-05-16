package messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/")
  String index(Model model) {
		RoomRequest roomRequest = new RoomRequest();
    model.addAttribute("roomRequestJavaObject", roomRequest);
    return "index";
  }

	@PostMapping("/requestRoom")
	String requestRoom(@ModelAttribute RoomRequest roomRequest, BindingResult errors, Model model) {
		System.out.println("I got the room request! for name:" + roomRequest.getHostName());

		return index(model);
	}

	@RequestMapping("/redirect")
  String redirect() {
    return "redirect";
  }

}
