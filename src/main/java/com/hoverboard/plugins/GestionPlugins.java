package com.hoverboard.plugins;

import java.io.File;
import java.util.Collection;
import javax.swing.JOptionPane;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

/**
 *
 * @author Arnaud
 */
public class GestionPlugins {
    
    public static void getButton() {
        PluginInterface plugin = new PluginManagerUtil(PluginManagerFactory.createPluginManager()).getPlugin(PluginInterface.class);
        System.out.println(plugin.afficheTest());
    }
    
    public static void loadPlugins() {
        /*for (File file : new File("plugins/").listFiles()) {
           System.out.println(file); 
        }*/
        
        PluginManager pluginManager = PluginManagerFactory.createPluginManager();
        pluginManager.addPluginsFrom(new File("plugins/").toURI());
        
        PluginInterface game = new PluginManagerUtil(pluginManager).getPlugin(PluginInterface.class);
        
        JOptionPane.showMessageDialog(null,game.afficheTest());
    }
}
