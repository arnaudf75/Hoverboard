package hoverboard;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

/**
 * ParserXml est la classe qui récupère les données des fichiers .xml de l'application grâce à l'API JDOM.
 * @author Arnaud
 */

public class ParserXml {
    
    SAXBuilder sax;
    Document document = new Document();
    
    public ParserXml() {
        this.sax = new SAXBuilder(); // On crée une instance de SAXBuilder pour le parsing
        this.document = new Document(); // On crée un objet Document pour le fichier à parser
    }
    
    /**
    * @param sax
    * L'objet SaxBuilder.
    * @return
    * Dictionnaire contenant les données permettant d'initialiser la connexion à la base de données (url, nom du driver, login et password).
    */

    public HashMap getDataJDBC(SAXBuilder sax) {
        try {
            this.document = sax.build(new File("src/ressources/data_jdbc.xml"));
        }
        catch(IOException | JDOMException error){
            System.out.println(error);
        }
        
        Element racine = this.document.getRootElement(); // L'objet racine de classe Element récupère la racine du fichier, ici "<root>"
        HashMap dicto = new HashMap();
        dicto.put("dbUrl", racine.getChild("dbUrl").getText());
        dicto.put("driver", racine.getChild("driver").getText());
        dicto.put("login", racine.getChild("login").getText());
        dicto.put("password", racine.getChild("password").getText());
        return (dicto);
    }
    
    /**
     * 
     * @param sax
     * L'objet SaxBuilder
     * @return 
     * Dictionnaire contenant les données d'un post-it.
     */
    
    public HashMap getDataPost(SAXBuilder sax) {
        try {
            this.document = sax.build(new File("src/ressources/mypostit.xml"));
        }
        catch(IOException | JDOMException error) {
            System.out.println(error);
        }
        Element racine = document.getRootElement();
        HashMap dicto = new HashMap();
        dicto.put("text", racine.getChild("text").getText());
        dicto.put("color", racine.getChild("color").getText());
        return (dicto);
    }
    
    /**
     * Créer un fichier cookie pour connecter l'utilisateur automatiquement la prochain fois qu'il utilisera l'application.
     * Les informations saisies dans la fenêtre de login sont récupérées pour les insérées dans le fichier cookie_login.xml.
     * @param loginField
     * Le login saisi par l'utilisateur lors de la connexion
     * @param passwordField
     * Le mot de passe saisi par l'utilisateur lors de la connexion
     */
    
    public void creerCookie(String loginField, String passwordField) {
        try {
            Element cookie = new Element("cookie");
            this.document = new Document();
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
    
    /**
     * Est appellée si il existe un cookie de login. Vérifie que celui-ci est valide.
     * @param sax
     * L'objet SaxBuilder.
     * @param connexion
     * L'objet BDD, permettant d'utiliser les méthodes de la classe.
     * @return 
     * True si les informations de login et de mot de passe dans le cookie sont bonnes, False sinon.
     */    
    
    public boolean isLoginValid(SAXBuilder sax, BDD connexion) {
        try {
            this.document = sax.build(new File("src/ressources/cookie_login.xml"));
        }
        catch(IOException | JDOMException error) {
            System.out.println(error);
        }
        Element racine = this.document.getRootElement();
        String loginUser = racine.getChild("login").getText();
        String passwordUser = racine.getChild("password").getText();
        if (connexion.connect_user(loginUser, passwordUser)) {
            return (true);
        }
        else {
            return (false);
        }
    }
    
    /**
     * Récupère l'objet SaxBuilder.
     * @return 
     * L'objet SaxBuilder.
     */
        
    public SAXBuilder getSax() {
        return sax;
    }
    
    /**
     * Récupère l'objet Document.
     * @return 
     * L'objet Document.
     */
    
    public Document getDocument() {
        return document;
    }
}