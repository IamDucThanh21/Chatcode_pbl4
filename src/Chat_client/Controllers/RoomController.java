package Chat_client.Controllers;

import Chat_client.Models.Room;

import java.util.ArrayList;

public class RoomController {
    public static Room findRoom(ArrayList<Room> roomList, int id) {
        for (Room room : roomList)
            if (room.getId() == id)
                return room;
        return null;
    }
    public static Room findPrivateRoom(ArrayList<Room> roomList, String Id_OtherUser) {
        for (Room room : roomList) {
            if (room.getType().equals("private") && room.getId_user().equals(Id_OtherUser))
                return room;
        }
        return null;
    }
    public static Room findGroup(ArrayList<Room> roomList, String groupName) {
        for (Room room : roomList) {
            if (room.getType().equals("group") && room.getName().equals(groupName))
                return room;
        }
        return null;
    }
}
