package hoverboard;

import windows.*;

import java.io.File;
import java.util.Hashtable;

public class MainApp {

    public static void main(String[] args) {
        
        File cookie = new File("src/ressources/cookie_login.xml");
        if (cookie.exists()) {
            ParserXml xmlParser = new ParserXml();
            Hashtable data_jdbc = xmlParser.getDataJDBC(xmlParser.getSax(), xmlParser.getDocument(), xmlParser.getRacine());        
            BDD connexion = new BDD(data_jdbc.get("dbUrl").toString(), data_jdbc.get("driver").toString(), data_jdbc.get("login").toString(), data_jdbc.get("password").toString());
            if ( xmlParser.isLoginValid(xmlParser.getSax(), xmlParser.getDocument(), xmlParser.getRacine(), connexion) ) {
                Home myHome = new Home(); 
            }
            else {
                Login login_window = new Login();
            }
        }
        else {
            Login login_window = new Login();
        }
         
    }
}