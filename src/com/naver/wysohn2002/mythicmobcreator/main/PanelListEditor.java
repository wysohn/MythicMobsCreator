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

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import com.naver.wysohn2002.mythicmobcreator.util.Inserter;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.FlowLayout;

public class PanelListEditor extends JPanel {
	private final List<String> list;
	private List<Integer> selectedIndexes;
	
	private PanelItem item;

	/**
	 * Create the panel.
	 * @param runnable 
	 */
	public PanelListEditor(final List<String> list, final Runnable onUpdate, final PanelItem item) {
		final Inserter inserter;
		if(list instanceof Inserter){
			inserter = (Inserter) list;
		}else{
			inserter = new Inserter(){
				@Override
				public int numParams() {
					return 1;
				}
				
				@Override
				public void add(List<String> params) {
					StringBuilder builder = new StringBuilder();
					for(String param : params)
						builder.append(param);
					list.add(builder.toString());
				}

				@Override
				public Class getParamType(int index) {
					return String.class;
				}

				@Override
				public void set(int index, List<String> params) {
					list.set(index, params.get(0));
				}
			};
		}
		this.list = list;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBorder(UIManager.getBorder("TextPane.border"));
		add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new GridLayout(0, 1, 5, 5));
		
		JPanel panelBtn = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBtn.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panelNorth.add(panelBtn);
		
		final JList jlist = new JList();
		jlist.setMaximumSize(new Dimension(300, 120));
		
		JButton BtnAdd = new JButton("Add");
		BtnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = extractText(item, 0);
				if(text == null || text.length() < 1){
					return;
				}
				
				List<String> params = createParams(item);
				
				inserter.add(params);
				
				updateListScreen(jlist, list);
				onUpdate.run();
			}
		});
		panelBtn.add(BtnAdd);
		
		JButton btnEdit = new JButton("Set");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndexes == null){
					JOptionPane.showMessageDialog(null, "You have not selected anything!");
					return;
				}
				
				if(selectedIndexes.size() > 1){
					JOptionPane.showMessageDialog(null, "Please select one item only!");
					return;
				}
				
				String text = extractText(item, 0);
				if(text == null || text.length() < 1){
					return;
				}
				
				List<String> params = createParams(item);
				
				inserter.set(selectedIndexes.get(0), params);
				
				updateListScreen(jlist, list);
				onUpdate.run();
			}
		});
		panelBtn.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndexes == null){
					JOptionPane.showMessageDialog(null, "You have not selected anything!");
					return;
				}
				
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure to delete " + selectedIndexes.size() + " items?") == JOptionPane.OK_OPTION) {
					for(int index : selectedIndexes){
						list.remove(index);
					}
				}
				
				updateListScreen(jlist, list);
				onUpdate.run();
			}
		});
		panelBtn.add(btnDelete);
		
		
		panelNorth.add(item);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(300, 120));
		scrollPane.setMaximumSize(new Dimension(300, 120));
		add(scrollPane, BorderLayout.CENTER);
		
		updateListScreen(jlist, list);
		
		jlist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList jlist = (JList)e.getSource();
				
				if(!jlist.isSelectionEmpty()){
					if(jlist.getMinSelectionIndex() == -1 || jlist.getMaxSelectionIndex() == -1){
						selectedIndexes = null;
						return;
					}
					
					selectedIndexes = new ArrayList<Integer>();
					
					for(int i = jlist.getMaxSelectionIndex(); i >= jlist.getMinSelectionIndex() ; i--){
						if(jlist.isSelectedIndex(i)){
							selectedIndexes.add(i);
						}
					}
				} else {
					selectedIndexes = null;
				}
			}
		});
		scrollPane.setViewportView(jlist);
		
		JPanel panelWest = new JPanel();
		add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(new GridLayout(0, 1, 5, 5));
		
		JButton btnUp = new JButton("up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndexes.size() == 1){
					int index = selectedIndexes.get(0);
					if(index - 1 < 0)
						return;
					
					swap(list, index, index - 1);
					
					updateListScreen(jlist, list);
					onUpdate.run();
				}
			}
		});
		panelWest.add(btnUp);
		
		JButton btnDown = new JButton("down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndexes.size() == 1){
					int index = selectedIndexes.get(0);
					if(index + 1 > list.size() - 1)
						return;
					
					swap(list, index, index + 1);
					
					updateListScreen(jlist, list);
					onUpdate.run();
				}
			}
		});
		panelWest.add(btnDown);
		
/*		for(int i = 0; i < numParams; i++){
			JTextField textField = new JTextField();
			textField.setBorder(new EmptyBorder(5, 5, 5, 5));
			panelTexts.add(textField);
			textField.setColumns(10);
			this.params[i] = textField;
		}*/
	}
	
	private void swap(List list, int val1, int val2){
		Object temp = list.get(val1);
		list.set(val1, list.get(val2));
		list.set(val2, temp);
	}
	
	private void updateListScreen(JList jlist, List<String> list2) {
		jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		DefaultListModel<String> model = new DefaultListModel();
		for(String val : list)
			model.addElement(val);
		jlist.setModel(model);
	}
	
	private String extractText(PanelItem item, int i) {
		Component comp = item.getComponents()[i];
		String str = null;
		if(comp instanceof JComboBox){
			JComboBox box = (JComboBox) comp;
			str = box.getSelectedItem().toString();
		}else{
			JTextField text = (JTextField) comp;
			str = text.getText();
		}
		
		if(str == null || str.length() < 1){
			return null;
		}else{
			return str;
		}
	}

	private List<String> createParams(PanelItem item) {
		List<String> strs = new ArrayList<String>();
		for(int i = 0; i < item.getComponents().length; i++){
			String extracted = extractText(item, i);
			strs.add(extracted == null ? "" : extracted);
		}
		return strs;
	}
	
	public static interface StringCombiner{
		String combine(String[] params);
	}

}
