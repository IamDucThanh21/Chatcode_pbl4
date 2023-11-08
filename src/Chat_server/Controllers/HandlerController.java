package Chat_server.Controllers;

import Chat_server.DAO.ClientDAO;
import Chat_server.Models.Client;
import Chat_server.Views.StartScreen;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HandlerController extends Thread{
    private Client client;
    private  OutputStream osHandler;
    private  InputStream isHandler;
    private Socket socketHandler;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public void setPort(int port) {
        this.port = port;
    }

    private ArrayList<Client> clientList;
    private int port;

    public void setClient(Client client) {
        this.client = client;
    }

    public HandlerController(Socket clientSocket){
        try {
            this.client = new Client();
            this.socketHandler = clientSocket;
            this.osHandler = this.socketHandler.getOutputStream();
            this.isHandler = this.socketHandler.getInputStream();
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.osHandler, StandardCharsets.UTF_8)); // viết riêng
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.isHandler, StandardCharsets.UTF_8)); // viết riêng
            this.port = this.socketHandler.getPort();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void run() {
        try{
            while (true){
                String header = bufferedReader.readLine();
                if(header == null) throw new IOException();

                System.out.println("Header: " + header);
                switch (header){
                    case "New login": {
                        String clientUsername = bufferedReader.readLine();
                        String clientPassword = bufferedReader.readLine();

                        String rs = ClientController.Login(clientUsername, clientPassword); // kiểm tra đăng nhập
                        // nếu mà k dính 2 cái trên thì dưới đó trả về id của client

                        if(rs.equals("Login-fail")) {
                            System.out.println("Đăng nhập không thành công");
                            bufferedWriter.write("Login-fail");
                            bufferedWriter.flush();
                        }
                        else if(rs.equals("Password_fail")) {
                            System.out.println("Mật khẩu không chính xác");
                            bufferedWriter.write("Password_fail");
                            bufferedWriter.flush();
                        }
                        else {
                            boolean logined = ClientController.checkClientIsLogin(rs);
                            if(logined) {
                                bufferedWriter.write("Account-logined");
                                bufferedWriter.flush();
                                System.out.println("Tài khoản đã được đăng nhập!");
                            }
                            else {
                                this.client = ClientDAO.getClient(rs);

                                String name = this.client.getName();
                                System.out.println(name + " đăng nhập thành công");

                                SocketController.addHandlerClient(this);
                                StartScreen.updateClient();

                                this.bufferedWriter.write("Login success");
                                this.bufferedWriter.newLine();
                                this.bufferedWriter.flush();

                                this.bufferedWriter.write(client.getId());
                                this.bufferedWriter.newLine();
                                this.bufferedWriter.write(client.getName());
                                this.bufferedWriter.newLine();
                                this.bufferedWriter.flush();

//                            Hiện số lượng người đang onl - 1 nghĩa là trừ thằng thằng mà đang nhắn
                                this.bufferedWriter.write("" + (SocketController.getClientSize() - 1));
                                this.bufferedWriter.newLine();
                                this.bufferedWriter.flush();
                                // cập nhật lại danh sách user đang online cho thằng this client
                                for (HandlerController handlerController : SocketController.getClientHandlers()) {
                                    if ((handlerController.getClient().getId()).equals(this.client.getId()))
                                        continue;
                                    this.bufferedWriter.write(handlerController.getClient().getId());
                                    this.bufferedWriter.newLine();
                                    this.bufferedWriter.write(handlerController.getClient().getName());
                                    this.bufferedWriter.newLine();
                                    this.bufferedWriter.flush();
                                }
//                              //Gửi thông tin từ this client về các client khác
                                for (HandlerController handlerController : SocketController.getClientHandlers()) {

                                    if ((handlerController.getClient().getId()).equals(this.client.getId()))
                                        continue;
                                    handlerController.getBufferedWriter().write("new user online");
                                    handlerController.getBufferedWriter().newLine();
                                    handlerController.getBufferedWriter().write("" + (SocketController.getClientSize() - 1));
                                    handlerController.getBufferedWriter().newLine();
                                    handlerController.getBufferedWriter().write(this.client.getId());
                                    handlerController.getBufferedWriter().newLine();
                                    handlerController.getBufferedWriter().write(this.client.getName());
                                    handlerController.getBufferedWriter().newLine();
                                    handlerController.getBufferedWriter().flush();
                                }
                            }
                        }
                        break;
                    }
                    case "Sign up":{
                        String name = bufferedReader.readLine();
                        String username = bufferedReader.readLine();
                        String password = bufferedReader.readLine();
                        String email = bufferedReader.readLine();

                        Client clientSignUp = new Client(name, username, password, email);
                        clientSignUp.setLogin(false);
                        String result = ClientController.SignUp(clientSignUp);

                        bufferedWriter.write(result);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();

                        break;
                    }
                    case "Get id user":{
                        String id = client.getId();
                        bufferedWriter.write(id);
                        bufferedWriter.flush();
                    }

                    case "get name": {
                        bufferedWriter.write("Server của ĐT");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        break;
                    }
                    case "get connected count": {
                        bufferedWriter.write("" + StartScreen.getSizeClient());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        break;
                    }
                }
            }
        }catch (IOException ex){
            if(!StartScreen.checkConnectedUser() && client!=null){
                try{
//                    for (Client clientVari : SocketController.getClients()) {
//                        if (!(clientVari.getId()).equals(client.getId())) {
//                            clientVari.bufferedWriter.write("user quit");
//                            clientVari.bufferedWriter.newLine();
//                            clientVari.bufferedWriter.write(client.getId());
//                            clientVari.bufferedWriter.newLine();
//                            clientVari.bufferedWriter.write(client.getName());
//                            clientVari.bufferedWriter.newLine();
//                            clientVari.bufferedWriter.flush();
//                        }
//                    }
//
//                    for (Room room : Main.socketController.allRooms)
//                        room.users.remove(thisClient.userName);
                    socketHandler.close();
                    System.out.println(client.getName() + " đã rời khỏi đoạn chat");
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                SocketController.removeClient(this);
                ClientController.Logout(client.getId());
                StartScreen.updateClient();
            }
        }
    }

    public Client getClient() {
        return client;
    }
}
