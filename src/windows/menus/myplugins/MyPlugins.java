package windows.menus.myplugins;

import hoverboard.BDD;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * MyPlugins est la fenêtre affichant la liste des plugins choisis sur le site web de l'application par l'utilisateur.
 * @author Arnaud
 */
public class MyPlugins extends JFrame {
    private int idUser = -1;
    private int idPlugin = -1;
    private int statutPlugin = -1;
    private String nomPlugin = "";
    private String descriptionPlugin = "";
    private final JPanel main_container = new JPanel();
    private final BDD connexion = new BDD();
    
    /**
     * Affiche la liste des plugins de l'utilisateur à partir de son id.
     * @param idUser L'id de l'utilisateur connecté.
     */
    public MyPlugins(int idUser) {
        this.idUser = idUser;
        this.setSize(800, 400);
        ResultSet listePlugins = connexion.getMyPlugins(this.idUser);
        try {
            while (listePlugins.next()) {
                this.idPlugin = listePlugins.getInt("idPlugin");
                this.nomPlugin = listePlugins.getString("namePlugin");
                this.descriptionPlugin = listePlugins.getString("descriptionPlugin");
                this.statutPlugin = listePlugins.getInt("idStatutPlugin");
                this.main_container.add(new Plugins(this.idUser, this.idPlugin, this.nomPlugin, this.descriptionPlugin, this.statutPlugin));
            }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'afficher la liste de vos plugins ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
        this.setContentPane(main_container);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
