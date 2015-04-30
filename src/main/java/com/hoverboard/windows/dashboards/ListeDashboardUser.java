package com.hoverboard.windows.dashboards;

import com.hoverboard.BDD;
import com.hoverboard.User;
import com.hoverboard.windows.menus.themes.Theme;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 */
public class ListeDashboardUser extends JFrame {
    private JPanel main_container = new JPanel();
    
    public ListeDashboardUser(int idDashboard) {
        
        ResultSet userList = BDD.getDashboardUsers(idDashboard);
        
        try {
            userList.last();
            int numberRows = userList.getRow();
            userList.beforeFirst();
            this.main_container.setLayout(new GridLayout(numberRows, 1));
            while (userList.next()) {
                this.main_container.add(new DashboardUser(idDashboard, userList.getInt("idUser"), userList.getString("login"), userList.getInt("isDashboardAdmin")));
            } 
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible de récupérer la liste des utilisateurs du dashboard !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        
        this.setTitle("Liste des utilisateurs du dashboard");
        this.setContentPane(main_container);
        this.setIconImage(Theme.icone.getImage());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}