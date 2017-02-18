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
package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class SimpleListEditor extends EmptyListEditor {
    private JTextField textField = new JTextField();
    public SimpleListEditor(List<String> targetList) {
        super(targetList);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());

        // tweak jList
        this.mainPanel.remove(jList);
        listPanel.add(jList, BorderLayout.CENTER);
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.mainPanel.add(listPanel);

        listPanel.add(textField, BorderLayout.NORTH);
        textField.setColumns(50);
    }

    @Override
    protected void onListItemSelected(List<Integer> selectedIndices) {
        if(selectedIndices.size() < 1)
            return;

        int selected = selectedIndices.get(0);

        textField.setText(list.get(selected));
    }

    @Override
    protected void onAdd() {
        list.add(textField.getText());
    }

    @Override
    protected void onEdit() {
        int selected = jList.getSelectedIndex();

        list.set(selected, textField.getText());
    }

}
