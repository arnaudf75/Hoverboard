package windows.widgets;

import windows.dashboards.Dashboard;

import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Image est la classe affichant une image dans un widget après sélection de celle-ci depuis une URL. L'image doit être hébergée sur un site en ligne pour l'afficher sur
 * tous les postes des utilisateurs du dashboard.
 * @author Arnaud
 */
public class ImagePostIt extends Widget {
    
    private String pathToImage = "";
    private final JButton validate = new JButton("Valider");
    private final JLabel imageLabel = new JLabel();
    private final JLabel linkLabel = new JLabel("Lien HTTP vers l'image :");
    private final JPanel middle_container = new JPanel();
    private final JTextField imageField = new JTextField("");

    /**
     * Crée un widget demandant à l'utilisateur de saisir l'URL de l'image qu'il veut ajouter.
     * @param idDashboard Le dashboard sur lequel se trouve l'utilisateur.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ImagePostIt(int idDashboard) {
        super();
        this.height = 300;
        this.width = 300;
        this.setBounds(this.positionX, this.positionY, this.width, this.height);
        this.validate.addActionListener(this);
        this.middle_container.setLayout(new GridLayout(2,1));
        this.middle_container.add(linkLabel);
        this.middle_container.add(imageField);
        this.middle_container.add(validate);
        this.content.add(middle_container);
        this.idDashboard = idDashboard;
        this.idWidget = this.connexion.ajouteWidget(this.positionX, this.positionY, this.height, this.width, this.idDashboard, "IMAGE");
        Dashboard.listWidgets.add(this);
        this.revalidate();
    }
    
    /**
     * Ajoute un widget déjà existant au dashboard lorsque l'utilisateur s'y connecte. Si l'URL de l'image est vide dans la base de données, un champ de saisie est affiché à la place.
     * @param idWidget L'ID du widget.
     * @param pathToImage L'URL de l'image qu'il faut afficher, elle est soit complète soit vide.
     * @param positionX La position horizontale du widget.
     * @param positionY La position verticale du widget.
     * @param height La hauteur du widget.
     * @param width La largeur du widget.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public ImagePostIt(int idWidget, String pathToImage, int positionX, int positionY, int height, int width) {
        super(idWidget, positionX, positionY, height, width);
        if (pathToImage.equals("")) {
            this.validate.addActionListener(this);
            this.middle_container.setLayout(new GridLayout(2,1));
            this.middle_container.add(linkLabel);
            this.middle_container.add(imageField);
            this.middle_container.add(validate);
            this.content.add(middle_container);
        }
        else {
            try {
                BufferedImage image = ImageIO.read(new URL(pathToImage));
                this.content.add(imageLabel);
                this.imageLabel.setIcon(new ImageIcon(image));
                this.pathToImage = pathToImage;
            }
            catch (IOException error) {
                JOptionPane.showMessageDialog(null, "Impossible de mettre à jour l'image de ce widget ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        Dashboard.listWidgets.add(this);
        this.revalidate();
    }
    
    /**
     * Cette fonction va actualiser le widget à partir des informations présentes dans la base de données. Si il existe une nouvelle URL valide, l'image actuelle sera mise à jour.
     */
    @Override
    public void refresh(){
        super.refresh();
        this.pathToImage = this.connexion.getContentWidget(this.idWidget);
        if (!this.pathToImage.equals("")) {
            try {
                BufferedImage image = ImageIO.read(new URL(this.pathToImage));
                this.content.removeAll();
                this.content.add(imageLabel);
                this.imageLabel.setIcon(new ImageIcon(image));
                this.repaint();
            }
            catch (IllegalArgumentException | IOException | NullPointerException error) {
                JOptionPane.showMessageDialog(null, "Impossible de mettre à jour l'image de ce widget ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Cette fonction est exécutée uniquement si l'URL saisie est valide. Elle sauvegarde celle-ci ainsi que les dimensions et les coordonnées du widget.
     */
    @Override
    public void save() {
        super.save();
        if (!this.pathToImage.equals("")) {
            this.connexion.updateWidget(this.idWidget, this.pathToImage, this.positionX, this.positionY, this.height, this.width);
        }
        else {
            JOptionPane.showMessageDialog(null, "Vous devez saisir une URL valide pour sauvegarder ce widget ! ", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Effectue une action en fonction du bouton cliqué : affiche l'image à partir de l'URL saisie si celle-ci est valide, met à jour le widget, le sauvegarde ou le supprime.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed (ActionEvent event) {
        Object source = event.getSource();
        if (source == validate) {
            this.pathToImage = this.imageField.getText();
            try {
                BufferedImage image = ImageIO.read(new URL(this.pathToImage));
                this.imageLabel.setIcon(new ImageIcon(image));
                this.content.remove(middle_container);
                this.content.add(imageLabel);
                this.revalidate();
            }
            catch (NullPointerException | IllegalArgumentException | IOException error) {
                JOptionPane.showMessageDialog(null, "Impossible d'ajouter une image à ce widget ! " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (source == refresh) {
            this.refresh();
        }
        else if (source == save) {
            this.save();
        }
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer cette image ?",
                "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    }
}