package Chat_server.Views;

import Chat_server.Controllers.HandlerController;
import Chat_server.Controllers.SocketController;
import Chat_server.Models.Client;

import java.util.ArrayList;

public class StartScreen {
    private int port = 2119;
    private static SocketController socketController;
    boolean isSocketOpened = false;
    public StartScreen(){
        String ip = SocketController.getThisIP();
        System.out.println("Địa chỉ ip: " + ip);
        System.out.println("Port      : " + port);
        System.out.println("Danh sách user đang online: ");

        socketController = new SocketController();
        if(!isSocketOpened){
            socketController.setServerName("Server này là của ĐT");
            socketController.setServerPort(port);

            socketController.OpenSocket(port);
            isSocketOpened = true;
        }
        else {
            socketController.CloseSocket();
            isSocketOpened = false;
        }
    }

    public static SocketController getSocketController() {
        return socketController;
    }

    public static void updateClient(){
        System.out.println("Danh sách user hiện đang online:");
        System.out.println("Tên client:");
        for(HandlerController client : SocketController.getClientHandlers()){
            System.out.println(client.getClient().getName() + " +++ " + client.getPort());
        }
    }
    public static boolean checkConnectedUser(){
        return socketController.checkConnectedUser();
    }
    public static int getSizeClient(){
        return socketController.getClientSize();
    }

}
