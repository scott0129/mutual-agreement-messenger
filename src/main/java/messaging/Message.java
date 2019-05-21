package messaging;

public class Message {

	private MessageType type;
	private boolean answer;

	public enum MessageType {
		JOIN,
		RESPONSE
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public boolean getAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}


}

