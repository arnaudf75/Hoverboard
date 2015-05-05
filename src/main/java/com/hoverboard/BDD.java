package com.hoverboard;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        
    /**
     * Crée un objet de type Connection permettant d'exécuter des requêtes vers la base de données à partir du fichier data_jdbc.xml.
     * @return databaseConnection, l'objet permettant d'intéragir à la base de données.
     */ 
    private static Connection getConnection() {
        Connection dataBaseConnection = null;
        try {
            Document data_jdbc = new SAXBuilder().build(BDD.class.getResource("/data/data_jdbc_local.xml"));
            Element racine = data_jdbc.getRootElement();
            String databaseUrl= racine.getChild("dbUrl").getText();
            String user =  racine.getChild("login").getText();
            String password =  racine.getChild("password").getText();
            Class.forName(racine.getChild("driver").getText());
            dataBaseConnection = DriverManager.getConnection(databaseUrl, user, password);
        }
        catch (ClassNotFoundException | IOException | JDOMException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible de se connecter à la base de données !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        return (dataBaseConnection);
    }
    
    /**
     * Ajoute un dashboard à la base de données.
     * @param idUser L'id de l'utilisateur qui a crée le dashboard et qui en est donc l'administrateur.
     * @param titreDashboard Le titre du dashboard saisi par l'utilisateur.
     * @param descriptionDashboard La description du dashboard saisie par l'utilisateur.
     */
    public static void ajouteDashboard(int idUser, String titreDashboard, String descriptionDashboard) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("INSERT INTO dashboard VALUES(NULL, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, titreDashboard);
                statement.setString(2, descriptionDashboard);
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    int idDashboard = result.getInt(1);
                    statement.close();
                    statement = databaseConnection.prepareStatement("INSERT INTO utilise VALUES (?, ?, 1)");
                    statement.setInt(1, idUser);
                    statement.setInt(2, idDashboard);
                    statement.executeUpdate();
                    statement.close();
                    databaseConnection.close();
                }
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de créer le dashboard !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }
    
    /**
     * Ajoute un utilisateur à un dashboard partagé.
     * @param idDashboard L'id du dashboard auquel il faut rajouter l'utilisateur.
     * @param pseudoUser Le pseudo de l'utilisateur saisi par l'administrateur du dashboard. L'application va chercher l'id correspondant si le pseudo existe bien dans la base
     * de données, si il n'y a aucun utilisateur correspondant au pseudo saisi, l'application affiche un message d'erreur.
     * @return True si l'utilisateur a bien été rajouté au dashboard, False sinon.
     */
    public static boolean ajouteUserToDashboard(int idDashboard, String pseudoUser) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT idUser from users WHERE login = ? ");
                statement.setString(1, pseudoUser);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    int idUser = result.getInt("idUser");
                    statement = databaseConnection.prepareStatement("INSERT INTO utilise VALUES (?, ?, 0)");
                    statement.setInt(1, idUser);
                    statement.setInt(2, idDashboard);
                    statement.executeUpdate();
                    return (true);
                }
                else {
                    return (false);
                }
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible d'ajouter '"+pseudoUser+"' au dashboard ! "  +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
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
    public static int ajouteWidget(int positionX, int positionY, int height, int width, int idDashboard, String typeWidget) {
        Connection databaseConnection = BDD.getConnection();
        int idWidget = -1;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("INSERT INTO widgets VALUES (NULL, 'Nouveau widget', '', ?, ?, ?, ?, ?, 0, ?, NULL) ", PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, positionX);
                statement.setInt(2, positionY);
                statement.setInt(3, height);
                statement.setInt(4, width);
                statement.setString(5, typeWidget);
                statement.setInt(6, idDashboard);
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();
                if (result.next()) {
                    idWidget = result.getInt(1);
                }
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible d'ajouter le widget !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (idWidget);
    }
    
    /**
     * Est exécutée lorsqu'un utilisateur essaye de se connecter à l'application. Vérifie que le login et le mot de passe saisis sont valides.
     * @param login Le login saisit par l'utilisateur via la fenêtre de login.
     * @param password Le mot de passe saisit par l'utilisateur via la fenêtre de login.
     * @return Un ResulSet contenant l'id, le prénom, le nom l'adresse email et 1 si l'utilisateur est administrateur, 0 sinon.
     */   
    public static ResultSet connect_user(String login, String password) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT idUser, firstName, lastName, email, isAdmin FROM users WHERE login = ? AND password = ?");
                statement.setString(1, login);
                statement.setString(2, password);
                result = statement.executeQuery();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de vous connecter à l'application !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result);
    }
    
    /**
     * Supprime un dashboard de la base de données.
     * @param idDashboard L'ID du dashboard à supprimer.
     */
    public static void deleteDashboard(int idDashboard) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("DELETE FROM utilise WHERE idDashboard = ?");
                statement.setInt(1, idDashboard);
                statement.executeUpdate();
                statement = databaseConnection.prepareStatement("UPDATE widgets SET isDeleted = 1 WHERE idDashboard = ?");
                statement.setInt(1, idDashboard);
                statement.executeUpdate();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du dashboard !" + error, "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Supprime un widget dans la base de données.
     * @param idWidget L'id du widget que l'utilisateur veut supprimer.
     */
    public static void deleteWidget(int idWidget) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("UPDATE widgets SET isDeleted = 1 WHERE idWidget = ?");
                statement.setInt(1, idWidget);
                statement.executeUpdate();
                databaseConnection.close();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de supprimer le widget !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }        
    }
    
    /**
     * Récupère le contenu d'un widget depuis la base de données.
     * @param idWidget L'id du widget concerné.
     * @return Le contenu du wigdet peut être soit du texte (pour les post-it) soit un élément faisant partie d'un fichier .xml (pour les listes de tâches et les sondages).
     */
    public static String getContentWidget(int idWidget) {
        Connection databaseConnection = BDD.getConnection();
        String contentWidget = "VIDE";
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT contentWidget FROM widgets WHERE idWidget = ?");
                statement.setInt(1, idWidget);
                ResultSet result = statement.executeQuery();
                result.next();
                contentWidget = result.getString("contentWidget");
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer le contenu du widget !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }        
        return (contentWidget);
    }
    
    /**
     * Cette fonction est appellée lorsqu'il faut actualiser un ou plusieurs widgets dans un dashboard. 
     * @param idWidget L'ID du widget sélectionné.
     * @return Un ResulSet contenant le nom, les positions, et les dimensions du widget.
     */
    public static ResultSet getDataWidget(int idWidget) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT nameWidget, positionX, positionY, longueur, largeur  FROM widgets WHERE idWidget = ?");
                statement.setInt(1, idWidget);
                result = statement.executeQuery();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer le contenu du widget !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result);
    }
    
    /**
     * Récupère la liste des dashboards d'un utilisateur.
     * @param idUser L'id de l'utilisateur connecté.
     * @return Un ResultSet contenant les données de chaque dashboard : id, droits (administrateur ou non), si il est partagé ou non, le titre et la description.
     */
    public static ResultSet getDashboards(int idUser) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT U.idUser, U.idDashboard, U.isDashboardAdmin, D.titleDashboard, D.descriptionDashboard "
                 + "FROM utilise U RIGHT JOIN dashboard D ON D.idDashboard = U.idDashboard WHERE idUser = ? ORDER BY titleDashboard ASC");
                statement.setInt(1, idUser);
                result = statement.executeQuery(); 
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer la liste des dashboards !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            } 
        }
        return (result);
    }
    
    /**
     * Cette fonction est appellée lorsque l'afministrateur d'un dashboard veut administrer les droits des autres utilisateurs.
     * @param idDashboard L'ID du dashboard utilisé.
     * @return Un ResulSet contenant l'ID, le login, les droits d'administration (par rapport au dashboard choisi) des utilisateurs du dashboard.
     */
    public static ResultSet getDashboardUsers(int idDashboard) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT U.idUser, U.login, UT.isDashboardAdmin FROM users U, utilise UT WHERE idDashboard = ? AND UT.idUser = U.idUser ORDER BY login ASC");
                statement.setInt(1, idDashboard);
                result = statement.executeQuery();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer la liste des utilisateurs du dashboard !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result);
    }
    
    /**
     * Récupère la liste des plugins ajoutés depuis le site par l'utilisateur connecté.
     * @param idUser L'id de l'utilisateur connecté.
     * @return Un ResultSet contenant l'id, le nom, la description et le statut pour chaque plugin.
     */
    public static ResultSet getMyPlugins(int idUser) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT * FROM plugins P WHERE idPlugin IN (SELECT idplugin FROM installe WHERE idUser = ?)");
                statement.setInt(1, idUser);
                result = statement.executeQuery();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer la liste des plugins !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result);   
    }
    
    /**
     * Récupère la liste des plugins de l'utilisateur ainsi que leur version la plus récente et le chemin auquel il faut accèder pour les télécharger.
     * @param idUser L'ID de l'utilisateur connecté.
     * @return La liste des plugins de l'utilisateur.
     */
    public static ResultSet getMyPluginsVersion(int idUser) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT DISTINCT P.* , V.numVersion, V.pathToVersion FROM plugins P "
                                                                                + "INNER JOIN version V ON V.idPlugin = P.idPlugin " +
                                                                                  "WHERE P.idPlugin IN (SELECT idplugin FROM installe WHERE idUser =  ?) LIMIT 1");
                statement.setInt(1, idUser);
                result = statement.executeQuery();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer la liste des plugins !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result); 
    }
  
    /**
     * Récupère le widget ayant l'id idWidget
     * @param idWidget L'id du widget concerné.
     * @return Les données de chaque widget (id, contenu, position et dimensions) dans un ResultSet.
     */
    public static ResultSet getWidget(int idWidget) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT * FROM widgets WHERE idWidget = ?");
                statement.setInt(1, idWidget);
                result = statement.executeQuery();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer le widget !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result);
    }
    
    /**
     * Récupère les widgets d'un dashboard.
     * @param idDashboard L'id du dashboard concerné.
     * @return Les données de chaque widget (id, contenu, position et dimensions) dans un ResultSet.
     */
    public static ResultSet getWidgets(int idDashboard) {
        Connection databaseConnection = BDD.getConnection();
        ResultSet result = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT * FROM widgets WHERE idDashboard = ? AND isDeleted = 0");
                statement.setInt(1, idDashboard);
                result = statement.executeQuery();  
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de récupérer les widgets !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (result);
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
    public static boolean registerUser(String firstName, String lastName, String email, String login, String password) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT email, login FROM users WHERE email = ? OR login = ?");
                ResultSet result = statement.executeQuery();
                if (!result.isBeforeFirst()) {
                    statement.close();
                    statement = databaseConnection.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?, ?, ?, ?, 0) ");
                    statement.setString(1, firstName);
                    statement.setString(2, lastName);
                    statement.setString(3, email);
                    statement.setString(4, login);
                    statement.setString(5, password);
                    statement.executeUpdate();
                    statement.close();
                }
                else {
                    return (false);
                }
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de créer le compte !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (true);
    }
    
    /**
     * Supprime un utilisateur du dashboard.
     * @param idDashboard L'ID du dashboard utilisé.
     * @param idUser L'ID de l'utilisateur à supprimer.
     */
    public static void removeUserFromDashboard(int idDashboard, int idUser) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("DELETE FROM utilise WHERE idUser = ? AND idDashboard = ?");
                statement.setInt(1, idUser);
                statement.setInt(2, idDashboard);
                statement.executeUpdate();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de l'utilisateur du dashboard !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Cherche dans la base de données si un utilisateur existe avec cet email et change le mot de passe.
     * @param emailUser L'adresse email renseignée dans le formulaire.
     * @return True si toutes les actions ont été effectuées, False si l'utilisateur n'existe pas ou que la connexion à la base de données n'a pas fonctionnée.
     */  
    public static String resetPassword(String emailUser) {
        Connection databaseConnection = BDD.getConnection();
        String token = null;
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT idUser FROM users WHERE email = ?");
                statement.setString(1, emailUser);
                ResultSet result = statement.executeQuery();
                if (!result.isBeforeFirst()) {
                    return (null);
                }
                else {
                    SecureRandom random = new SecureRandom();
                    token = new BigInteger(130, random).toString(32);
                    result.next();
                    int idUser = result.getInt("idUser");
                    result.close();
                    statement.close();
                    statement = databaseConnection.prepareStatement("UPDATE users SET password = ? WHERE idUser = ?");
                    statement.setString(1, token);
                    statement.setInt(2, idUser);
                    statement.executeUpdate();
                }
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de changer le mot de passe !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return (token);
    }
    
    /**
     * Change les droits d'un utilisateur par rapport à un dashboard.
     * @param idUser L'ID de l'utilisateur à modifier.
     * @param idDashboard L'ID du dashboard utilisé.
     * @param isDashboardAdmin Les nouveaux droits d'administration (0 : Non Administrateur, 1 : Administrateur).
     */
    public static void setDashboardRights(int idUser, int idDashboard, int isDashboardAdmin) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("UPDATE utilise SET isDashboardAdmin = ? WHERE idUser = ? AND idDashboard = ?");
                statement.setInt(1, isDashboardAdmin);
                statement.setInt(2, idUser);
                statement.setInt(3, idDashboard);
                statement.executeUpdate();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de changer le statut de l'utilisateur !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Change le mot de passe dans la base de données par celui saisi par l'utilisateur si les deux sont différents
     * et si l'utilisateur a rentré son bon mot de passe actuel.
     * @param idUser L'id de l'utilisateur connecté.
     * @param old_password Le mot de passe actuel.
     * @param new_password Le nouveau mot de passe désiré.
     */
    public static void setNewPassword(int idUser, String old_password, String new_password) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("SELECT users.password FROM users WHERE idUser = ?");
                statement.setInt(1, idUser);
                ResultSet result = statement.executeQuery();
                result.next();
                statement.close();
                if (old_password.equals(result.getString("password"))) {
                    if (!result.getString("password").equals(new_password)) {
                        statement = databaseConnection.prepareStatement("UPDATE users SET password = ? WHERE idUser = ? ");
                        statement.setString(1, new_password);
                        statement.setInt(2, idUser);
                        statement.executeUpdate();
                    }
                    else {
                       JOptionPane.showMessageDialog(null, "L'ancien et le nouveau mot de passe sont identiques !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Votre mot de passe ne correspond pas à celui stocké dans la base de données ! ", "ERREUR", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de mettre à jour votre mot de passe !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        } 
    }
    
    /**
     * Change les informations de l'utilisateur dans la base de données.
     * @param idUser L'id de l'utilisateur connecté.
     * @param prenom Le nouveau prénom.
     * @param nom Le nouveau nom de famille.
     * @param email Le nouvel email.
     */
    public static void setNewPrivateInformations(int idUser, String prenom, String nom, String email) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("UPDATE users SET firstName = ?, lastName = ?, email = ? WHERE idUser = ?");
                statement.setString(1, prenom);
                statement.setString(2, nom);
                statement.setString(3, email);
                statement.setInt(4, idUser);
                statement.executeUpdate();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de mettre à jour vos coordonnées !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Cette fonction est appellée lorsque l'utilisateur clique sur le bouton "Enregistrer" (îcone de disquete) sur un widget.
     * Elle modifie le contenu d'un widget dans la base de données.
     * @param idWidget L'ID du widget concerné.
     * @param namewidget Le titre du widget.
     * @param contentWidget Le contenu du widget (une chaîne de caractère simple pour les post-it, une URL pour les images ou une chaine
     * de caractères au format .xml pour les liste de tâches et les sondages).
     * @param positionX La position horizontale du widget.
     * @param positionY La position verticale du widget.
     * @param height La hauteur du widget.
     * @param width La largeur du widget.
     */
    public static void updateWidget(int idWidget, String namewidget, String contentWidget, int positionX, int positionY, int height, int width) {
        Connection databaseConnection = BDD.getConnection();
        if (databaseConnection != null) {
            try {
                PreparedStatement statement = databaseConnection.prepareStatement("UPDATE widgets SET nameWidget = ?, contentWidget = ?, positionX = ?, positionY = ?, longueur = ?, largeur = ? WHERE idWidget = ?");
                statement.setString(1, namewidget);
                statement.setString(2, contentWidget);
                statement.setInt(3, positionX);
                statement.setInt(4, positionY);
                statement.setInt(5, height);
                statement.setInt(6, width);
                statement.setInt(7, idWidget);
                statement.executeUpdate();
            }
            catch (SQLException error) {
                JOptionPane.showMessageDialog(null, "Impossible de modifier le widget !", "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}