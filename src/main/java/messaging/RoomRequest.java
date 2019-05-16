package messaging;

import java.io.Serializable;

public class RoomRequest implements Serializable {

	private String roomName;
	private String hostName;

	private static final long serialVersionUID = 641L; //Train I happened to be on when I wrote this

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


}
