package windows.widgets;

import hoverboard.BDD;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Cavoleau
 */
public abstract class Widget extends JInternalFrame implements ActionListener, MouseMotionListener {

    protected int idDashboard;
    protected int idWidget;
    protected int height;
    protected int width;
    protected int positionX;
    protected int positionY;
    protected int oldX;
    protected int oldY;
    
    protected BDD connexion = new BDD();
    protected Dimension buttonSize = new Dimension(16,15);
    protected JButton save = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/save.png")));
    protected JButton refresh = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/refresh.png")));
    protected JButton del = new JButton(new ImageIcon(this.getClass().getClassLoader().getResource("ressources/images/delete.png")));
    protected JPanel buttons = new JPanel();
    protected JPanel settings = new JPanel();
    protected JPanel content = new JPanel();
    
    @SuppressWarnings("LeakingThisInConstructor")
    /**
     * Constructeur de la classe abstraite Widget
     */
    public Widget(){
        super();
        this.positionX = 0;
        this.positionY = 0;
        this.save.setPreferredSize(buttonSize);
        this.refresh.setPreferredSize(buttonSize);
        this.del.setPreferredSize(buttonSize);
        save.addActionListener(this);
        refresh.addActionListener(this);
        del.addActionListener(this);
        buttons.add(save);
        buttons.add(refresh);
        buttons.add(del);
        settings.setLayout(new BorderLayout());
        settings.add(buttons, BorderLayout.EAST);
        this.add(settings, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);
        this.setResizable(true);
        this.setVisible(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null); 
        settings.addMouseMotionListener(this);
        settings.addMouseListener(
        new MouseListener(){
            @Override
            public void mousePressed(MouseEvent event) {
                oldX=event.getX();
                oldY=event.getY();
            }
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        }
        );
    }
    
    public Widget(int idWidget, int positionX, int positionY, int height, int width)
    {
        super();
        this.idWidget = idWidget;
        this.height=height;
        this.width=width;
        this.positionX=positionX;
        this.positionY=positionY;
        this.setBounds(positionX, positionY, this.width, this.height);
        
        this.save.setPreferredSize(buttonSize);
        this.refresh.setPreferredSize(buttonSize);
        this.del.setPreferredSize(buttonSize);
        save.addActionListener(this);
        refresh.addActionListener(this);
        del.addActionListener(this);
        buttons.add(save);
        buttons.add(refresh);
        buttons.add(del);
        settings.setLayout(new BorderLayout());
        settings.add(buttons, BorderLayout.EAST);
        this.add(settings, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);
        this.setResizable(true);
        this.setVisible(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null); 
        settings.addMouseMotionListener(this);
        settings.addMouseListener(
        new MouseListener(){
            @Override
            public void mousePressed(MouseEvent event) {
                oldX=event.getX();
                oldY=event.getY();
            }
            @Override public void mouseClicked(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        }
        );
    }
    
    @Override
    /**
     * Modifie la position du widget lorsque l'utilisateur clique sur la barre du haut et bouge le pointeur de la souris.
     * @param event
     * L'action qui vient de se produire, en l'occurence le déplacement du pointeur de la souris
     * après que l'utilisateur ait cliqué sur le haut du widget.
     */
    public void mouseDragged(MouseEvent event) {
       // if (contains(e.getX(),e.getY())) { A utiliser pour que le post it reste dans le cadre
            //System.out.println(oldX+"  --- "+oldY);
            positionX += event.getX()-oldX;
            positionY += event.getY()-oldY;
            setBounds(positionX,positionY,width,height);
       //} 
    }
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        //mise a jour sur la BDD
        if (source == save) {
            this.save();
        }
        //rechercher la derniere version sur la BDD
        else if (source == refresh) {
            this.refresh();
        }
        //suppression du postit
        else if (source == del){
            int option = JOptionPane.showConfirmDialog(null, "Êtes vous sûr de bien vouloir supprimer ce widget ?",
            "Confirmez la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (option == JOptionPane.OK_OPTION) {
                this.dispose();
                this.connexion.deleteWidget(this.idWidget);
            }
        }
    }
    
    public void refresh(){
        System.out.println("id:"+this.idWidget+"positionX:"+this.connexion.getFieldWidget(this.idWidget, "positionX"));
        this.positionX=Integer.parseInt(this.connexion.getFieldWidget(this.idWidget, "positionX"));
        this.positionY=Integer.parseInt(this.connexion.getFieldWidget(this.idWidget, "positionY"));
        this.height=Integer.parseInt(this.connexion.getFieldWidget(this.idWidget, "longueur"));
        this.width=Integer.parseInt(this.connexion.getFieldWidget(this.idWidget, "largeur"));
        this.setBounds(positionX, positionY, width, height);
    }
    public void save(){
        
    }
    
    /**
     * 
     * @param event 
     */
    @Override
    public void mouseMoved(MouseEvent event) {}

        //System.out.println ("Ici update"); 
        // POUR LE LOCAL : Je récupère le nouveau contenu du JTextField, je l'envoie au fichier .xml correspondant
        // POUR LE ONLINE : Ensuite je fais un Update BDD SET content = content
}