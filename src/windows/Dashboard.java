package windows;

import hoverboard.ParserXml;
import java.io.File;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 * Dashboard est la classe qui permet d'afficher les différents widgets d'un panneau.
 * @author Arnaud
 */

public class Dashboard extends JPanel {
    int idDashboard;
    int nombreWidgets;
    
    
    public Dashboard()  {
        // Code pour l'affichage de widgets depuis un dossier local avec des fichiers .xml
        
        // Je crée un nouveau fichier .xml
        ParserXml myParser = new ParserXml();
        int idDashboard = 3; // Variable rentrée en dur, à enlever
        //myParser.creePostIt(idDashboard, this.height, this.width, this.positionX, this.positionY);
        HashMap dicto = new HashMap();
        File directory = new File("src/ressources/dashboard_"+idDashboard);        
        // Avec l'id du dashboard, je vais voir en local pour récupérer les fichiers post it
        String [] listeFichiers = directory.list();
        // Pour chaque post it récupéré (au format Hashmap), je crée un nouveau post it (new PostIt) et à la fin je fais un revalidate()
        for (int i=0; i<directory.listFiles().length; i++) {
            dicto = myParser.getDataPost(myParser.getSax(),"src/ressources/dashboard_"+idDashboard+"/"+listeFichiers[i]);
            this.add(new PostIt(99999,dicto.get("content").toString()));
        }
    }
}
