package messaging;

public class RoomState {

	boolean hostResponded = false;
	boolean clientResponded = false;
	boolean result = true;
	
	public void setHostResponse(boolean response) {
		hostResponded = true;
		result = response && result;
	}

	public void setClientResponse(boolean response) {
		clientResponded = true;
		result = response && result;
	}

	public boolean resultReady() {
		return hostResponded && clientResponded;
	}

	public boolean getResult() {
		return result;
	}

}
