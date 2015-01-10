package Hoverboard;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;      
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDD {
    
    Connection dbcon = null;
    String requete;
    Statement statement;
    ResultSet result;
    String user, password;
    
    public BDD(String user, String password) {
        try {
            String dbUrl="jdbc:mysql://localhost:3306/Hoverboard";
            Class.forName("com.mysql.jdbc.Driver");
            dbcon = DriverManager.getConnection(dbUrl,user,password);
        }
        catch (ClassNotFoundException class_error) {
           System.out.println("Error while connecting to database "+class_error); 
        }
        catch (SQLException sql_error) {
            System.out.println("Error while connecting to database "+sql_error);  
        }
    }
}
