package Chat_client.Views;

import Chat_client.Models.Client;

import java.util.Scanner;

public class SignUp{
    private String NameUser;
    private String username;
    private String password;
    private String email;
    Client client;

    public Client getClient() {
        return client;
    }

    Scanner sc = new Scanner(System.in);
    public SignUp(){
        this.NameUser = sc.next("NameUser: ");
        this.username = sc.next("Username: ");
        this.password = sc.next("Password: ");
        this.email = sc.next("Email: ");
        client = new Client(NameUser, username, password, email);
    }

}
// viết sau