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

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.NumberedListEditor;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;

public class AITargetSelector extends ArrayList<String> implements CustomValue {

    @Override
    public JPanel getEditor() {
        return new Editor(this);
    }

    public static enum Targets {
        clear,
        attacker,
        monsters,
        players,
        villagers,
        golems,
        OtherFaction,
        OtherFactionMonsters,
        OtherFactionVillagers,
        SpecificFaction(true),
        SpecificFactionMonsters(true),

        ;
        private final boolean hasParams;

        private Targets() {
            hasParams = false;
        }

        private Targets(boolean hasParams) {
            this.hasParams = hasParams;
        }

        public boolean isHasParams() {
            return hasParams;
        }
    }

    private class Editor extends NumberedListEditor {
        private JPanel panelNorth;

        private Targets target = Targets.clear;
        private String param = null;

        private JComboBox<Targets> targetSelector;

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

            targetSelector = new JComboBox<Targets>();
            panelNorth.add(targetSelector);

            for (Targets target : Targets.values())
                targetSelector.addItem(target);
            targetSelector.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int state = e.getStateChange();
                    if (state == ItemEvent.SELECTED) {
                        Object[] selecteds = e.getItemSelectable().getSelectedObjects();
                        if (selecteds.length > 0) {
                            Targets target = (Targets) selecteds[0];
                            onTargetSelect(target);
                        }
                    }
                }
            });
            targetSelector.setEditable(false);
        }

        @Override
        protected void onListItemSelected(List<Integer> selectedIndices) {
            if (selectedIndices.size() < 1)
                return;

            int selected = selectedIndices.get(0);

            String value = this.list.get(selected);
            String[] split = value.split(" ", 2);

            Targets g = Targets.valueOf(split[0]);
            String option = null;
            if (split.length == 2) {
                option = split[1];
            }

            updateValues(g, option);
        }

        private void updateValues(Targets t, String option) {
            targetSelector.setSelectedItem(t);
            if (option != null && optionField != null) {
                optionField.setText(option);
            }
        }

        @Override
        protected void onAdd() {
            if (this.param != null)
                this.list.add(target + " " + param);
            else
                this.list.add(target.toString());

            this.updateJList();
        }

        @Override
        protected void onEdit() {
            int selected = this.getSelectedIndex();
            if (selected == -1)
                return;

            if (this.param != null)
                this.list.set(selected, target + " " + param);
            else
                this.list.set(selected, target.toString());

            this.updateJList();
        }

        private JTextField optionField = null;

        private void onTargetSelect(Targets target) {
            this.target = target;

            if (optionField != null) {
                panelNorth.remove(optionField);
                param = null;
            }

            if (target.hasParams) {
                optionField = new JTextField();
                panelNorth.add(optionField);

                optionField.setColumns(10);
                optionField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        setText();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        setText();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        setText();
                    }

                    private void setText() {
                        param = optionField.getText().length() < 1 ? null : optionField.getText();
                    }
                });


            }

            revalidate();
            repaint();
        }
    }
}
