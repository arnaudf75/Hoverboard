package windows.menus.myplugins;

import hoverboard.BDD;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Plugins est la classe qui affiche dans un JPanel les informations sur chacun des plugins de l'utilisateur.
 * @author Arnaud
 */
public class Plugins extends JPanel implements ActionListener {
    protected BDD connexion = new BDD();
    private int idUser = -1;
    private int idPlugin = -1;
    private final JButton activate = new JButton("Activer");
    private final JButton deactivate = new JButton("Désactiver");
    private final JLabel labelNom = new JLabel("");
    private final JLabel labelDescription = new JLabel("");
    
    /**
     * Crée un JPanel affichant les informations relatives à un plugin.
     * @param idUser L'id de l'utilisateur connecté.
     * @param idPlugin L'id du plugin affiché.
     * @param namePlugin Le nom du plugin affiché.
     * @param descriptionPlugin La description du plugin affiché.
     * @param statutPlugin Le statut du plugin : si il est désactivé, en cours de téléchargement ou activé.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Plugins(int idUser, int idPlugin, String namePlugin, String descriptionPlugin, int statutPlugin) {
        this.idUser = idUser;
        this.idPlugin = idPlugin;
        this.labelNom.setText(namePlugin);
        this.labelDescription.setText(descriptionPlugin);
        this.labelNom.setForeground(Color.RED);
        this.labelDescription.setForeground(Color.BLUE);
        
        this.activate.addActionListener(this);
        this.deactivate.addActionListener(this);
        
        this.add(labelNom);
        this.add(labelDescription);
        
        if (statutPlugin == 1) {
            this.add(activate);
        }
        else if (statutPlugin == 3) {
            this.add(deactivate);
        }
        else {
            this.add(new JLabel("En cours de téléchargement..."));
        }
    }
    
    /**
     * Active ou désactive le plugin sélectionné.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if (source == activate) {
            this.remove(activate);
            this.add(deactivate);
            this.connexion.setStatutPlugin(idUser, idPlugin, 3);
            this.revalidate();
        }
        else if (source == deactivate) {
            this.remove(deactivate);
            this.add(activate);
            this.connexion.setStatutPlugin(idUser, idPlugin, 1);
            this.revalidate();
        }
    }
}