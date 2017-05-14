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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.BooleanEditor;
import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.EnumEditor;
import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.NumberEditor;
import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.SimpleListEditor;
import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.StringEditor;
import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.WrapEventHandler;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;
import com.naver.wysohn2002.mythicmobcreator.util.NumberUtil;

public class PanelResourceEditor extends JPanel {
	private ConfigurationSerializable target;

	private JPanel panel;
	/**
	 * Create the panel.
	 */
	public PanelResourceEditor(ConfigurationSerializable target) {
		this.target = target;

		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 5, 5));

		notifyDataChanged();
	}

    public void notifyDataChanged() {
        panel.removeAll();
        DefaultListModel<PanelItem> model = new DefaultListModel();
        try {
            addItems(model);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | InstantiationException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < model.size(); i++) {
            panel.add(model.get(i));
        }
    }

	private void addItems(DefaultListModel<PanelItem> model)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, InstantiationException {
		addItems(model, target);
	}

	private static final int TEXTFIELDWIDTH = 300;
	private static final int TEXTFIELDHEIGHT = 30;
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
				Boolean previous = (Boolean) field.get(target);
				final Field targetField = field;

				model.addElement(new PanelItem(field.getName(), new BooleanEditor(new WrapEventHandler<Boolean>(){
                    @Override
                    public void onSet(Boolean newValue) {
                        try {
                            targetField.set(target, newValue);
                        } catch (Exception ex){
                            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
                }, Boolean.valueOf(previous == null ? false : previous))));
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

				model.addElement(new PanelItem(field.getName(), new NumberEditor(new WrapEventHandler<Number>(){
                    @Override
                    public void onSet(Number newValue) {
                        try {
                            targetField.set(target, newValue);
                        } catch (Exception ex){
                            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
				}, (Number) field.get(target))));
			}else if(List.class.isAssignableFrom(field.getType())){
				List temp = (List) field.get(target);
				final List list = temp == null ? (List) field.getType().newInstance() : temp;
				final CustomValue customValue;
				if(CustomValue.class.isAssignableFrom(field.getType())){
					customValue = (CustomValue) list;
				}else{
					customValue = new CustomValue(){
                        @Override
                        public JPanel getEditor() {
                            return new SimpleListEditor(list);
                        }
					};
				}

				field.set(target, list);

				trimList(list);
				model.addElement(new PanelItem(field.getName(), customValue));
			}else if(field.getType().isEnum()){
				final Field targetField = field;

				Class<? extends Enum> clazz = (Class<? extends Enum<?>>) targetField.getType();
				model.addElement(new PanelItem(field.getName(), new EnumEditor(new WrapEventHandler(){
                    @Override
                    public void onSet(Object newValue) {
                        try {
                            targetField.set(target, newValue);
                        } catch (Exception ex){
                            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
				}, clazz, (Enum) field.get(target))));
			}else{
				final Field targetField = field;
				model.addElement(new PanelItem(field.getName(), new StringEditor(new WrapEventHandler<String>(){
                    @Override
                    public void onSet(String newValue) {
                        try {
                            targetField.set(target, newValue);
                        } catch (Exception ex){
                            Main.LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                        }
                    }
				}, (String) field.get(target))));
			}
		}
	}

	private void trimList(List<String> list) {
       for(ListIterator<String> iter = list.listIterator(); iter.hasNext();){
           String str = iter.next();
           iter.set(str.trim());
       }
    }

    public ConfigurationSerializable getTarget() {
		return target;
	}

}
