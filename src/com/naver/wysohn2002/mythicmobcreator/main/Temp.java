package com.naver.wysohn2002.mythicmobcreator.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Temp extends JPanel {
    private JTextField conditionField;
    private JTextField actionField;

    /**
     * Create the panel.
     */
    public Temp() {
        setLayout(new GridLayout(0, 3, 5, 5));

        JComboBox conditionSelector = new JComboBox();
        add(conditionSelector);

        conditionSelector.setEditable(false);

        JCheckBox conditionOption = new JCheckBox("option");
        add(conditionOption);

        conditionOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();

                conditionField.setEnabled(selected);
            }
        });

        conditionField = new JTextField();
        conditionField.setEnabled(false);
        add(conditionField);
        conditionField.setColumns(10);

        JComboBox actionSelector = new JComboBox();
        add(actionSelector);

        actionSelector.setEditable(false);

        JCheckBox actionOption = new JCheckBox("option");
        add(actionOption);

        actionOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();

                actionField.setEnabled(selected);
            }
        });

        actionField = new JTextField();
        actionField.setEnabled(false);
        add(actionField);
        actionField.setColumns(10);

    }

}
