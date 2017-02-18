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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class EnumEditor extends JPanel {
    public EnumEditor(final WrapEventHandler<Enum> handle, Class<? extends Enum> clazz, Enum def){
        setLayout(new BorderLayout(5, 5));

        JComboBox<Enum> comboBox = new JComboBox<Enum>(clazz.getEnumConstants());
        add(comboBox, BorderLayout.CENTER);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    Object[] selecteds = e.getItemSelectable().getSelectedObjects();
                    if (selecteds.length > 0) {
                        Enum enumVal = (Enum) selecteds[0];
                        handle.onSet(enumVal);
                    }
                }
            }
        });
        comboBox.setEditable(false);
        comboBox.setSelectedItem(def);
    }
}
