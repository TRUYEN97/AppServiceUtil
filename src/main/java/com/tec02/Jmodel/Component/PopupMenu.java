/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.Jmodel.Component;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Administrator
 */
public class PopupMenu {
    
    private final JPopupMenu popupMenu;

    public PopupMenu() {
        this.popupMenu = new JPopupMenu();
    }
    
    public void addItemMenu(String itemName, ActionListener actionListener){
        JMenuItem itemMenu = new JMenuItem(itemName);
        itemMenu.addActionListener(actionListener);
        this.popupMenu.add(itemMenu);
    }
    
    public void addItemMenu(String itemName, ActionListener actionListener, Icon icon){
        JMenuItem itemMenu = new JMenuItem(itemName);
        itemMenu.addActionListener(actionListener);
        itemMenu.setIcon(icon);
        this.popupMenu.add(itemMenu);
    }
    
    public void addItemMenu(String itemName, ActionListener actionListener, String iconPath){
        addItemMenu(itemName, actionListener, new ImageIcon(itemName));
    }
    
    public void show(Component component ,int x, int y){
        this.popupMenu.show(component, x, y);
    }
    
    public void show(MouseEvent e){
        show(e.getComponent(), e.getX(), e.getY());
    }

    public boolean isEmpty() {
        return this.popupMenu.getComponentCount() == 0;
    }
}
