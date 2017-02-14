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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.constants.AIGoalSelector;
import com.naver.wysohn2002.mythicmobcreator.constants.AITargetSelector;
import com.naver.wysohn2002.mythicmobcreator.constants.DamageModifiers;
import com.naver.wysohn2002.mythicmobcreator.constants.Drops;
import com.naver.wysohn2002.mythicmobcreator.util.Inserter;
import com.naver.wysohn2002.mythicmobcreator.util.NumberUtil;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.GridLayout;

public class PanelResourceEditor extends JPanel {
	private ConfigurationSerializable target;
	/**
	 * Create the panel.
	 */
	public PanelResourceEditor(ConfigurationSerializable target) {
		this.target = target;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 5, 5));

		try {
			DefaultListModel<PanelItem> model = new DefaultListModel();
			addItems(model);
			for(int i = 0; i < model.size(); i++){
				panel.add(model.get(i));
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(this, e.getMessage());
			Main.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void addItems(DefaultListModel<PanelItem> model)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, InstantiationException {
		addItems(model, target);
	}

	private static final int TEXTFIELDWIDTH = 300;
	private static final int TEXTFIELDHEIGHT = 30;
	private void addItems(DefaultListModel<PanelItem> model, final ConfigurationSerializable target)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		for (Field field : target.getClass().getFields()) {
			if(ConfigurationSerializable.class.isAssignableFrom(field.getType())){
				final DefaultListModel<PanelItem> subList = new DefaultListModel();
				ConfigurationSerializable subTarget = (ConfigurationSerializable) field.get(target);
				if(subTarget == null)
					subTarget = (ConfigurationSerializable) field.getType().newInstance();
				field.set(target, subTarget);
				
				addItems(subList, subTarget);
				
				JButton button = new JButton();
				button.setHorizontalAlignment(SwingConstants.RIGHT);
				button.setText("Edit");
				button.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						FrameDetailsEditor details = new FrameDetailsEditor(subList);
						details.setVisible(true);
					}
				});
				model.addElement(new PanelItem(field.getName(), button));
			}else if(Boolean.class.isAssignableFrom(field.getType())){
				JCheckBox checkBox = new JCheckBox();
				checkBox.setHorizontalAlignment(SwingConstants.RIGHT);
				Boolean previous = (Boolean) field.get(target);
				checkBox.setSelected(Boolean.valueOf(previous == null ? false : previous));
				final Field targetField = field;
				checkBox.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						AbstractButton abstractButton = (AbstractButton) e.getSource();
				        boolean selected = abstractButton.getModel().isSelected();
				        
				        try {
							targetField.set(target, selected);
						} catch (Exception ex){
							Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
						}
					}
				});
				
				model.addElement(new PanelItem(field.getName(), checkBox));
			}else if(Number.class.isAssignableFrom(field.getType())){
				final JTextField textField = new JTextField();
				textField.setPreferredSize(new Dimension(TEXTFIELDWIDTH, TEXTFIELDHEIGHT));
				textField.setText(String.valueOf(field.get(target)));
				textField.setHorizontalAlignment(SwingConstants.LEADING);
				
				JButton button = new JButton();
				button.setHorizontalAlignment(SwingConstants.RIGHT);
				button.setText("Set");
				final Field targetField = field;
				button.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (textField.getText() != null && textField.getText().length() > 0
									&& !textField.getText().equals("null")){
								Number num = 0;
								try{
									num = NumberUtil.toNumber(textField.getText());
								}catch(NumberFormatException ex){
									JOptionPane.showMessageDialog(null, textField.getText() + " is not a valid number.");
									return;
								}
								targetField.set(target, num);
							}else{
								targetField.set(target, null);
							}
						} catch (Exception ex){
							Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
						}
					}
				});
				
				model.addElement(new PanelItem(field.getName(), textField, button));
			}else if(List.class.isAssignableFrom(field.getType())){
				List temp = (List) field.get(target);
				final List list = temp == null ? (List) field.getType().newInstance() : temp;
				Component[] components;
				final Inserter inserter;
				if(Inserter.class.isAssignableFrom(field.getType())){
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
				components = new Component[inserter.numParams()];
				
				for(int i = 0; i < components.length; i++){
					Class type = inserter.getParamType(i);
					if(type.isEnum()){
						JComboBox<String> comboBox = new JComboBox<String>();
						for(Object econst : type.getEnumConstants())
							comboBox.addItem(econst.toString());
						comboBox.setSelectedIndex(0);
						components[i] = comboBox;
					}else{
						JTextField textField = new JTextField();
						textField.setPreferredSize(new Dimension(150, 20));
						components[i] = textField;
					}
				}
				
				final Field targetField = field;
				PanelListEditor listEditor = new PanelListEditor(list, new Runnable(){
					@Override
					public void run() {
						try {
							targetField.set(target, list);
						} catch (Exception ex){
							Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
						}
					}
				}, new PanelItem(null, components));
				
				model.addElement(new PanelItem(field.getName(), listEditor));
			}else if(field.getType().isEnum()){
				JComboBox<String> comboBox = new JComboBox<String>();
				String previousName = String.valueOf(field.get(target));
				int selected = 0;
				for(int i = 0; i < field.getType().getEnumConstants().length; i++){
					String name = field.getType().getEnumConstants()[i].toString();
					comboBox.addItem(name);
					if(previousName.equalsIgnoreCase(name))
						selected = i;
				}
				comboBox.setSelectedIndex(selected);
				
				final Field targetField = field;
				comboBox.addItemListener(new ItemListener(){
					@Override
					public void itemStateChanged(ItemEvent e) {
						int state = e.getStateChange();
						if (state == ItemEvent.SELECTED) {
							Class<? extends Enum> cast = (Class<? extends Enum<?>>) targetField.getType();
							String name = (String) e.getItemSelectable().getSelectedObjects()[0];
							try {
								targetField.set(target, Enum.valueOf(cast, name));
							} catch (Exception ex){
								Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
							}
						}
					}
				});
				
				model.addElement(new PanelItem(field.getName(), comboBox));
			}else{
				final JTextField textField = new JTextField();
				textField.setPreferredSize(new Dimension(TEXTFIELDWIDTH, TEXTFIELDHEIGHT));
				textField.setText(String.valueOf(field.get(target)));
				textField.setHorizontalAlignment(SwingConstants.LEADING);
				
				JButton button = new JButton();
				button.setHorizontalAlignment(SwingConstants.RIGHT);
				button.setText("Set");
				final Field targetField = field;
				button.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (textField.getText() != null && textField.getText().length() > 0
									&& !textField.getText().equals("null")){
								targetField.set(target, textField.getText());
							}else{
								targetField.set(target, null);
							}
						} catch (Exception ex){
							Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
						}
					}
				});
				
				model.addElement(new PanelItem(field.getName(), textField, button));
			}
		}
	}

	public ConfigurationSerializable getTarget() {
		return target;
	}

}
