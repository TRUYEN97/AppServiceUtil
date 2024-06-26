/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qt.Jmodel.Component;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Administrator
 * @param <T>
 */
public class MyListTabel<T> extends AbsMenuAndAction<JList<T>> {

    private DefaultListModel<T> defaultListModel;

    public MyListTabel(JList<T> jList) {
        this(jList, true, false);
    }

    public MyListTabel(JList<T> jList, boolean sortAble) {
        this(jList, sortAble, false);
    }

    public MyListTabel(JList<T> jList, boolean sortAble, boolean singleSelection) {
        super(jList);
        this.defaultListModel = new DefaultListModel<>();
        this.component.setModel(defaultListModel);
        if (singleSelection) {
            this.component.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } else {
            this.component.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        if (sortAble) {
            this.component.addMouseMotionListener(new MouseMotionListener() {
                private int oldIndex = -1;
                private Point oldPonit;

                @Override
                public void mouseDragged(MouseEvent e) {
                    int index = jList.getSelectedIndex();
                    Point point = e.getPoint();
                    if (index == -1 || oldPonit == null) {
                        oldPonit = point;
                        return;
                    }
                    int diff = point.y - oldPonit.y;
                    int diffIndex = index - oldIndex;
                    oldPonit = point;
                    int size = defaultListModel.getSize();
                    if (diff == 0 || oldIndex == index) {
                        return;
                    } else if (diff < 0 && index < size - 1 && diffIndex == -1) {
                        T oldIndexT = defaultListModel.getElementAt(index);
                        T newIndexT = defaultListModel.getElementAt(index + 1);
                        defaultListModel.setElementAt(newIndexT, index);
                        defaultListModel.setElementAt(oldIndexT, index + 1);
                    } else if (diff > 0 && index > 0 && diffIndex == 1) {
                        T oldIndexT = defaultListModel.getElementAt(index);
                        T newIndexT = defaultListModel.getElementAt(index - 1);
                        defaultListModel.setElementAt(newIndexT, index);
                        defaultListModel.setElementAt(oldIndexT, index - 1);
                    }
                    oldIndex = index;
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    if (e.getButton() == MouseEvent.NOBUTTON) {
                        oldPonit = null;
                    }
                }
            });
        }
    }

    public void addItem(T element) {
        if (element == null) {
            return;
        }
        this.defaultListModel.addElement(element);
    }

    public void reset() {
        this.defaultListModel.removeAllElements();
    }

    public int size() {
        return this.defaultListModel.getSize();
    }

    public void setItem(int index, T element) {
        this.defaultListModel.set(0, element);
    }

    public void addItem(int index, T element) {
        this.defaultListModel.add(0, element);
    }

    public List<T> getAllItem() {
        List<T> rs = new ArrayList<>();
        Enumeration<T> enumeration = this.defaultListModel.elements();
        while (enumeration.hasMoreElements()) {
            T nextElement = enumeration.nextElement();
            if (nextElement == null) {
                continue;
            }
            rs.add(nextElement);
        }
        return rs;
    }

    public boolean contains(T element) {
        if (element == null) {
            return false;
        }
        Enumeration<T> enumeration = this.defaultListModel.elements();
        while (enumeration.hasMoreElements()) {
            T nextElement = enumeration.nextElement();
            if (nextElement == null) {
                continue;
            }
            if (nextElement.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void addAllItem(List<T> rows) {
        if (rows == null || rows.isEmpty()) {
            return;
        }
        this.defaultListModel.addAll(rows);
    }

    public T getSelectioned() {
        return this.component.getSelectedValue();
    }

    public List<T> getSelectedValuesList() {
        return this.component.getSelectedValuesList();
    }

    public List<T> removeItemSelecteds() {
        List<T> allItem = new ArrayList<>(getSelectedValuesList());
        for (T t : allItem) {
            this.defaultListModel.removeElement(t);
        }
        return allItem;
    }

    public List<T> removeAllItem() {
        List<T> allItem = getAllItem();
        reset();
        return allItem;
    }

    @Override
    public int getSelectedRowCount() {
        return getSelectedValuesList().size();
    }

    public void remove(int index) {
        this.component.remove(index);
    }

    public void removeFirst() {
        if (size() <= 0) {
            return;
        }
        remove(0);
    }

    public void removeLast() {
        int size = size();
        if (size <= 0) {
            return;
        }
        remove(size - 1);
    }

    public void addModel(DefaultListModel<T> model) {
        this.defaultListModel = model;
        this.component.setModel(model);
    }

    public void setSelected(T item) {
        this.component.setSelectedValue(item, true);
    }

    @Override
    public void clearSelection() {
        this.component.clearSelection();
        this.component.validate();
    }

}
