package com.hoverboard.plugins;

import javax.swing.JButton;
import javax.swing.JInternalFrame;

import net.xeoh.plugins.base.Plugin;

/**
 *
 * @author Arnaud
 */
public interface PluginInterface extends Plugin {
    
    public JButton getPluginButton();
    public JInternalFrame getPluginWidget();
    public String afficheTest();
}
