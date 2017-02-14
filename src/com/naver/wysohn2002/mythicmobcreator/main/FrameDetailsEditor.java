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
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JList;
import java.awt.GridLayout;

public class FrameDetailsEditor extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrameDetailsEditor(DefaultListModel<PanelItem> model) {
		setPreferredSize(new Dimension(420, 420));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(350, 300));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(350, 350));
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel list = new JPanel();
		scrollPane.setViewportView(list);
		list.setLayout(new GridLayout(0, 1, 5, 5));
		for(int i = 0; i < model.size(); i++){
			list.add(model.getElementAt(i));
		}
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		pack();
	}

}
