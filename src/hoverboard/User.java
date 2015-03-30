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
    
    /**
     * Renvoie l'id de l'utilisateur connecté.
     * @return L'id de l'utilisateur.
     */
    public int getIdUser() {
        return (this.idUser);
    }
    
    /**
     * Renvoie le login de l'utilisateur connecté.
     * @return Le login de l'utilisateur.
     */
    public String getLogin() {
        return (this.login_user);
    }
    
    /**
     * Renvoie le prénom de l'utilisateur connecté.
     * @return Le prénom de l'utilisateur.
     */
    public String getFirstName() {
        return (this.firstName_user);
    }
    
    /**
     * Renvoie le nom de l'utilisateur connecté.
     * @return Le nom de l'utilisateur.
     */
    public String getLastName() {
        return (this.lastName_user);
    }
    
    /**
     * Renvoie l'email de l'utilisateur connecté.
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return (this.email);
    }
    
    /**
     * Renvoie les droits d'administration de l'utilisateur connecté.
     * @return True si l'utilisateur est administrateur, False sinon.
     */
    public boolean getAdminRights() {
        return (this.isAdmin);
    }
    
    /**
     * Modifie le prénom de l'utilisateur connecté.
     * @param firstName Le nouveau prénom.
     */
    public void setFirstName(String firstName) {
        this.firstName_user = firstName;
    }
    
    /**
     * Modifie le nom de famille de l'utlisateur connecté.
     * @param lastName Le nouveau nom de famille.
     */
    public void setLastName(String lastName) {
        this.lastName_user = lastName;
    }
    
    /**
     * Modifie l'email de l'utilisateur connecté.
     * @param email Le nouvel email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}