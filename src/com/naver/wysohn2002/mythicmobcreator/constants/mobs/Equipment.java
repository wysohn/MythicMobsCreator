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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.EmptyListEditor;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;

public class Equipment extends ArrayList<String> implements CustomValue{
    @Override
    public JPanel getEditor() {
        return new Editor(this);
    }

    private static final Map<Integer, Parts> valueMap = new HashMap<Integer, Parts>();
    public enum Parts{
        MainHand(0), Helmet(1), Chest(2), Leggings(3), Boots(4), OffHand(5);
        private final int realVal;

        private Parts(int realVal) {
            this.realVal = realVal;
            valueMap.put(realVal, this);
        }
        @Override
        public String toString(){
            return name();
        }
        public static Parts fromValue(int val){
            return valueMap.get(val);
        }
    }

    private class Editor extends EmptyListEditor{
        private JPanel panelNorth;

        private JComboBox<Parts> partSelector;
        private JTextField itemField;

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

            partSelector = new JComboBox<Parts>(Parts.values());
            panelNorth.add(partSelector);

            partSelector.setEditable(false);

            itemField = new JTextField(10);
            panelNorth.add(itemField);
        }

        @Override
        protected void onAdd() {
            list.add(itemField.getText()+":"+((Parts) partSelector.getSelectedItem()).realVal);

            this.updateJList();
        }

        @Override
        protected void onEdit() {
            int selected = jList.getSelectedIndex();
            if(selected < 0)
                return;

            list.set(selected, itemField.getText()+":"+((Parts) partSelector.getSelectedItem()).realVal);

            this.updateJList();
        }

        @Override
        protected void onListItemSelected(List<Integer> selectedIndices) {
            if(selectedIndices.size() < 1)
                return;

            int selected = selectedIndices.get(0);

            String[] split = list.get(selected).split(":", 2);
            String item = split[0];
            String slot = split[1];
            Parts part = Parts.fromValue(Integer.parseInt(slot));
            if(part == null)
                return;

            partSelector.setSelectedItem(part);
            itemField.setText(item);
        }

    }
}
