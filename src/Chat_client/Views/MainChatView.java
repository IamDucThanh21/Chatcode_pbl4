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
    private ArrayList<Client> OnlineUserList;
    private ServerData connectedServer;
    Scanner sc = new Scanner(System.in);
//    public MainChatView(String idUser, SocketController socketController) {
//        this.idUser = idUser;
//        // this.socketController = socketController;
//        System.out.println("Số user đang online: " + socketController.getUserOnline());
//        // có 1 dãy các user đang online, nhập số để check muốn nhắn với user nào, vd: 1. Đức Thành
//        // viết hàm để lấy danh sách các user đang online, gán cho mỗi user 1 số
//        Room foundRoom = Room.findPrivateRoom(socketController.getAllRooms(), "Thành");
//        if(foundRoom == null){
//            // socketController.createPrivateRoom();
//        }
//    }

    public MainChatView(ServerData connectedServer, ArrayList<Client> OnlineUserList){
        this.OnlineUserList = OnlineUserList;
        this.connectedServer = connectedServer;
        updateServerData();
        // có 1 dãy các user đang online, nhập số để check muốn nhắn với user nào, vd: 1. Đức Thành

        updateUserOnlineList(OnlineUserList);
//        System.out.print("Nhập số của người muốn gửi: ");
//        int otherUser = sc.nextInt();
//        System.out.println(OnlineUserList.get(otherUser - 1).getId());
        // Room foundRoom = Room.findPrivateRoom()
    }
    public void updateServerData(){
        System.out.println("Số user đang online: " + connectedServer.getConnectAccountCount());
    }
    public void updateUserOnlineList(ArrayList<Client> OnlineUser){
        this.OnlineUserList = OnlineUser;
        int i = 1;
        for(Client client : OnlineUserList){
            System.out.println(i++ + ". " + client.getName());
        }
    }
}
