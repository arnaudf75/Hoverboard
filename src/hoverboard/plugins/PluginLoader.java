/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoverboard.plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import static java.nio.file.Files.list;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Cette classe permet au programme de charger le jar d'un plugin afin de
 * l'implémenter.
 *
 * @author Amram
 */
public class PluginLoader {

    // la classe prend pour attribut un PluginFile pour garder la reference du plugin
    // de plus *captain obvious speaking*, il faut un fichier plugin pour l' activer/loader ou le desactiver/unloader
    private PluginFile file;
    public static ArrayList<PluginDeBase> list = new ArrayList<>();
    public static ArrayList<String> names = new ArrayList<String>();

    // constructeur ne prend donc qu'un seul param : le PluginFile
    public PluginLoader(PluginFile file) {
        this.file = file;
    }

    /**
     * set list of instance of pluginDeBase
     */
    public void loadPlugin() {

        try {
            // répertoire de plugins
            File PathFileToPlugin = new File(file.getPathOnMachine());
            // liste des plugins
            File[] listFile = PathFileToPlugin.listFiles();
            //classe loader
            ArrayList<ClassLoader> listClassLoader = new ArrayList<ClassLoader>();
            // Liste d'enumeration 
            Enumeration<JarEntry> enums;
            String namePlugin = null;
            Class nameClass = null;
            ArrayList<Class> listClass = new ArrayList<Class>();
            //parcours de la liste des pulgins
            int place = 0;
            for (File f : listFile) {
                try {
                    URL[] listeUrl = {f.toURL()};
                    listClassLoader.add(new URLClassLoader(listeUrl));
                    JarFile ficjar = new JarFile(f.getAbsolutePath());
                    enums = ficjar.entries();
                    // parcours de l'énumération 
                    while (enums.hasMoreElements()) {
                        namePlugin = enums.nextElement().toString();
                        if (namePlugin.length() > EXTENSION.length() && namePlugin.substring(namePlugin.length() - EXTENSION.length()).compareTo(EXTENSION) == 0) {
                            namePlugin = namePlugin.substring(0, namePlugin.length() - EXTENSION.length());
                            namePlugin = namePlugin.replaceAll("/", ".");
                            nameClass = Class.forName(namePlugin, true, listClassLoader.get(place));
                            for (int i = 0; i < nameClass.getInterfaces().length; i++) {
                                if (nameClass.getInterfaces()[i].toString().equals("interface hoverboard.plugins.PluginDeBase")) {
                                    listClass.add(nameClass);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println();
                }
                place++;
            }
            place = 0;
            for (Class c : listClass) {
                names.add(c.getName().substring(c.getName().indexOf(".") + 1));
                PluginDeBase pluginInstance = (PluginDeBase) Class.forName(c.getName(), true, listClassLoader.get(place)).newInstance();
                list.add(pluginInstance);
                place++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur lecture plugin");
        }
    }
    private static final String EXTENSION = ".class";

}
