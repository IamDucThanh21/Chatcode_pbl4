package Chat_server.Models;

import java.util.ArrayList;

public class Room {
    public static int currentRoomID = 1;
    private int id;
    private  String name; // nếu là nhắn tin 1-1 thì name là tên người nhận, multi client thì là tên nhóm
    private ArrayList<Client> clients;
    public Room(String name, ArrayList<String> users) {
        this.id = currentRoomID++;
        this.name = name;
        // this.clients = users;
    }

    public static Room findRoom(ArrayList<Room> roomList, int id) {
        for (Room room : roomList)
            if (room.id == id)
                return room;
        return null;
    }
}
