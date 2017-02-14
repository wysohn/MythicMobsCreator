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
