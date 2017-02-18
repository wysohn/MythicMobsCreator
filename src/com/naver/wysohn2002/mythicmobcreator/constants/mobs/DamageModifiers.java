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
package com.naver.wysohn2002.mythicmobcreator.constants.mobs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.EmptyListEditor;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;

public class DamageModifiers extends ArrayList<String> implements CustomValue{
    @Override
    public JPanel getEditor() {
        return new Editor(this);
    }

	public static enum Modifiers{
		DROWNING,
		BLOCK_EXPLOSION,
		ENTITY_EXPLOSION,
		VOID,
		LIGHTNING,
		SUICIDE,
		STARVATION,
		POISON,
		MAGIC,
		DRAGON_BREATH,
		WITHER,
		FALLING_BLOCK,
		THORNS,
		CUSTOM,
		LAVA,
		MELTING,
		FIRE_TICK,
		FIRE,
		HOT_FLOOR,
		FALL,
		SUFFOCATION,
		PROJECTILE,
		ENTITY_ATTACK,
		CONTACT,
		;
	}

    private class Editor extends EmptyListEditor {
        private JPanel panelNorth;

        private Modifiers modifier = Modifiers.DROWNING;
        private Double value = null;

        private JComboBox<Modifiers> modifierSelector;
        private JTextField valueField = null;

        public Editor(List<String> targetList) {
            super(targetList);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BorderLayout());

            // tweak jList
            this.mainPanel.remove(this.scrollPane);
            listPanel.add(this.scrollPane, BorderLayout.CENTER);
            jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            this.mainPanel.add(listPanel);

            panelNorth = new JPanel();
            listPanel.add(panelNorth, BorderLayout.NORTH);

            panelNorth.setLayout(new FlowLayout());

            modifierSelector = new JComboBox<Modifiers>();
            panelNorth.add(modifierSelector);

            for (Modifiers modifier : Modifiers.values())
                modifierSelector.addItem(modifier);
            modifierSelector.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int state = e.getStateChange();
                    if (state == ItemEvent.SELECTED) {
                        Object[] selecteds = e.getItemSelectable().getSelectedObjects();
                        if (selecteds.length > 0) {
                            Modifiers modifier = (Modifiers) selecteds[0];
                            onModifierSelect(modifier);
                        }
                    }
                }
            });
            modifierSelector.setEditable(false);

            valueField = new JTextField(10);
            panelNorth.add(valueField);

            valueField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    setValue();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    setValue();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    setValue();
                }

                private void setValue() {
                    try{
                        value = Double.parseDouble(valueField.getText());
                    }catch(NumberFormatException e){
                        valueField.setBackground(Color.red);
                    }
                }
            });
        }

        @Override
        protected void onListItemSelected(List<Integer> selectedIndices) {
            if (selectedIndices.size() < 1)
                return;

            int selected = selectedIndices.get(0);

            String selectedValue = this.list.get(selected);
            String[] split = selectedValue.split(" ", 2);

            Modifiers m = Modifiers.valueOf(split[0]);
            String value = null;
            if (split.length == 2) {
                value = split[1];
            }

            updateValues(m, value);
        }

        private void updateValues(Modifiers m, String value) {
            modifierSelector.setSelectedItem(m);
            if (value != null && valueField != null) {
                valueField.setText(value);
            }
        }

        @Override
        protected void onAdd() {
            if(this.value == null)
                return;

            this.list.add(modifier + " " + value);

            this.updateJList();
        }

        @Override
        protected void onEdit() {
            int selected = this.getSelectedIndex();
            if (selected == -1)
                return;

            if(this.value == null)
                return;

            this.list.set(selected, modifier + " " + value);

            this.updateJList();
        }

        private void onModifierSelect(Modifiers modifier) {
            this.modifier = modifier;
        }
    }
}
