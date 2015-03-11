package windows;

import hoverboard.BDD;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 */
public class ListeDashboard extends JPanel {
    
    protected int idUser = -1;
    protected BDD connexion = new BDD();
    
    public ListeDashboard(int idUser) {
        this.idUser = idUser;
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
                this.add(new DashboardPreview(idDashboard, titleDashboard, descriptionDashboard, isAdmin, isShared));
            }
        }
        catch (SQLException error) {
            System.out.println("Impossible d'afficher la liste des dashboards ! "+error);
        }
    }
}