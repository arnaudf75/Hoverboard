package com.hoverboard.plugins;

import java.io.File;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

/**
 * Cette classe charge les plugins depuis le dossier "plugins" avec Java Simple Plugin Framework.
 * @author Arnaud
 */
public class GestionPlugins {
    
    /**
     * Sélectionne un plugin implémentant l'interface "PluginInterface".
     */
    public static void getButton() {
        PluginInterface plugin = new PluginManagerUtil(PluginManagerFactory.createPluginManager()).getPlugin(PluginInterface.class);
    }
    
    /**
     * Charge les plugins. 
     */
    public static void loadPlugins() {
        
        PluginManager pluginManager = PluginManagerFactory.createPluginManager();
        pluginManager.addPluginsFrom(new File("plugins/").toURI());
        
        PluginInterface plugins = new PluginManagerUtil(pluginManager).getPlugin(PluginInterface.class);
        
    }
}
