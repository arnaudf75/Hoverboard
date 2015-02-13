package hoverboard;

import java.io.File;
import java.io.FileWriter;
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
    
    public Hashtable getDataPost(SAXBuilder sax, Document document, Element racine) {
        try {
            document = sax.build(new File("src/ressources/mypostit.xml"));
        }
        catch(IOException | JDOMException error) {
            System.out.println(error);
        }
        racine = document.getRootElement();
        dicto.put("text", racine.getChild("text").getText());
        dicto.put("color", racine.getChild("color").getText());
        return (dicto);
    }
    
    public void creerCookie(String loginField, String passwordField) {
        try {
            Element cookie = new Element("cookie");
            Document document = new Document();
            document.setRootElement(cookie);
            Element login = new Element("login").addContent(loginField);
            Element password = new Element("password").addContent(passwordField);
            document.getRootElement().addContent(login);
            document.getRootElement().addContent(password);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter("src/ressources/cookie_login.xml"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    
    public boolean isLoginValid(SAXBuilder sax, Document document, Element racine,BDD connexion) {
        try {
            document = sax.build(new File("src/ressources/cookie_login.xml"));
        }
        catch(IOException | JDOMException error) {
            System.out.println(error);
        }
        racine = document.getRootElement();
        String loginUser = racine.getChild("login").getText();
        String passwordUser = racine.getChild("password").getText();
        if (connexion.connect_user(loginUser, passwordUser)) {
            return (true);
        }
        else {
            return (false);
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