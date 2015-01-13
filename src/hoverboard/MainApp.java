package Hoverboard;
import windows.*;

public class MainApp {

    public static void main(String[] args) {
        Login_window login_window = new Login_window();
        BDD connexion = new BDD("root","root");
        connexion.connect_user("aflaesch","root");
        //home home_window = new home();
        
    }
}