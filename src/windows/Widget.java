package windows;

import hoverboard.BDD;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Cavoleau
 */
public abstract class Widget extends JInternalFrame implements ActionListener,MouseMotionListener {
    protected int idDashboard;
    protected int idWidget;
    protected int height;
    protected int width;
    protected int positionX;
    protected int positionY;
    protected int oldX;
    protected int oldY;
    
    protected JButton del = new JButton("x");
    protected JPanel settings=new JPanel();
    protected JPanel content=new JPanel();
    
    protected BDD connexion = new BDD();
    
    public Widget(){
        super();
        settings.setLayout(new BorderLayout());
        this.add(settings, BorderLayout.NORTH);
        this.add(content, BorderLayout.CENTER);
        
        del.addActionListener(this);
        settings.add(del,BorderLayout.EAST);
        this.setResizable(true);
        this.setVisible(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        
        settings.addMouseMotionListener(this);
    }
    
    @Override
    public void mouseDragged(MouseEvent event) {
       // if (contains(e.getX(),e.getY())) { A utiliser pour que le post it reste dans le cadre
            positionX += event.getX();
            positionY += event.getY();
            setBounds(positionX,positionY,height,width);
       //} 
    }
    @Override
    public void mouseMoved(MouseEvent event) {
	 
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == del){
            this.dispose();
            this.connexion.deleteWidget(this.idWidget);
            System.out.println("Je supprime "+this.idWidget);
        }
    }
}