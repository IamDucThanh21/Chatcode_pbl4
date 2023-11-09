package Chat_client.Views;

import Chat_client.Controllers.SocketController;
import Chat_client.Models.ServerData;
import com.mysql.cj.log.Log;

import java.net.Socket;
import java.util.Scanner;

public class ConnectView {
    private static final long serialVersionUID = 1L;
    private SocketController socketController;
    private String ipAddress;
    private int port;
    private Scanner sc = new Scanner(System.in);

    public String getIpAddress() {
        return ipAddress;
    }
    public int getPort() {
        return port;
    }
    public ConnectView(){
        // hàm update ServerTable
        System.out.println("Lựa chọn các chức năng cần thiết:");
        System.out.println("1. Tham gia server");
        System.out.println("2. Thoát");

        int selection;
        System.out.print("Nhập lựa chọn: ");
        selection = sc.nextInt();

        if(selection == 1){
            System.out.print("Nhập địa chỉ ip: ");
            this.ipAddress = sc.next();
            System.out.print("Nhập port      : ");
            this.port = sc.nextInt();
        }
        else if(selection == 2){
            System.exit(0);
        }
        else System.out.println("Nhập sai lựa chọn");
    }



}
