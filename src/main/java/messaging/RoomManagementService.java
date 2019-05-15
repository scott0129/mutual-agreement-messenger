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

	public int makeRoom() {
		rooms.add(new RoomState());
		return rooms.size() - 1;
	}

	public RoomState getRoomState(String roomNum) {
		int idx = Integer.parseInt(roomNum);
		return rooms.get(idx);
	}
	
}