package hoverboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import java.util.Hashtable;


public class ParserXml {
    
    SAXBuilder sax;
    Document document = new Document();
    Element racine; // On crée un objet Element pour la racine du fichier .xml
    Hashtable dicto = new Hashtable();
    
    public ParserXml() {
        this.sax = new SAXBuilder(); // On crée une instance de SAXBuilder pour le parsing
        this.document = new Document(); // On crée un objet Document pour le fichier à parser
    }

    public Hashtable getDataJDBC(SAXBuilder sax, Document document, Element racine) {
        try {
            document = sax.build(new File("src/ressources/data_jdbc.xml"));
        }
        catch(IOException | JDOMException error){
            System.out.println(error);
        }
        
        racine = document.getRootElement(); // L'objet racine de classe Element récupère la racine du fichier, ici "<root>"
        dicto.put("dbUrl", racine.getChild("dbUrl").getText());
        dicto.put("driver", racine.getChild("driver").getText());
        dicto.put("login", racine.getChild("login").getText());
        dicto.put("password", racine.getChild("password").getText());
        return (dicto);
    }
    
    public void creerDocument() {
        try {
            String fichier = "filefichier.xml";
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
            //avec en argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document, new FileOutputStream(fichier));
        }
        catch (java.io.IOException e) {
            
        }
    }
    
    public SAXBuilder getSax() {
        return sax;
    }
    
    public Document getDocument() {
        return document;
    }
    
    public Element getRacine() {
        return racine;
    }
}