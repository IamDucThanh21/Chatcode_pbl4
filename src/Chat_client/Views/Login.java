package Chat_client.Views;

import Chat_client.Controllers.SocketController;
import Chat_client.Models.ServerData;
import java.util.Scanner;

public class Login{
    private String username;
    private String password;
    private Scanner sc = new Scanner(System.in);
    public Login(){
        System.out.print("username: ");
        this.username = sc.next();
        System.out.print("Password: ");
        this.password = sc.next();
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
