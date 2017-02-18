package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class BooleanEditor extends JPanel {
    public BooleanEditor(final WrapEventHandler<Boolean> handle, boolean def){
        setLayout(new BorderLayout(5, 5));

        JCheckBox chckbxChekcbox = new JCheckBox("");
        add(chckbxChekcbox, BorderLayout.CENTER);

        chckbxChekcbox.setSelected(def);
        chckbxChekcbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                handle.onSet(abstractButton.getModel().isSelected());
            }
        });
    }
}
