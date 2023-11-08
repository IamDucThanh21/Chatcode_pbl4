package Chat_server.Controllers;

import Chat_server.DAO.ClientDAO;
import Chat_server.Models.Client;
import Chat_server.Models.Room;
import Chat_server.Views.StartScreen;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SocketController {
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    private String serverName;
    private int serverPort;
    ServerSocket serverSocket;
    private static ArrayList<HandlerController> clients;
    private ArrayList<Room> rooms;

    public static ArrayList<HandlerController> getClientHandlers() {
        return clients;
    }
    public static void addHandlerClient(HandlerController client){
        clients.add(client);
    }
    public static void removeClient(HandlerController client){
        clients.remove(client);
    }
    public static int getClientSize(){
        return clients.size();
    }
    public static ArrayList<Client> getClientInfors(){
        ArrayList<Client> clientList= new ArrayList<Client>();
        for(HandlerController handlerController : clients){
            clientList.add(handlerController.getClient());
        }
        return clientList;
    }
    public SocketController(){
        clients = new ArrayList<HandlerController>();
        rooms = new ArrayList<Room>();
    }
    public void OpenSocket(int port) {
        try {
            serverSocket = new ServerSocket(port);
            new Thread(() -> {
                try {
                    do {
                        System.out.println("Waiting for client");

                        Socket clientSocket = serverSocket.accept();

                        HandlerController handlerController = new HandlerController(clientSocket);
                        handlerController.start();
                    } while (serverSocket != null && !serverSocket.isClosed());
                } catch (IOException e) {
                    System.out.println("Server or client socket closed");
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkConnectedUser(){
        return serverSocket.isClosed();
    }
    public void CloseSocket() {
        try {
            for (HandlerController handlerController : clients)
                handlerController.getClient().getSocket().close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateOnlineUser(HandlerController client){
        try {
            for (HandlerController handlerController : clients) {
                if (handlerController == client)
                    continue;
                System.out.println(client.getClient().getName() + " -> " + handlerController.getClient().getName() );
                handlerController.getBufferedWriter().write("new user online");
                handlerController.getBufferedWriter().newLine();
                handlerController.getBufferedWriter().write(client.getClient().getId());
                handlerController.getBufferedWriter().newLine();
                handlerController.getBufferedWriter().write(client.getClient().getName());
                handlerController.getBufferedWriter().newLine();
                handlerController.getBufferedWriter().flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getThisIP() {
        String ip = "";
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("google.com", 80));
            ip = socket.getLocalAddress().getHostAddress();
            socket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return ip;
    }
}
