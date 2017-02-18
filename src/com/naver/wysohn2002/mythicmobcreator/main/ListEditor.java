package com.naver.wysohn2002.mythicmobcreator.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ListEditor extends JPanel {

    /**
     * Create the panel.
     */
    public ListEditor() {
        setLayout(new BorderLayout(0, 0));

        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);

        JButton btnAdd = new JButton("Add");
        panelNorth.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        panelNorth.add(btnEdit);

        JButton btnRemove = new JButton("Remove");
        panelNorth.add(btnRemove);

        JPanel panelWest = new JPanel();
        panelWest.setMaximumSize(new Dimension(50, 32767));
        add(panelWest, BorderLayout.WEST);
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));

        JButton btnUp = new JButton("Up");
        btnUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelWest.add(btnUp);

        JButton btnDown = new JButton("Down");
        btnDown.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelWest.add(btnDown);

        JPanel panelCenter = new JPanel();
        add(panelCenter, BorderLayout.CENTER);
    }

}
