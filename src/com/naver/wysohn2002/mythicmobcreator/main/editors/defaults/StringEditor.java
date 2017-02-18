package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StringEditor extends JPanel {
    private JTextField textField;
    public StringEditor(final WrapEventHandler<String> handle, String def){
        setLayout(new BorderLayout(0, 0));

        textField = new JTextField();
        add(textField, BorderLayout.CENTER);

        textField.setColumns(20);
        textField.setText(def);
        textField.getDocument().addDocumentListener(new DocumentListener() {
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
                handle.onSet(textField.getText());
            }
        });
    }
}
