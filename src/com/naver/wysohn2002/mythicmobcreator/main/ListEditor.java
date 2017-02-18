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
