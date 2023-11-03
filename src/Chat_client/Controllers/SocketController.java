package Chat_client.Controllers;

import Chat_client.Models.Room;
import Chat_client.Models.ServerData;
import Chat_client.Models.Client;
import Chat_client.Views.Login;
import Chat_client.Views.MainChatView;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class SocketController {
    Client client;
    ServerData connectedServer;
    Socket socket;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    Thread receiveAndProcessThread;
    Scanner sc = new Scanner(System.in);
    private ArrayList<Client> onlineUsers;
    public ArrayList<Room> allRooms;

    public ArrayList<Room> getAllRooms() {
        return allRooms;
    }

    public SocketController(String ipAddress, int port){
        onlineUsers = new ArrayList<Client>();
        allRooms = new ArrayList<Room>();
        client = new Client();
        connectedServer = new ServerData(ipAddress, port);
        try {
            socket = new Socket(ipAddress, port);
            InputStream is = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            OutputStream os = socket.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Không tồn tại server");
            // throw new RuntimeException(e);
        }
        boolean rs =false;
        while (!rs){
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
            System.out.print("Nhập lựa chọn: ");
            int select = sc.nextInt();
            if(select == 1){
                Login account = new Login();
                rs = Login(account.getUsername(), account.getPassword());
            }
            else if (select == 2) {
                // đăng kí
            }
            else rs = false;
        }
        connectedServer.setOpen(true);
        try {
            connectedServer.setConnectAccountCount(Integer.parseInt(bufferedReader.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        getOnlineUserss();
        MainChatView mainChatView = new MainChatView(connectedServer, onlineUsers);
        mainChatView.updateServerData();
        mainChatView.updateUserOnlineList(onlineUsers);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String header = bufferedReader.readLine();
                        System.out.println("Header: " + header);
                        if (header == null)
                            throw new IOException();
                        switch (header) {
                            case "new user online": {
                                String Id_user = "không", Name_user = "không";
                                Id_user = bufferedReader.readLine();
                                Name_user = bufferedReader.readLine();
                                System.out.println(Id_user + " +++ " + Name_user);
                                Client clientVari = new Client(Id_user, Name_user);
                                onlineUsers.add(clientVari);
                                mainChatView.updateServerData();
                                mainChatView.updateUserOnlineList(onlineUsers);
                                break;
                            }
                            case "user quit": {
                                String Id_user = bufferedReader.readLine();
                                String Name_user = bufferedReader.readLine();
                                Client clientVari = new Client(Id_user, Name_user);
                                System.out.println(Id_user + " quit");
                                onlineUsers.remove(clientVari);
                                mainChatView.updateServerData();
                                mainChatView.updateUserOnlineList(onlineUsers);
//                            for (Room room : allRooms) {
//                                if (room.users.contains(whoQuit)) {
//                                    Main.mainScreen.addNewMessage(room.id, "notify", whoQuit, "Đã thoát ứng dụng");
//                                    room.users.remove(whoQuit);
//                                }
//                            }
//                            Main.mainScreen.updateRoomUsersJList();

                                break;
                            }
                        }
                    }
                }catch (IOException e){
                    System.out.println("Server đã đóng, ứng dụng sẽ thoát ngay lập tức!");
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                System.exit(0);
            }
        }).start();
//        () -> {
//            try {
//                while (true) {
//                    String header = bufferedReader.readLine();
//                    System.out.println("Header: " + header);
//                    if (header == null)
//                        throw new IOException();
//                    switch (header) {
//                        case "new user online": {
//                            String Id_user = "không", Name_user = "không";
//                            Id_user = bufferedReader.readLine();
//                            Name_user = bufferedReader.readLine();
//                            System.out.println(Id_user + " +++ " + Name_user);
//                            Client clientVari = new Client(Id_user, Name_user);
//                            onlineUsers.add(clientVari);
//                            mainChatView.updateServerData();
//                            mainChatView.updateUserOnlineList(onlineUsers);
//                            break;
//                        }
//                        case "user quit": {
//                            String Id_user = bufferedReader.readLine();
//                            String Name_user = bufferedReader.readLine();
//                            Client clientVari = new Client(Id_user, Name_user);
//                            System.out.println(Id_user + " quit");
//                            onlineUsers.remove(clientVari);
//                            mainChatView.updateServerData();
//                            mainChatView.updateUserOnlineList(onlineUsers);
////                            for (Room room : allRooms) {
////                                if (room.users.contains(whoQuit)) {
////                                    Main.mainScreen.addNewMessage(room.id, "notify", whoQuit, "Đã thoát ứng dụng");
////                                    room.users.remove(whoQuit);
////                                }
////                            }
////                            Main.mainScreen.updateRoomUsersJList();
//
//                            break;
//                        }
//                    }
//                }
//            }catch (IOException e){
//                System.out.println("Server đã đóng, ứng dụng sẽ thoát ngay lập tức!");
//                try {
//                    socket.close();
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//            System.exit(0);
//        });
//        receiveAndProcessThread.start();
    }
    public void getOnlineUserss(){
        for(int i = 0; i < this.connectedServer.getConnectAccountCount(); i++ ){
            try {
                String id_user = bufferedReader.readLine();
                String NameUser = bufferedReader.readLine();
                Client clientVari = new Client(id_user, NameUser);
                onlineUsers.add(clientVari);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public boolean Login(String username, String password){
        try{
            bufferedWriter.write("New login");
            bufferedWriter.newLine();
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.write(password);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String loginResult = bufferedReader.readLine();

            if(loginResult.equals("Login-fail")) System.out.println("Đăng nhập không thành công");
            else if(loginResult.equals("Password_fail")) System.out.println("Mật khẩu không chính xác");
            else if(loginResult.equals("Account-logined")) System.out.println("Tài khoản đã được đăng nhập");
            else if(loginResult.equals("Login success")){
                System.out.println("Đăng nhập thành công!");
                client.setId(bufferedReader.readLine());
                client.setName(bufferedReader.readLine());
                return true;
            }
            return false;
        }catch (IOException ex){

        }
        return false;
    }

    public String GetIdUser(String username, String password){
        String id ="";
        try{
            bufferedWriter.write("Get id user");
            bufferedWriter.newLine();
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.write(password);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            id = bufferedReader.readLine();
            return id;
        }catch (IOException ex){

        }
        return id;
    }
    // lấy những user khác đang online trên server
    public int getUserOnline(){
        return connectedServer.getConnectAccountCount();
    }
    public static Socket serverOnline(String ip, int port) {
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 300);
            s.close();
            return s;
        } catch (IOException ex) {
            return null;
        }
    }
    public void createPrivateRoom(String otherUser) {
        try {
            bufferedWriter.write("request create room");
            bufferedWriter.newLine();
            bufferedWriter.write(otherUser); // room name
            bufferedWriter.newLine();
            bufferedWriter.write("private"); // room name
            bufferedWriter.newLine();
            bufferedWriter.write("2");
            bufferedWriter.newLine();
            bufferedWriter.write(client.getId());
            bufferedWriter.newLine();
            bufferedWriter.write(otherUser);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}