package windows.widgets;

import hoverboard.BDD;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
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
    public Widget(){
        super();
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
    }
    
    /**
     * Modifie la position du widget lorsque l'utilisateur clique sur la barre du haut et bouge le pointeur de la souris.
     * @param event
     * L'action qui vient de se produire, en l'occurence le déplacement du pointeur de la souris
     * après que l'utilisateur ait cliqué sur le haut du widget.
     */
    @Override
    public void mouseDragged(MouseEvent event) {
       // if (contains(e.getX(),e.getY())) { A utiliser pour que le post it reste dans le cadre
            positionX += event.getX();
            positionY += event.getY();
            setBounds(positionX,positionY,height,width);
       //} 
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