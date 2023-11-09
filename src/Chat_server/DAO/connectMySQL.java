package Chat_server.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectMySQL {
    public static Connection connectSQL(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/chat_tcp";
            String user = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, user, password);
//            if (conn != null) {
//                System.out.println("Connect success");
//            }
            return conn;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
