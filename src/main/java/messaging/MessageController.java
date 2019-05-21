package messaging;

import static messaging.Message.MessageType.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@EnableScheduling
@Controller
public class MessageController {

	@Autowired
	private RoomManagementService roomService;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	public MessageController(SimpMessagingTemplate template) {
		this.template = template;
	}

	//TODO: Validate rooms, throw exception
	@MessageMapping("/{roomNum}/{clientOrHost}")
	public void hostMessage(@DestinationVariable String roomNum, @DestinationVariable String clientOrHost, @Payload Message message) {
		System.out.println("got request");
		RoomState room = roomService.getRoomState(roomNum);

		if (message.getType() == JOIN) {
			Response response = new Response(room.getHostResponded(),
					room.getClientResponded(),
					room.getResult());
			this.template.convertAndSend("/topic/" + roomNum, response);  
			System.out.println(room.getResult());
		} else if (message.getType() == RESPONSE) {
			switch (clientOrHost) {
				case "host":
					room.setHostResponse(message.getAnswer());
					System.out.println("message got was: " + message.getAnswer());
					break;
				case "client":
					room.setClientResponse(message.getAnswer());
					break;
			}

			Response response = new Response(room.getHostResponded(),
					room.getClientResponded(),
					room.getResult());
			System.out.println("sending to: " + "/topic/" + roomNum);
			this.template.convertAndSend("/topic/" + roomNum, response);  
		}
	}

	@RequestMapping("/requestRoom")
	String requestRoom(@ModelAttribute RoomRequest roomRequest, BindingResult errors, Model model) {
		int roomNumber = roomService.makeRoom(roomRequest.getRoomName(), roomRequest.getHostName());

		return "redirect:room/" + roomNumber + "/host";
	}


	@RequestMapping("/testing")
	String roomTest(Model model) {
		int roomNumber = roomService.makeRoom("testRoom", "testHost");
		RoomState room = new RoomState("testingRoom", "testingHost");
		model.addAttribute("roomName", "testingRoom");
		model.addAttribute("hostName", "testingHost");
		model.addAttribute("roomNumber", roomNumber); 
		return "test_room";
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
		model.addAttribute("roomName", room.getRoomName());
		model.addAttribute("hostName", room.getHostName());
		model.addAttribute("roomNumber", roomNum); 

		return "host_room";
	}

	@RequestMapping("/room/{roomNum}/client")
	String roomClient(@PathVariable String roomNum, Model model) {
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
		model.addAttribute("roomName", room.getRoomName());
		model.addAttribute("hostName", room.getHostName());
		model.addAttribute("roomNumber", roomNum); 

		return "client_room";
	}

	/*
		 @Scheduled(fixedRate = 2000)
		 public void repeat_greeting() {
		 System.out.println("scheduled");
		 this.template.convertAndSend("/topic/greetings", new Message("Hello"));
		 }
		 */

}

