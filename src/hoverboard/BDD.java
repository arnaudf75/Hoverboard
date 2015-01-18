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
    String login, password;
    
    public BDD(String user, String password) {
        try {
            String dbUrl="jdbc:mysql://localhost:3306/Hoverboard";
            Class.forName("com.mysql.jdbc.Driver");
            dbcon = DriverManager.getConnection(dbUrl,user,password);
            statement = dbcon.createStatement();
        }
        catch (ClassNotFoundException | SQLException conn_error) {
           System.out.println("Error while connecting to database "+conn_error); 
        }
    }
    
    public void connect_user(String login, String password) {
        requete = ("SELECT * FROM users WHERE login ='"+login+"' AND password ='"+password+"'");        
        try {
            result = statement.executeQuery(requete);
            if (!result.isBeforeFirst()) {
                System.out.println ("Nobody in the database with this login and password !");
            }
            else {
                result.next();
                String lastName = result.getString("lastName");
                String firstName = result.getString("firstName");
                System.out.println(firstName+" "+lastName);
            }
        }
        catch (SQLException error) {
            System.out.println (error);
        }
        
    }
}
