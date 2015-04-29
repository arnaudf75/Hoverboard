package hoverboard;

import windows.Home;
import windows.Login;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;

/**
 * MainApp est la classe principale qui s'exécute au démarrage de l'application.
 * @author Arnaud
 */
public class MainApp {
    
    /**
     * Fonction principale appellée au lancement du programme. Elle lance une fenêtre de connexion ou, si un cookie existe et est valide,
     * connecte l'utilisateur et affiche sa fenêtre d'accueil.
     * @param args Les paramètres éventuellement saisis depuis la console.
     */
    public static void main(String[] args) {
        
        try {
            BDD connexion = new BDD();
            File cookie = new File("userData/cookie_login.xml");
            if (cookie.exists()) {
                Document data_cookie = new SAXBuilder().build(cookie);
                Element racine = data_cookie.getRootElement();
                String login = racine.getChild("login").getText();
                String password = racine.getChild("password").getText();
                ResultSet isUser = connexion.connect_user(login,password);
                if (!isUser.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "Aucun utilisateur avec ce login et ce mot de passe !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                    Login login_window = new Login();
                }
                else {
                    isUser.next();
                    int idUser = isUser.getInt("idUser");
                    String firstName = isUser.getString("firstName");
                    String lastName = isUser.getString("lastName");
                    String email = isUser.getString("email");
                    int isAdmin = isUser.getInt("isAdmin");
                    Home homeWindow = new Home(new User(idUser, login, firstName, lastName, email, isAdmin));
                }
            }
            else {
                Login login_window = new Login();
            }
        }
        catch (IOException | JDOMException | SQLException error) {
            JOptionPane.showMessageDialog(null, "Le cookie de login n'existe pas ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
            Login login_window = new Login();
        }
    }
}