/*******************************************************************************
 *     Copyright (C) 2017 wysohn
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EmptyListEditor extends JPanel {
    protected final JList<String> jList;
    protected final List<String> list;

    protected final JButton btnAdd;
    protected final JButton btnEdit;
    protected final JButton btnRemove;
    protected final JButton btnUp;
    protected final JButton btnDown;

    protected final JPanel mainPanel;
    protected final JScrollPane scrollPane;

    /**
     * Create the panel.
     */
    public EmptyListEditor(List<String> targetList) {
        this.list = targetList;

        setLayout(new BorderLayout(0, 0));

        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);

        btnAdd = new JButton("Add");
        panelNorth.add(btnAdd);

        btnEdit = new JButton("Edit");
        panelNorth.add(btnEdit);

        btnRemove = new JButton("Remove");
        panelNorth.add(btnRemove);

        JPanel panelWest = new JPanel();
        panelWest.setMaximumSize(new Dimension(50, 32767));
        add(panelWest, BorderLayout.WEST);
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));

        btnUp = new JButton("Up");
        btnUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelWest.add(btnUp);

        panelWest.add(Box.createRigidArea(new Dimension(5,0)));

        btnDown = new JButton("Down");
        btnDown.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelWest.add(btnDown);

        mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);

        scrollPane = new JScrollPane();
        mainPanel.add(scrollPane);

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(220, 100));

        jList = new JList<String>(list.toArray(new String[list.size()]));
        scrollPane.setViewportView(jList);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList lsm = (JList)e.getSource();

                List<Integer> indices = new ArrayList<Integer>();
                if(!lsm.isSelectionEmpty()){
                    int min = lsm.getMinSelectionIndex();
                    int max = lsm.getMaxSelectionIndex();
                    for(int i = min; i <= max; i++){
                        if(lsm.isSelectedIndex(i))
                            indices.add(i);
                    }
                }
                onListItemSelected(indices);
            }
        });

        ////////////////////////////////////////////////////////////
        this.btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        this.btnEdit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                onEdit();
            }
        });

        this.btnRemove.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove();
            }
        });

        this.btnUp.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                onUp();
            }
        });

        this.btnDown.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                onDown();
            }
        });
    }

    protected void onListItemSelected(List<Integer> selectedIndices){
        throw new RuntimeException("Not implemented");
    }

    protected void onAdd(){
        throw new RuntimeException("Not implemented");
    }

    protected void onEdit(){
        throw new RuntimeException("Not implemented");
    }

    protected void onRemove(){
        int[] indices = this.jList.getSelectedIndices();
        int result = JOptionPane.showConfirmDialog(this, "Are you sure to remove ["+indices.length+"] items?");
        if(result == JOptionPane.OK_OPTION){
            for(int i = indices.length - 1; i >= 0; i--){
                this.list.remove(indices[i]);
            }

            this.updateJList();
        }
    }

    protected void onUp(){
        if(list.size() < 2)
            return;

        if(this.getSelectedIndex() <= 0)
            return;

        swap(this.getSelectedIndex(), this.getSelectedIndex() - 1);

        updateJList();
    }

    protected void onDown(){
        if(list.size() < 2)
            return;

        if(this.getSelectedIndex() > list.size() - 1)
            return;

        swap(this.getSelectedIndex(), this.getSelectedIndex() + 1);

        updateJList();
    }

    protected void swap(int index1, int index2){
        String temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    protected void updateJList(){
        jList.setListData(list.toArray(new String[list.size()]));
    }

    /**
     *
     * @return index of selected index; -1 if not selected.
     */
    protected int getSelectedIndex(){
        return jList.getSelectedIndex();
    }
}