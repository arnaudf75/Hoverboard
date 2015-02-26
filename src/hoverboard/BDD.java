package hoverboard;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;      
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * BDD est la classe qui instancie une connexion à la base de données et qui contient les requêtes SQL permettant d'intéragir avec celle-ci.
 * @author Arnaud
 */

public class BDD {
    
    Connection dataBaseConnection = null;
    String requete;
    Statement statement;
    ResultSet result;
    String databaseUrl, driver, user, password;
    
    /**
     * Initialise la connexion à la base de données
     */
    
    public BDD() {
        ParserXml xmlParser = new ParserXml();
        HashMap data_jdbc = xmlParser.getDataJDBC(xmlParser.getSax());        
        try {
            this.databaseUrl=data_jdbc.get("dbUrl").toString();
            this.user =  data_jdbc.get("login").toString();
            this.password =  data_jdbc.get("password").toString();
            Class.forName(data_jdbc.get("driver").toString());
            dataBaseConnection = DriverManager.getConnection(databaseUrl, user, password);
            this.statement = dataBaseConnection.createStatement();
        }
        catch (ClassNotFoundException | SQLException conn_error) {
           System.out.println("Error while connecting to database "+conn_error); 
        }
    }
    
    /**
     * 
     * @param positionX
     * @param positionY
     * @param height
     * @param width
     * @param idDashboard
     * @param typeWidget
     * @return 
     */

    public int ajouteWidget(int positionX, int positionY, int height, int width, int idDashboard, int typeWidget) {
        this.requete = "INSERT INTO widgets VALUES (NULL, '', "+positionX+", "+positionY+", "+height+", "+width+", "+idDashboard+", "+typeWidget+") "; // 2 veut dire qu'on ajoute un post-it, à changer plus tard
        int idWidget = -1;
        try {
            this.statement.executeUpdate(this.requete, Statement.RETURN_GENERATED_KEYS);
            this.result = this.statement.getGeneratedKeys();
            if (this.result.next()) {
                idWidget = this.result.getInt(1);
                System.out.println("Id du widget : "+idWidget +" de type "+typeWidget);
            }
        }
        catch (SQLException error) {
            System.out.println("Impossible d'ajouter le widget ! "+error);
            return (-1);
        }
        
        return (idWidget);
    }
    
    /**
     * Est exécutée lorsqu'un utilisateur essaye de se connecter à l'application. Vérifie que le login et le mot de passe saisis sont valides.
     * @param login
     * Le login saisit par l'utilisateur via la fenêtre de login.
     * @param password
     * Le mot de passe saisit par l'utilisateur via la fenêtre de login.
     * @return 
     * True si il exite bien un utilisateur, False si il n'en existe aucun ou qu'une erreur est levée.
     */
    
    public boolean connect_user(String login, String password) {
        requete = ("SELECT * FROM users WHERE login ='"+login+"' AND password ='"+password+"'");
        try {
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
    
    /**
     * Supprime un widget dans la base de données.
     * @param idWidget 
     */
    
    public void deleteWidget(int idWidget) {
        this.requete = "DELETE FROM widgets WHERE idWidget ="+idWidget;
        try {
            this.statement.executeUpdate(requete);
        }
        catch (SQLException error) {
            System.out.println ("Impossible de supprimer le widget ! "+error);
        }
    }
    
    public ResultSet getNewWidgets(int idDashboard) {
        this.requete = "SELECT E.*, T.nomTypeWidget FROM widgets E, type_widget T WHERE idDashboard = 3 AND E.idTypeWidget = T.idTypeWidget";
        try {
            this.result = statement.executeQuery(requete);
            if (!this.result.isBeforeFirst()) {
                System.out.println("Aucun widget !");
                return (this.result);
            }
        }
        catch (SQLException error) {
            System.out.println ("Impossible de récupérer les widgets ! "+error);
        }
        return (this.result);
    }
    
    
    /**
     * Appellée lorsqu'un utilisateur demande la création d'un compte sur l'application. Elle vérifie qu'il n'existe pas déjà
     * un utilisateur utilisant ce login et ce mot de passe, sinon elle l'inscrit.
     * @param firstName
     * Prénom saisit par l'utilisateur.
     * @param lastName
     * Nom de famille saisit par l'utilisateur.
     * @param login
     * Login choisit par l'utilisateur
     * @param password
     * Mot de passe choisit par l'utilisateur.
     * @param email
     * Email saisit par l'utilisateur.
     * @return 
     * True si l'utilisateur a bien été inscrit, False sinon.
     */
    
    public boolean registerUser(String firstName, String lastName, String email, String login, String password) {
        requete = ("SELECT email, login FROM users WHERE email = '"+email+"' OR login = '"+login+"'");
        try {
            result = statement.executeQuery(requete);
            if (!result.isBeforeFirst()) {
                // Pas d'users existant, on peut inscrire l'user
                requete = "INSERT INTO users VALUES (NULL, '"+firstName+"', '"+lastName+"', '"+email+"', '"+login+"', '"+password+"' ,0 ,0) ";
                statement.executeUpdate(requete);
            }
            else {
                return (false);
            }
        }
        catch (SQLException error) {
             System.out.println("Impossible de créer le compte ! "+error);   
        }
        return (true);
    }
    
    /**
     * Cherche dans la base de données si un utilisateur existe avec cet email et change le mot de passe.
     * @param emailUser
     * L'adresse email renseignée dans le formulaire.
     * @return 
     * True si toutes les actions ont été effectuées, False si l'utilisateur n'existe pas ou que la connexion à la base de données n'a pas fonctionnée.
     */
    
    public boolean resetPassword(String emailUser) {
        requete = "SELECT * FROM users WHERE email ='"+emailUser+"'";
        try {
            result = statement.executeQuery(requete);
            if (!result.isBeforeFirst()) {
                // Aucun utilisateur avec cet email
                return (false);
            }
            else {
                // On doit réinitialiser le mot de passe de l'utilisateur ici
                // Une idée : Update From users; Set password = random(passsword)
                // On envoie le nouveau password par mail à l'utilisateur
                return (true);
            }
        }
        catch (SQLException error) {
            System.out.println ("Impossible de changer le mot de passe ! "+error);
        }
        return (false);
    }
}