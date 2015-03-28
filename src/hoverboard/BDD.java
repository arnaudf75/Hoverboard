package hoverboard;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;      
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * BDD est la classe qui instancie une connexion à la base de données et qui contient les requêtes SQL
 * permettant d'intéragir avec celle-ci.
 * @author Arnaud
 */
public class BDD {
    
    Connection dataBaseConnection = null;
    String requete;
    Statement statement;
    ResultSet result;
    String databaseUrl, driver, user, password;
    
    /**
     * Initialise la connexion à la base de données à partir du fichier data_jdbc.xml.
     */ 
    public BDD() {
        try {
            Document data_jdbc = new SAXBuilder().build(this.getClass().getClassLoader().getResource("ressources/data_jdbc.xml"));
            Element racine = data_jdbc.getRootElement();
            this.databaseUrl= racine.getChild("dbUrl").getText();
            this.user =  racine.getChild("login").getText();
            this.password =  racine.getChild("password").getText();
            Class.forName(racine.getChild("driver").getText());
            dataBaseConnection = DriverManager.getConnection(databaseUrl, user, password);
            this.statement = dataBaseConnection.createStatement();
        }
        catch (ClassNotFoundException | IOException | JDOMException | SQLException error) {
           System.out.println("Impossible de se connecter à la base de données ! "+error); 
        }
    }
    
    public int ajouteDashboard(int idUser, String titreDashboard, String descriptionDashboard, int isShared) {
        this.requete = "INSERT INTO dashboard VALUES(NULL, '"+titreDashboard+"', '"+descriptionDashboard+"', "+isShared+")";
        int idDashboard = -1;
        try {
            this.statement.executeUpdate(this.requete, Statement.RETURN_GENERATED_KEYS);
            this.result = this.statement.getGeneratedKeys();
            if (this.result.next()) {
                idDashboard = this.result.getInt(1);
            }
            this.requete = "INSERT INTO utilise VALUES ("+idUser+", "+idDashboard+", 1)";
            this.statement.executeUpdate(this.requete);
        }
        catch (SQLException error) {
            System.out.println("Impossible d'ajouter le dashboard ! "+error);
            return (-1);
        }
        return (idDashboard);
    }
    
    public boolean ajouteUserToDashboard(int idDashboard, String pseudoUser) {
        this.requete = "SELECT idUser from users WHERE login ='"+pseudoUser+"' ";
        int idUser = -1;
        try {
            this.result = this.statement.executeQuery(this.requete);
            if (this.result.next()) {
                idUser = this.result.getInt("idUser");
                this.requete = "INSERT INTO utilise VALUES ("+idUser+", "+idDashboard+", 0)";
                this.statement.executeUpdate(this.requete);
                return (true);
            }
        }
        catch (SQLException error) {
            System.out.println("Impossible d'ajouter '"+pseudoUser+"' au dashboard !");
        }
        return (false);
    }
    
    /**
     * Ajoute un widget créé depuis le menu à la base de données.
     * @param positionX La position horizontale du widget sur le dashboard.
     * @param positionY La position verticale du widget sur le dashboard.
     * @param height La hauteur du widget.
     * @param width La largeur du widget.
     * @param idDashboard L'id du dashboard depuis lequel le widget a été créé.
     * @param typeWidget Le type du widget (post-it, liste de tâches ou sondage). 
     * @return L'id du widget issu de l'insertion dans la base de données. Si cette dernière échoue, la fonction renvoie -1.
     */
    public int ajouteWidget(int positionX, int positionY, int height, int width, int idDashboard, int typeWidget) {
        this.requete = "INSERT INTO widgets VALUES (NULL, '', "+positionX+", "+positionY+", "+height+", "+width+", "+idDashboard+", "+typeWidget+") ";
        int idWidget = -1;
        try {
            this.statement.executeUpdate(this.requete, Statement.RETURN_GENERATED_KEYS);
            this.result = this.statement.getGeneratedKeys();
            if (this.result.next()) {
                idWidget = this.result.getInt(1);
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
     * @param login Le login saisit par l'utilisateur via la fenêtre de login.
     * @param password Le mot de passe saisit par l'utilisateur via la fenêtre de login.
     * @return True si il exite bien un utilisateur, False si il n'en existe aucun ou qu'une erreur est levée.
     */   
    public ResultSet connect_user(String login, String password) {
        this.requete = ("SELECT idUser, firstName, lastName, email, isAdmin FROM users WHERE login ='"+login+"' AND password ='"+password+"' AND isActive = 1");
        try {
            this.result = this.statement.executeQuery(requete);
        }
        catch (SQLException error) {
            System.out.println (error);
        }
        return (this.result);
    }
    
    /**
     * Supprime un widget dans la base de données.
     * @param idWidget
     * L'id du widget que l'utilisateur veut supprimer.
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
    
    /**
     * Récupère le contenu d'un widget depuis la base de données.
     * @param idWidget L'id du widget concerné.
     * @return Le contenu du wigdet peut être soit du texte (pour les post-it) soit un élément faisant partie d'un fichier .xml (pour les listes de tâches et les sondages).
     */
    public String getContentWidget(int idWidget) {
        String contentWidget = "NULL";
        this.requete = "SELECT contentWidget FROM widgets WHERE idWidget ="+idWidget;
        try {
            this.result = this.statement.executeQuery(this.requete);
            this.result.next();
            contentWidget = result.getString("contentWidget");
        }
        catch (SQLException error) {
            System.out.println ("Impossible de récupérer le contenu du widget ! "+error);
        }
        return (contentWidget);
    }
    
    /**
     * 
     * @param idUser
     * @return 
     */
    public ResultSet getMyPlugins(int idUser) {
        this.requete = "SELECT T.*, P.namePlugin, P.descriptionPlugin FROM telecharge T"
                    + " RIGHT JOIN plugins P ON T.idPlugin = P.idPlugin"
                    + " WHERE idUser = " + idUser
                    + " AND P.isValid = 1"
                    + " ORDER BY T.idStatutPlugin DESC";
        try {
            this.result = this.statement.executeQuery(this.requete);
        }
        catch (SQLException error) {
            System.out.println("Impossible de récupérer la liste des plugins !");
        }
        return (this.result);
    }
    
    /**
     * Récupère les widgets d'un dashboard.
     * @param idDashboard L'id du dashboard concerné.
     * @return Les données de chaque widget (id, contenu, position et dimensions) dans un ResultSet.
     */
    public ResultSet getWidgets(int idDashboard) {
        this.requete = "SELECT E.*, T.nomTypeWidget FROM widgets E, type_widget T WHERE idDashboard = "+idDashboard+" AND E.idTypeWidget = T.idTypeWidget";
        try {
            this.result = statement.executeQuery(requete);
        }
        catch (SQLException error) {
            System.out.println ("Impossible de récupérer les widgets ! "+error);
        }
        return (this.result);
    }
    
    /**
     * Récupère la liste des dashboards d'un utilisateur.
     * @param idUser L'id de l'utilisateur connecté.
     * @return Un ResultSet contenant les données de chaque dashboard : id, droits (administrateur ou non), si il est partagé ou non, le titre et la description.
     */
    public ResultSet getDashboards(int idUser) {
        this.requete = "SELECT U.idUser, U.idDashboard, U.isDashboardAdmin, D.titleDashboard, D.descriptionDashboard, D.isShared"
                    + " FROM utilise U RIGHT JOIN dashboard D ON D.idDashboard = U.idDashboard WHERE idUser = "+idUser;
        try {
            this.result = statement.executeQuery(requete);
        }
        catch (SQLException error) {
            System.out.println ("Impossible de récupérer la liste des dashboards !"+ error);
        } 
        return (this.result);
    }
    
    /**
     * Appellée lorsqu'un utilisateur demande la création d'un compte sur l'application. Elle vérifie qu'il n'existe pas déjà
     * un utilisateur utilisant ce login et ce mot de passe, sinon elle l'inscrit.
     * @param firstName Prénom saisi par l'utilisateur.
     * @param lastName Nom de famille saisi par l'utilisateur.
     * @param login Login choisit par l'utilisateur
     * @param password Mot de passe choisit par l'utilisateur.
     * @param email Email saisit par l'utilisateur.
     * @return True si l'utilisateur a bien été inscrit, False sinon.
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
    
    /**
     * 
     * @param idUser
     * @param idPlugin 
     * @param statut
     */
    public void setStatutPlugin(int idUser, int idPlugin, int statut) {
        this.requete = "UPDATE telecharge SET idStatutPlugin = "+statut
                      +" WHERE idPlugin = "+idPlugin
                      +" AND idUser = "+idUser;
        try {
            this.statement.executeUpdate(this.requete);
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible de modifier le statut du plugin !" +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * La fonction est appellée lorsque l'utilisateur clique sur le bouton "Enregistrer" (îcone de disquete) sur un widget.
     * Elle modifie le contenu d'un widget dans la base de données.
     * @param idWidget L'id du widget concerné.
     * @param contentWidget Le contenu du widget modifié.
     */
    public void updateWidget(int idWidget, String contentWidget) {
        this.requete = "UPDATE widgets SET contentWidget = '"+contentWidget+"' WHERE idWidget = "+idWidget;
        try {
            this.statement.executeUpdate(this.requete);
        }
        catch (SQLException error) {
            System.out.println ("Impossible de modifier le widget "+error); 
        }
    }
}