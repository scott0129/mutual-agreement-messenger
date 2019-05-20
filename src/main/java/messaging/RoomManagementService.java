package messaging;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomManagementService {

	List<RoomState> rooms;

	public RoomManagementService() {
		rooms = new ArrayList<RoomState>(0);
	}

	public int makeRoom(String roomName, String hostName) {
		rooms.add(new RoomState(roomName, hostName));
		return rooms.size() - 1;
	}

	public RoomState getRoomState(String roomNum) {
		int idx = Integer.parseInt(roomNum);
		return rooms.get(idx);
	}

	public boolean roomExists(int roomNum) {
		return 0 <= roomNum && roomNum < rooms.size(); 
	}
	
}
