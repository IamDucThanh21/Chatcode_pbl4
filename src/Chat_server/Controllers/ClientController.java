package Chat_server.Controllers;

import Chat_server.DAO.ClientDAO;
import Chat_server.Models.Client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientController{
    public void showClients(){
        ArrayList<Client> clients = new ArrayList<Client>();
        clients = ClientDAO.getClients();
        for (Client client:clients) {
            System.out.printf("%-10s%-20s%-20s%-20s%-30s%-5s",client.getId(), client.getName(), client.getUsername(), client.getPassword(), client.getEmail(), client.isLogin());
            System.out.println();
        }
    }
    public static String Login(String username, String password){
        String result = ClientDAO.Login(username, password);
        if(result.equals("Login-fail")){
            return "Login-fail";
        } else if (result.equals("Password_fail")) {
            return "Password_fail";
        } else{
            Client client = ClientDAO.getClient(result);
            // System.out.println(result);
            return result;
        }
    }
    public static boolean checkClientIsLogin(String idUser){
        return ClientDAO.CheckLogin(idUser);
    }
    public static void Logout(String iduser){
        ClientDAO.Logout(iduser);
    }
    public void showUser(){
        String id_user = "US01";
        Client client = ClientDAO.getClient(id_user);
        System.out.printf("%-10s%-20s%-20s%-20s%-30s%-5s",client.getId(), client.getName(), client.getUsername(), client.getPassword(), client.getEmail(), client.isLogin());
    }
}
