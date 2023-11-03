package Chat_client.Models;

public class ServerData {
    private String ip;
    private int port;
    private boolean isOpen;
    private int connectAccountCount;
    public String getIp() {
        return ip;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectAccountCount() {
        return connectAccountCount;
    }

    public void setConnectAccountCount(int connectAccountCount) {
        this.connectAccountCount = connectAccountCount;
    }


    public void setOpen(boolean open) {
        isOpen = open;
    }
    public ServerData(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.isOpen = false;
        this.connectAccountCount = 0;
    }

    public ServerData(String ip, int port, boolean isOpen, int connectAccountCount) {
        this.ip = ip;
        this.port = port;
        this.isOpen = isOpen;
        this.connectAccountCount = connectAccountCount;
    }
}
