
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBCONNECTION_MAIN {

    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "lavanya29";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
    // try{
//    Connection con=DriverManager.getConnection(URL,USER,PASSWORD);}
//catch(SQLException e){
//    e.printStackTree();
//}