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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;

public class PanelItem extends JPanel {
	private JTextField textField;

	private CustomValue customValue;

	/**
	 * Create the panel.
	 */
    PanelItem(String key, CustomValue customValue) {
        setBackground(Color.LIGHT_GRAY);

        setBorder(new LineBorder(new Color(0, 0, 0), 2));
        setLayout(new BorderLayout(5, 5));

        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new GridLayout(0, 1, 5, 5));

        if (key != null) {
            textField = new JTextField();
            textField.setDisabledTextColor(Color.WHITE);
            textField.setPreferredSize(new Dimension(30, 20));
            textField.setEditable(false);
            panelNorth.add(textField);
            textField.setColumns(10);
            textField.setText(key);
            textField.setFont(new Font("SansSeif", Font.BOLD, 14));
            textField.setForeground(Color.MAGENTA);
        }

        JPanel panelCenter = new JPanel();
        panelCenter.setMaximumSize(new Dimension(600, 32767));
        add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

        panelCenter.add(customValue.getEditor());
    }

    PanelItem(String key, Component... customValue) {
        setBackground(Color.LIGHT_GRAY);

        setBorder(new LineBorder(new Color(0, 0, 0), 2));
        setLayout(new BorderLayout(5, 5));

        JPanel panelNorth = new JPanel();
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new GridLayout(0, 1, 5, 5));

        if (key != null) {
            textField = new JTextField();
            textField.setDisabledTextColor(Color.WHITE);
            textField.setPreferredSize(new Dimension(30, 20));
            textField.setEditable(false);
            panelNorth.add(textField);
            textField.setColumns(10);
            textField.setText(key);
            textField.setFont(new Font("SansSeif", Font.BOLD, 14));
            textField.setForeground(Color.MAGENTA);
        }

        JPanel panelCenter = new JPanel();
        panelCenter.setMaximumSize(new Dimension(600, 32767));
        add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

        for(Component comp : customValue)
            panelCenter.add(comp);
    }
}
