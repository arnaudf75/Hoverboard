package hoverboard;

/**
 * User est la classe qui contient les informations concernant un utilisateur de l'application.
 * @author Arnaud
 */

public class User {
    private int idUser = -1;
    private String firstName_user = null;
    private String lastName_user = null;
    private boolean isAdmin = false;
    
    public User(int idUser, String firstName, String lastName, int isAdmin) {
        this.idUser = idUser;
        this.firstName_user = firstName;
        this.lastName_user = lastName;
        if (isAdmin == 1) {
            this.isAdmin = true;
        }
    }
}
