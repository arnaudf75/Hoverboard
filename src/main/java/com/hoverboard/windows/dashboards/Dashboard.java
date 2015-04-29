package com.hoverboard.windows.dashboards;

import com.hoverboard.BDD;
import com.hoverboard.User;
import com.hoverboard.windows.menus.newdashboard.AddMates;
import com.hoverboard.windows.menus.themes.Theme;
import com.hoverboard.windows.widgets.ImagePostIt;
import com.hoverboard.windows.widgets.Poll;
import com.hoverboard.windows.widgets.PollCreator;
import com.hoverboard.windows.widgets.PostIt;
import com.hoverboard.windows.widgets.ToDoList;
import com.hoverboard.windows.widgets.Widget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Dashboard est la classe qui permet d'afficher et de créer les widgets.
 * @author Arnaud
 */
public class Dashboard extends JPanel implements ActionListener {
    private int idDashboard = -1;
    private final BDD connexion = new BDD();
    public static final ArrayList<Widget> listWidgets = new ArrayList();
    private final Dimension buttonSize = new Dimension(32,32);
    private final JButton new_postit = new JButton(new ImageIcon(this.getClass().getResource("/images/postit_icon.png")));
    private final JButton new_imagePostIt = new JButton(new ImageIcon(this.getClass().getResource("/images/image_icon.png")));
    private final JButton new_tasklist = new JButton(new ImageIcon(this.getClass().getResource("/images/tasklist_icon.png")));
    private final JButton new_poll = new JButton(new ImageIcon(this.getClass().getResource("/images/poll_icon.png")));
    private final JButton add_users = new JButton(new ImageIcon(this.getClass().getResource("/images/addMates.png")));
    private final JButton refreshAllWidgets = new JButton(new ImageIcon(this.getClass().getResource("/images/refreshAll.png")));
    private final JButton saveAllWidgets = new JButton(new ImageIcon(this.getClass().getResource("/images/saveAll.png")));
    private final JDesktopPane widget_container = new JDesktopPane() {
        @Override
        public void paintComponent(Graphics graphics) {    
            super.paintComponent(graphics);
            Graphics2D graphic2D = (Graphics2D) graphics;
            Image image = Theme.background.getImage();
            graphic2D.drawImage(image, 0, 0, getSize().width, getSize().height, this);
        }
    };
    private final JPanel topLeftSide_container = new JPanel();
    private final JPanel topRightSide_container = new JPanel();
    private final JPanel top_container = new JPanel();
        
    /**
     * Crée le dashboard complet avec les widgets qui y sont associés.
     * @param idDashboard L'id du dashboard choisi dans la liste des dashboards de la page d'accueil.
     * @param titreDashboard Le titre affiché en haut de la fenêtre.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Dashboard(int idDashboard, String titreDashboard) {
        this.idDashboard = idDashboard;
        this.setLayout(new BorderLayout());
        this.top_container.setLayout(new BorderLayout());
        
        this.add_users.addActionListener(this);
        this.new_postit.addActionListener(this);
        this.new_imagePostIt.addActionListener(this);
        this.new_tasklist.addActionListener(this);
        this.new_poll.addActionListener(this);
        this.refreshAllWidgets.addActionListener(this);
        this.saveAllWidgets.addActionListener(this);
        
        this.new_postit.setPreferredSize(buttonSize);
        this.new_imagePostIt.setPreferredSize(buttonSize);
        this.new_tasklist.setPreferredSize(buttonSize);
        this.new_poll.setPreferredSize(buttonSize);
        this.add_users.setPreferredSize(buttonSize);
        this.refreshAllWidgets.setPreferredSize(buttonSize);
        this.saveAllWidgets.setPreferredSize(buttonSize);
        
        this.new_postit.setToolTipText("Nouveau post-it");
        this.new_imagePostIt.setToolTipText("Nouvelle image");
        this.new_tasklist.setToolTipText("Nouvelle liste de tâches");
        this.new_poll.setToolTipText("Nouveau sondage");
        this.add_users.setToolTipText("Ajouter des utilisateurs à ce dashboard");
        this.refreshAllWidgets.setToolTipText("Rafraîchir tous les widgets");
        this.saveAllWidgets.setToolTipText("Enregistrer tous les widgets");
        
        this.topLeftSide_container.add(new_postit);
        this.topLeftSide_container.add(new_imagePostIt);
        this.topLeftSide_container.add(new_tasklist);
        this.topLeftSide_container.add(new_poll);
        this.topLeftSide_container.add(add_users);
        
        this.topRightSide_container.add(refreshAllWidgets);
        this.topRightSide_container.add(saveAllWidgets);
        
        this.top_container.add(topLeftSide_container, BorderLayout.WEST);
        this.top_container.add(topRightSide_container, BorderLayout.EAST);
        this.add(widget_container, BorderLayout.CENTER);
        
        this.afficheWidgets(this.idDashboard);
        
        this.add(top_container, BorderLayout.NORTH);

        this.revalidate();
    }
    
    /**
     * 
     * @param idDashboard 
     */
    public void afficheWidgets(int idDashboard) {
        ResultSet listeWidgets = this.connexion.getWidgets(idDashboard);
        try {
            while (listeWidgets.next()) {
                switch (listeWidgets.getString("typeWidget")) {
                    case "POSTIT" : {
                        this.widget_container.add(new PostIt(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                                                listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur")));
                        break;
                    }
                    case "TODOLIST" : {
                        this.widget_container.add(new ToDoList(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                                    listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur")));
                        break;
                    }
                    case "POLL" : {
                        String XMLContent = listeWidgets.getString("contentWidget");
                        if (XMLContent.isEmpty()) {
                            this.widget_container.add(new PollCreator(listeWidgets.getInt("idWidget"), "<poll published='false'><questionlist></questionlist></poll>", listeWidgets.getInt("positionX"),
                                   listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur"), User.getIdUser()));
                            org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
                            try {
                               Document doc = saxBuilder.build(new StringReader(XMLContent));
                               Element poll = doc.getRootElement();
                               if(Boolean.valueOf(poll.getAttributeValue("published"))){
                                   this.widget_container.add(new Poll(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                   listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur"), User.getIdUser()));
                               }
                               else {
                                   this.widget_container.add(new PollCreator(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                   listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur"), User.getIdUser()));
                               }
                            }
                            catch (IOException | JDOMException error) {
                                JOptionPane.showMessageDialog(null, "handle Exception " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();
                            try {
                               Document doc = saxBuilder.build(new StringReader(XMLContent));
                               Element poll = doc.getRootElement();
                               if(Boolean.valueOf(poll.getAttributeValue("published"))){
                                   this.widget_container.add(new Poll(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                   listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur"), User.getIdUser()));
                               }
                               else {
                                   this.widget_container.add(new PollCreator(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                   listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur"), User.getIdUser()));
                               }
                            }
                            catch (IOException | JDOMException error) {
                                JOptionPane.showMessageDialog(null, "handle Exception " +error, "ERREUR", JOptionPane.ERROR_MESSAGE);
                            } 

                        }
                        break;
                    }
                    case "IMAGE" : {
                        this.widget_container.add(new ImagePostIt(listeWidgets.getInt("idWidget"), listeWidgets.getString("contentWidget"), listeWidgets.getInt("positionX"),
                                                    listeWidgets.getInt("positionY"), listeWidgets.getInt("longueur"), listeWidgets.getInt("largeur")));
                        break;
                    }
                    default : {
                        JOptionPane.showMessageDialog(null, "Type de widget non pris en charge !", "ERREUR", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
            }
        }
        catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Impossible d'afficher les widgets ! "+error, "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * En fonction de l'action, peut créer un widget ou revenir à la page d'accueil.
     * @param event L'action qui vient de se produire (bouton cliqué).
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == add_users) {
            AddMates addUsersToDashboard = new AddMates(this.idDashboard);
        }
        else {
            if (source == new_postit) {
                this.widget_container.add(new PostIt(this.idDashboard));
            }
            else if (source == new_imagePostIt) {
                this.widget_container.add(new ImagePostIt(this.idDashboard));
            }
            else if (source == new_tasklist) {
                this.widget_container.add(new ToDoList(this.idDashboard));
            }
            else if (source == new_poll){
                this.widget_container.add(new PollCreator(this.idDashboard, User.getIdUser()));
            }
            else if (source == refreshAllWidgets) {
                for (Widget widget : listWidgets) {
                    widget.refresh();
                }
            }
            else if (source == saveAllWidgets) {
                for (Widget widget : listWidgets) {
                    widget.save();
                }
            }
            this.revalidate();
        }
    }
}
