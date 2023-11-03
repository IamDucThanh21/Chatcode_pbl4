package Chat_server.Models;

import java.util.ArrayList;

public class Room {
    private int id;
    private  String name; // nếu là nhắn tin 1-1 thì name là tên người nhận, multi client thì là tên nhóm
    private ArrayList<Client> clients;
}
