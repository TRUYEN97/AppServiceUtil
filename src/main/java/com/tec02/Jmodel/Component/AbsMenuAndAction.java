/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tec02.Jmodel.Component;

import com.tec02.Jmodel.IAction;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;

/**
 *
 * @author Administrator
 * @param <T>
 */
public abstract class AbsMenuAndAction<T extends JComponent> {

    protected PopupMenu menu;
    protected PopupMenu selectedMenu;
    protected IAction<MouseEvent> doubleClickAction;
    private IAction<KeyEvent> actionKeyPressed;
    private final HashMap<Integer, IAction<KeyEvent>> keyEvents;
    protected final T component;

    public AbsMenuAndAction(T component) {
        if (component == null) {
            throw new NullPointerException("Table cannot be null");
        }
        component.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (actionKeyPressed != null) {
                    actionKeyPressed.action(evt);
                } else {
                    for (Map.Entry<Integer, IAction<KeyEvent>> entry : keyEvents.entrySet()) {
                        if (evt.getKeyCode() == entry.getKey()) {
                            entry.getValue().action(evt);
                        }
                    }
                }
            }
        });
        this.menu = new PopupMenu();
        this.selectedMenu = new PopupMenu();
        this.component = component;
        this.keyEvents = new HashMap<>();
        this.component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClickdAction(e);
            }
        });
        Component parerent = this.component.getParent();
        if (parerent != null) {
            parerent.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (getSelectedRowCount() > 0 && !e.isControlDown()) {
                        clearSelection();
                    }
                    mouseClickdAction(e);
                }
            });
        }
    }

    public void putAllKeyEvents(HashMap<Integer, IAction<KeyEvent>> keyEvents) {
        this.keyEvents.putAll(keyEvents);
    }
    
    public void addKeyEventAction(int keyCode, IAction<KeyEvent> event) {
        this.keyEvents.put(keyCode, event);
    }

    public void clearKeyEventActions() {
        this.keyEvents.clear();
    }

    public HashMap<Integer, IAction<KeyEvent>> getKeyEventActions() {
        return keyEvents;
    }

    public void setActionKeyPressed(IAction<KeyEvent> actionKeyPressed) {
        this.actionKeyPressed = actionKeyPressed;
    }

    public IAction<KeyEvent> getActionKeyPressed() {
        return actionKeyPressed;
    }

    public IAction<MouseEvent> getDoubleClickAction() {
        return doubleClickAction;
    }

    public T getComponent() {
        return component;
    }

    private void mouseClickdAction(MouseEvent e) {
        if (this.component == null) {
            return;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            if (!e.isControlDown()) {
                clearSelection();
            }
            return;
        }
        if (e.getClickCount() > 1 && e.getButton() == MouseEvent.BUTTON1
                && getSelectedRowCount() == 1 && doubleClickAction != null) {
            doubleClickAction.action(e);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (getSelectedRowCount() > 0 && selectedMenu != null && !selectedMenu.isEmpty()) {
                selectedMenu.show(e);
            } else if (menu != null && !menu.isEmpty()) {
                menu.show(e);
            }
        }
    }

    public PopupMenu getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(PopupMenu selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public void setMenu(PopupMenu menu) {
        this.menu = menu;
    }

    public PopupMenu getMenu() {
        return menu;
    }

    public void setDoubleClickAction(IAction<MouseEvent> doubleClickAction) {
        this.doubleClickAction = doubleClickAction;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        if (this.component == null) {
            return;
        }
        this.component.addMouseListener(mouseAdapter);
    }

    public abstract void clearSelection();

    public abstract int getSelectedRowCount();

}
