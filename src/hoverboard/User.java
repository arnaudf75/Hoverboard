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
    
    /**
     * Crée un objet User à partir des informations récupérées dans la base de données après la connexion de l'utilisateur.
     * @param idUser
     * L'id de l'utilisateur.
     * @param firstName
     * Le prénom de l'utilisateur.
     * @param lastName
     * Le nom de l'utilisateur.
     * @param isAdmin
     * Vaut 1 si l'utilisateur est un administeur, 0 sinon.
     */
    public User(int idUser, String firstName, String lastName, int isAdmin) {
        this.idUser = idUser;
        this.firstName_user = firstName;
        this.lastName_user = lastName;
        if (isAdmin == 1) {
            this.isAdmin = true;
        }
    }
}