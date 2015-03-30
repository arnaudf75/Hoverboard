package windows.dashboards;

import hoverboard.User;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * La classe ListeDashboard affiche dans une fenêtre la liste des dashboards d'un utilisateur.
 * @author Arnaud
 */
public class ListeDashboard extends Home {

    /**
     * Crée une fenêtre contenant la liste des dashboard à partir de l'id de l'utilisateur connecté.
     * @param utilisateur Un objet User contenant les informations de l'utilisateur connecté (id, nom, prénom, email et droits d'administration si existants).
     */
    public ListeDashboard(User utilisateur) {
        this.utilisateur = utilisateur;
        this.setTitle("Choisissez un dashboard");
        ResultSet listeDashboard = connexion.getDashboards(this.utilisateur.getIdUser());
        try {
            listeDashboard.last();
            int numberRows = listeDashboard.getRow()/2;
            listeDashboard.beforeFirst();
            this.setLayout(new GridLayout(numberRows,2));
            while (listeDashboard.next()) {
                int idDashboard = listeDashboard.getInt("idDashboard");
                int isShared = listeDashboard.getInt("isShared");
                int isAdmin = listeDashboard.getInt("isDashboardAdmin");
                String titleDashboard = listeDashboard.getString("titleDashboard");
                String descriptionDashboard = listeDashboard.getString("descriptionDashboard");
                this.add(new DashboardPreview(this.utilisateur, idDashboard, titleDashboard, descriptionDashboard, isAdmin, isShared));
            }
            this.revalidate();
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste des dashboards ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
}