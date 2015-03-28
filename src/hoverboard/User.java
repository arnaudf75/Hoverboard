package hoverboard;

/**
 * User est la classe qui contient les informations concernant un utilisateur de l'application.
 * @author Arnaud
 */
public class User {
    private int idUser = -1;
    private String login_user = "";
    private String firstName_user = "";
    private String lastName_user = "";
    private String email = "";
    private boolean isAdmin = false;
    
    /**
     * Crée un objet User à partir des informations récupérées dans la base de données après la connexion de l'utilisateur.
     * @param idUser L'id de l'utilisateur.
     * @param login_user Le login de l'utilisateur.
     * @param firstName Le prénom de l'utilisateur.
     * @param lastName Le nom de l'utilisateur.
     * @param email L'email de l'utilisateur.
     * @param isAdmin Vaut 1 si l'utilisateur est un administeur, 0 sinon.
     */
    public User(int idUser, String login_user, String firstName, String lastName, String email, int isAdmin) {
        this.idUser = idUser;
        this.login_user = login_user;
        this.firstName_user = firstName;
        this.lastName_user = lastName;
        this.email = email;
        if (isAdmin == 1) {
            this.isAdmin = true;
        }
    }
    
    public int getIdUser() {
        return (this.idUser);
    }
    
    public String getLogin() {
        return (this.login_user);
    }
    
    public String getFirstName() {
        return (this.firstName_user);
    }
    
    public String getLastName() {
        return (this.lastName_user);
    }
    
    public String getEmail() {
        return (this.email);
    }
    
    public boolean getAdminRights() {
        return (this.isAdmin);
    }
}