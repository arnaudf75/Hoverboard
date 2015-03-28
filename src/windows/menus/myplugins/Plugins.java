package windows.menus.myplugins;

import hoverboard.BDD;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
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
     * 
     * @param idUser
     * @param idPlugin
     * @param namePlugin
     * @param descriptionPlugin
     * @param statutPlugin 
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Plugins(int idUser, int idPlugin, String namePlugin, String descriptionPlugin, int statutPlugin) {
        this.idUser = idUser;
        this.idPlugin = idPlugin;
        this.idUser = idUser;
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