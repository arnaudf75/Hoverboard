package Hoverboard;
import windows.*;

public class Hoverboard {

    public static void main(String[] args) {
        login login_window = new login();
        home home_window = new home();
        BDD connexion = new BDD("root","root");
        
        System.out.println(login_window.login.getText());
    }
}