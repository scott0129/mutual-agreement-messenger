package messaging;

public class RoomState {

	private String roomName;
	private String hostName;
	private String clientName;

	private boolean hostResponded = false;
	private boolean clientResponded = false;
	private boolean result = true;

	public RoomState(String roomName, String hostName) {
		setRoomName(roomName);
		setHostName(hostName);
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setHostResponse(boolean response) {
		hostResponded = true;
		result = response && result;
	}

	public void setClientResponse(boolean response) {
		clientResponded = true;
		result = response && result;
	}

	public String getRoomName() {
		return roomName;
	}

	public boolean resultReady() {
		return hostResponded && clientResponded;
	}

	public String getHostName() {
		return hostName;
	}

	public String getClientName() {
		return clientName;
	}

	public boolean getHostResponded() {
		return hostResponded;
	}

	public boolean getClientResponded() {
		return clientResponded;
	}

	public boolean getResult() {
		return result;
	}


}
