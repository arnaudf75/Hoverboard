package hoverboard;

import windows.*;

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
            String dbUrl="jdbc:mysql://localhost:3306/hoverboard_jx90";
            Class.forName("com.mysql.jdbc.Driver");
            dbcon = DriverManager.getConnection(dbUrl,user,password);
        }
        catch (ClassNotFoundException | SQLException conn_error) {
           System.out.println("Error while connecting to database "+conn_error); 
        }
    }
    
     public boolean connect_user(String login, String password) {
        requete = ("SELECT * FROM users WHERE login ='"+login+"' AND password ='"+password+"'");
        try {
            Statement statement = dbcon.createStatement();
            result = statement.executeQuery(requete);
            if (!result.isBeforeFirst()) {
                return (false);
            }
            else {
                result.next();
                String lastName = result.getString("lastName");
                String firstName = result.getString("firstName");
                Home home_window = new Home();
                return (true);
            }
        }
        catch (SQLException error) {
            System.out.println (error);
        }
        return (false);
    }
}