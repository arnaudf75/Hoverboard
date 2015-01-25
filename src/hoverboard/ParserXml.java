package hoverboard;

import java.io.File;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class ParserXml {
    
    SAXBuilder sax;
    Document document = new Document();
    Element Racine;
    
    public ParserXml() {
        SAXBuilder sax = new SAXBuilder(); // On crée une instance de SAXBuilder pour le parsing
        Document document = new Document(); // On crée un objet Document pour le fichier à parser
        Element racine; // On crée un objet Element pour la racine du fichier .xml
    }

    public void getDataJDBC(SAXBuilder sax, Document document, Element racine) {
        try {
            document = sax.build(new File("../../ressources/data_jdbc.xml"));
        }
        catch(IOException | NullPointerException | JDOMException error){
            System.out.println(error);
        }
        
        racine = document.getRootElement(); // L'objet racine de classe Element récupère la racine du fichier, ici "<root>"
        System.out.println(racine.getChild("dbUrl").getText());
        System.out.println(racine.getChild("driver").getText());
        System.out.println(racine.getChild("login").getText());
        System.out.println(racine.getChild("password").getText());
    }
}