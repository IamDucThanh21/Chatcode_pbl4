package Chat_client.Views;

import Chat_client.Controllers.SocketController;
import Chat_client.Models.Client;
import Chat_client.Models.Room;
import Chat_client.Models.ServerData;

import java.util.ArrayList;
import java.util.Scanner;

public class MainChatView {
    private static final long serialVersionUID = 1L;
    private static int chattingRoom = -1;

    private ServerData connectedServer;
    Scanner sc = new Scanner(System.in);

    public MainChatView(ServerData connectedServer){
        this.connectedServer = connectedServer;
        // có 1 dãy các user đang online, nhập số để check muốn nhắn với user nào, vd: 1. Đức Thành

//        System.out.print("Nhập số của người muốn gửi: ");
//        int otherUser = sc.nextInt();
//        System.out.println(OnlineUserList.get(otherUser - 1).getId());
        // Room foundRoom = Room.findPrivateRoom()
    }

}
