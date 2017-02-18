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
