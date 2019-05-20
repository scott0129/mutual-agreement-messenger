package messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

//@EnableScheduling
@Controller
public class MessageController {

	private SimpMessagingTemplate template;

	@Autowired
	public MessageController(SimpMessagingTemplate template) {
		this.template = template;
	}

	@MessageMapping("/rooms/{roomNum}/client")
	public Response hostMessage(@DestinationVariable String roomNum, Message message) {
		this.template.convertAndSend("/user-responses/rooms/" + Integer.parseInt(roomNum), "testing purposes");
		return new Response("Hello, !!!");
	}
	/*
		 @Scheduled(fixedRate = 2000)
		 public void repeat_greeting() {
		 System.out.println("scheduled");
		 this.template.convertAndSend("/topic/greetings", new Message("Hello"));
		 }
		 */

}

