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
		int roomNumber = roomService.makeRoom(roomRequest.getRoomName(), roomRequest.getHostName());

		return "redirect:room/" + roomNumber + "/host";
	}

	@RequestMapping("/room/{roomNum}/host")
	String roomHost(@PathVariable String roomNum, Model model) {
		if (roomNum.equals("-1")) {
			RoomState room = new RoomState("testingRoom", "testingHost");
			model.addAttribute("roomName", "testingRoom");
			model.addAttribute("hostName", "testingHost");
			model.addAttribute("roomNumber", roomNum); 
			return "host_room";
		}

		if (!roomService.roomExists(Integer.parseInt(roomNum))) {
			return "no_room_found";
		}
		RoomState room = roomService.getRoomState(roomNum);

    model.addAttribute("roomJavaObj", room);

		return "host_room";
	}

	@RequestMapping("/room/{roomNum}/client")
	String roomClient(@PathVariable String roomNum, Model model) {
		if (roomNum.equals("-1")) {
			RoomState room = new RoomState("testingRoom", "testingHost");
			model.addAttribute("roomName", "testingRoom");
			model.addAttribute("hostName", "testingHost");
			return "room";
		}

		if (!roomService.roomExists(Integer.parseInt(roomNum))) {
			return "no_room_found";
		}
		RoomState room = roomService.getRoomState(roomNum);

    model.addAttribute("roomJavaObj", room);

		return "room";
	}

	@RequestMapping("/redirect")
  String redirect() {
    return "redirect";
  }

}
