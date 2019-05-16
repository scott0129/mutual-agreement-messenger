package messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
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

	@Autowired
	RoomManagementService roomService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/")
  String index(Model model) {
		RoomRequest roomRequest = new RoomRequest();
    model.addAttribute("roomRequestJavaObject", roomRequest);
    return "index";
  }

	@RequestMapping("/requestRoom")
	String requestRoom(@ModelAttribute RoomRequest roomRequest, BindingResult errors, Model model) {
		System.out.println("I got the room request! for name:" + roomRequest.getHostName());
		int roomNumber = roomService.makeRoom();

		return "redirect:room/" + roomNumber + "/host";
	}

	@RequestMapping("/room/{roomNum}/host")
	String roomHost(@PathVariable String roomNum, Message message) {
		if (!roomService.roomExists(Integer.parseInt(roomNum))) {
			return "no_room_found";
		}

		return "room";
	}

	@RequestMapping("/redirect")
  String redirect() {
    return "redirect";
  }

}
