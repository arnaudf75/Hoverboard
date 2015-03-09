package hoverboard;

import windows.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * MainApp est la classe principale qui s'exécute au démarrage de l'application.
 * @author Arnaud
 */

public class MainApp {

    public static void main(String[] args) {
        ParserXml xmlParser = new ParserXml();
        BDD connexion = new BDD();
        
        File cookie = new File("src/ressources/cookie_login.xml");
        if (cookie.exists()) {
            
            HashMap data_cookie = xmlParser.getDataCookie();
            String login = data_cookie.get("loginUser").toString();
            String password = data_cookie.get("passwordUser").toString();
            ResultSet isUser = connexion.connect_user(login,password);
            try {
                if (!isUser.isBeforeFirst()) {
                    System.out.println("rien");
                    Login login_window = new Login();
                }
                else {
                    isUser.next();
                    int idUser = isUser.getInt("idUser");
                    Home myHome = new Home(idUser);
                }
            }
            catch (SQLException error) {
                System.out.println("Impossible de récupérer votre idUser ! "+error);
            }
        }
        else {
            Login login_window = new Login();
        }
    }
}