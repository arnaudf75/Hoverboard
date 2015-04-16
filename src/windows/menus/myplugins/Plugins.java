package windows.menus.myplugins;

import hoverboard.BDD;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Plugins est la classe qui affiche dans un JPanel les informations sur chacun des plugins de l'utilisateur.
 * @author Arnaud
 */
public class Plugins extends JPanel implements ActionListener {
    protected BDD connexion = new BDD();
    private int idUser = -1;
    private int idPlugin = -1;
    private int idVersion = -1;
    
    private final JButton activate = new JButton("Activer");
    private final JButton deactivate = new JButton("Désactiver");
    private final JButton delete = new JButton ("Supprimer");
    private final JLabel labelNom = new JLabel("");
    private final JLabel labelDateUpdate = new JLabel("");
    private final JLabel labelNumeroVersion = new JLabel("");
    private final JTextArea areaDescription = new JTextArea("");
    
    /**
     * Crée un JPanel affichant les informations relatives à un plugin.
     * @param idUser L'id de l'utilisateur connecté.
     * @param idPlugin L'id du plugin affiché.
     * @param namePlugin Le nom du plugin affiché.
     * @param descriptionPlugin La description du plugin affiché.
     * @param statutPlugin Le statut du plugin : si il est désactivé, en cours de téléchargement ou activé.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Plugins(int idUser, int idPlugin, int idVersion, String namePlugin, String numeroVersion, String dateUpdate, String descriptionPlugin, int statutPlugin) {        
        this.idUser = idUser;
        this.idPlugin = idPlugin;
        this.idVersion = idVersion;
        
        this.setLayout(new GridLayout(1,6));
        
        this.labelNom.setText(namePlugin);
        this.labelNumeroVersion.setText(numeroVersion);
        this.labelDateUpdate.setText(dateUpdate);
        this.areaDescription.setText(descriptionPlugin);
        this.areaDescription.setWrapStyleWord(true);
        this.areaDescription.setLineWrap(true);
        this.areaDescription.setEditable(false);
        this.areaDescription.setOpaque(false);
        
        this.activate.addActionListener(this);
        this.deactivate.addActionListener(this);
        this.delete.addActionListener(this);
        
        this.add(labelNom);
        this.add(labelNumeroVersion);
        this.add(labelDateUpdate);
        this.add(areaDescription);
        
        if (statutPlugin == 1) {
            this.add(activate);
        }
        else if (statutPlugin == 3) {
            this.add(deactivate);
        }
        else {
            this.add(new JLabel("En cours de téléchargement..."));
        }
        this.add(delete);
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
        }
        else if (source == deactivate) {
            this.remove(deactivate);
            this.add(activate);
            this.connexion.setStatutPlugin(idUser, idPlugin, 1);
        }
        else if (source == delete) {
            // suppression du .jar
            // suppression de la version dans la base de données
        }
        this.revalidate();
    }
}