package hoverboard;

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
    String dbUrl, driver, user, password;
    
    public BDD(String dbUrl, String driver, String user, String password) {
        
        try {
            this.dbUrl=dbUrl;
            Class.forName(driver);
            dbcon = DriverManager.getConnection(dbUrl,user,password);
        }
        catch (ClassNotFoundException | SQLException conn_error) {
           System.out.println("Error while connecting to database "+conn_error); 
        }
    }
    
    public boolean connect_user(String login, String password) {
        requete = ("SELECT * FROM users WHERE login ='"+login+"' AND password ='"+password+"'");
        try {
            this.statement = dbcon.createStatement();
            result = statement.executeQuery(requete);
            if (!result.isBeforeFirst()) {
                return (false);
            }
            else {
                result.next();
                String lastName = result.getString("lastName");
                String firstName = result.getString("firstName");
                return (true);
            }
        }
        catch (SQLException error) {
            System.out.println (error);
        }
        return (false);
    }
    
    public boolean ifUserExists(String login, String email) {
        requete = ("SELECT email, login FROM users WHERE email = '"+email+"' OR login = '"+login+"'");
        try {
            this.statement = dbcon.createStatement();
            result = statement.executeQuery(requete);
            if (!result.isBeforeFirst()) {
                // Pas d'users existant, on peut inscrire l'user
                requete = "INSERT INTO users VALUES (NULL, '"+login+"', '"+login+"', '"+email+"', '"+login+"', '"+login+"' ,0 ,0) ";
                statement.executeUpdate(requete);
                return (true);
            }
            else {
                return (false);
            }
        }
        catch (SQLException error) {
             System.out.println(error);   
        }
        return (true);
    }
     
}