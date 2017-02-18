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
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NumberEditor extends JPanel {
    private JTextField textField;
    public NumberEditor(final WrapEventHandler<? extends Number> handle, Number def){
        setLayout(new BorderLayout(0, 0));

        textField = new JTextField();
        add(textField, BorderLayout.CENTER);

        textField.setColumns(20);
        textField.setText(def == null ? "0.0" : def.toString());
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handle();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handle();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handle();
            }

            private void handle() {
                try{
                    setNumber(handle, textField.getText());
                }catch(NumberFormatException ex){
                    textField.setBackground(Color.red);
                }
            }
        });
    }

    protected void setNumber(WrapEventHandler handle, String input) throws NumberFormatException{
        if(input.contains(".")){
            handle.onSet(Double.parseDouble(input));
        }else{
            handle.onSet(Integer.parseInt(input));
        }
    }
}
