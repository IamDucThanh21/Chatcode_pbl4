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
        System.out.print("NameUser: ");
        this.NameUser = sc.next();
        System.out.print("Username: ");
        this.username = sc.next();
        System.out.print("Password: ");
        this.password = sc.next();
        System.out.print("Email: ");
        this.email = sc.next();
        this.client = new Client(NameUser, username, password, email);
    }

}
// viết sau