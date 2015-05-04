package windows;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe ListeDashboard affiche dans une fenêtre la liste des dashboards d'un utilisateur.
 * @author Arnaud
 */
public class ListeDashboard extends Home {

    /**
     * Crée une fenêtre contenant la liste des dashboard à partir de l'id de l'utilisateur connecté.
     * @param idUser L'id de l'utilisateur connecté.
     */
    public ListeDashboard(int idUser) {
        this.idUser = idUser;
        this.setTitle("Choisissez un dashboard");
        ResultSet listeDashboard = connexion.getDashboards(this.idUser);
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
                this.add(new DashboardPreview(idUser, idDashboard, titleDashboard, descriptionDashboard, isAdmin, isShared));
            }
            this.revalidate();
        }
        catch (SQLException error) {
            System.out.println("Impossible d'afficher la liste des dashboards ! "+error);
        }
    }
}