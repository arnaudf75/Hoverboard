package com.hoverboard;

/**
 * User est la classe qui contient les informations concernant un utilisateur de l'application.
 * @author Arnaud
 */
public class User {
    private static int idUser = -1;
    private static String login_user = "";
    private static String firstName_user = "";
    private static String lastName_user = "";
    private static String email = "";
    private static boolean isAdmin = false;
    
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
        User.idUser = idUser;
        User.login_user = login_user;
        User.firstName_user = firstName;
        User.lastName_user = lastName;
        User.email = email;
        if (isAdmin == 1) {
            User.isAdmin = true;
        }
    }
    
    /**
     * Renvoie l'id de l'utilisateur connecté.
     * @return L'id de l'utilisateur.
     */
    public static int getIdUser() {
        return (User.idUser);
    }
    
    /**
     * Renvoie le login de l'utilisateur connecté.
     * @return Le login de l'utilisateur.
     */
    public static String getLogin() {
        return (User.login_user);
    }
    
    /**
     * Renvoie le prénom de l'utilisateur connecté.
     * @return Le prénom de l'utilisateur.
     */
    public static String getFirstName() {
        return (User.firstName_user);
    }
    
    /**
     * Renvoie le nom de l'utilisateur connecté.
     * @return Le nom de l'utilisateur.
     */
    public static String getLastName() {
        return (User.lastName_user);
    }
    
    /**
     * Renvoie l'email de l'utilisateur connecté.
     * @return L'email de l'utilisateur.
     */
    public static String getEmail() {
        return (User.email);
    }
    
    /**
     * Renvoie les droits d'administration de l'utilisateur connecté.
     * @return True si l'utilisateur est administrateur, False sinon.
     */
    public static boolean getAdminRights() {
        return (User.isAdmin);
    }
    
    /**
     * Modifie le prénom de l'utilisateur connecté.
     * @param firstName Le nouveau prénom.
     */
    public static void setFirstName(String firstName) {
        User.firstName_user = firstName;
    }
    
    /**
     * Modifie le nom de famille de l'utlisateur connecté.
     * @param lastName Le nouveau nom de famille.
     */
    public static void setLastName(String lastName) {
        User.lastName_user = lastName;
    }
    
    /**
     * Modifie l'email de l'utilisateur connecté.
     * @param email Le nouvel email.
     */
    public static void setEmail(String email) {
        User.email = email;
    }
}