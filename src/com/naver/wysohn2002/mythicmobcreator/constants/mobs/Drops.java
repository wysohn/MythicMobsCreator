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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.EmptyListEditor;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;
import com.naver.wysohn2002.mythicmobcreator.util.NumberUtil;

public class Drops extends ArrayList<String> implements CustomValue{
    @Override
    public JPanel getEditor() {
        return new DropEditor(this);
    }

	public static enum SpecialDrops{
		champions_exp,
		skillapi_exp,
		heroesexp,
		mcmmo_exp,
		exp,
		money,
		mythicdrop(true),
		phatloot(true),
		;

		private final boolean isItem;

		private SpecialDrops() {
			this.isItem = false;
		}

		private SpecialDrops(boolean isItem) {
			this.isItem = isItem;
		}

		public boolean isItem() {
			return isItem;
		}

		@Override
		public String toString(){
			return super.toString().replaceAll("_", "-");
		}
	}

	public static class DropEditor extends EmptyListEditor {
	    private JPanel panelNorth;
	    private JPanel panelGroup;

	    private JComboBox<DropTypes> typeSelector;

	    public DropEditor(List<String> targetList) {
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

	        //for normal drop
	        typeSelector = new JComboBox<DropTypes>(DropTypes.values());
	        panelNorth.add(typeSelector);

	        typeSelector.addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int state = e.getStateChange();
                    if (state == ItemEvent.SELECTED) {
                        Object[] selecteds = e.getItemSelectable().getSelectedObjects();
                        if(selecteds.length > 0){
                            DropTypes type = (DropTypes) selecteds[0];
                            onTypeSelect(type);
                        }
                    }
                }
            });
	        typeSelector.setEditable(false);

            panelGroup = new JPanel();
            panelNorth.add(panelGroup);

	        onTypeSelect(DropTypes.Drop);
	    }

	    //drop or dropTable
        private JTextField firstField = new JTextField(10);

        //special
        private JComboBox<SpecialDrops> specialTypeSelector = new JComboBox<SpecialDrops>(SpecialDrops.values()){{
            addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int state = e.getStateChange();
                    if (state == ItemEvent.SELECTED) {
                        Object[] selecteds = e.getItemSelectable().getSelectedObjects();
                        if(selecteds.length > 0){
                            SpecialDrops drop = (SpecialDrops) selecteds[0];
                            onSpecialDropSelected(drop);
                        }
                    }
                }

                private void onSpecialDropSelected(SpecialDrops drop) {
                    clearComponents();

                    if(drop.isItem){
                        panelGroup.add(secondField);
                    }else{
                        panelGroup.add(secondField);
                        panelGroup.add(thirdField);
                    }
                }
            });
        }};

        //share(drop & special)
        private JTextField secondField = new JTextField(5);
        private JTextField thirdField = new JTextField(5);

	    private void onTypeSelect(DropTypes type){
	        clearComponents();
	        clearSpecialComponent();

	        switch(type){
	        case Drop:
                panelGroup.add(firstField);
                panelGroup.add(secondField);
                panelGroup.add(thirdField);

	            break;
	        case DropTable:
	            panelGroup.add(firstField);

	            break;
	        case Special:
	            panelGroup.add(specialTypeSelector);
                panelGroup.add(secondField);
                panelGroup.add(thirdField);

	            break;
	        }

	        revalidate();
	        repaint();
	    }

        private void clearComponents() {
            if (firstField != null)
                panelGroup.remove(firstField);
            if (secondField != null)
                panelGroup.remove(secondField);
            if (thirdField != null)
                panelGroup.remove(thirdField);
        }

        private void clearSpecialComponent(){
            if (specialTypeSelector != null)
                panelGroup.remove(specialTypeSelector);
        }

	    @Override
        protected void onListItemSelected(List<Integer> selectedIndices) {

        }

        @Override
        protected void onAdd() {
            String str = createString();
            if(str != null)
                list.add(str);

            this.updateJList();
        }

        @Override
        protected void onEdit() {
            String str = createString();
            if(str != null)
                list.set(jList.getSelectedIndex(), str);

            this.updateJList();
        }

        private String createString() {
            DropTypes type = (DropTypes) typeSelector.getSelectedItem();

            switch(type){
            case Drop:
                String itemName = firstField.getText();
                String amount = secondField.getText();
                String chance = thirdField.getText();

                if(amount.contains("-") && !NumberUtil.integerRange.matcher(amount).matches()){
                    JOptionPane.showMessageDialog(this, amount+" is not a valid range");
                    return null;
                }else if(!NumberUtil.integer.matcher(amount).matches()){
                    JOptionPane.showMessageDialog(this, amount+" is not an integer");
                    return null;
                }

                if (chance.contains("-") && !NumberUtil.rationalRange.matcher(chance).matches()) {
                    JOptionPane.showMessageDialog(this, chance+" is not a valid range");
                    return null;
                }else if(!NumberUtil.rational.matcher(chance).matches()){
                    JOptionPane.showMessageDialog(this, chance+" is not a rational");
                    return null;
                }

                return itemName + " " + amount + " " + chance;
            case DropTable:
                String tableName = firstField.getText();

                return tableName;
            case Special:
                SpecialDrops special = (SpecialDrops) specialTypeSelector.getSelectedItem();
                if(special.isItem){
                    String name = secondField.getText();

                    return special + " " + name;
                }else{
                    String amount2 = secondField.getText();
                    String chance2 = thirdField.getText();

                    if(amount2.contains("-") && !NumberUtil.integerRange.matcher(amount2).matches()){
                        JOptionPane.showMessageDialog(this, amount2+" is not a valid range");
                        return null;
                    }else if(!NumberUtil.integer.matcher(amount2).matches()){
                        JOptionPane.showMessageDialog(this, amount2+" is not an integer");
                        return null;
                    }

                    if (chance2.contains("-") && !NumberUtil.rationalRange.matcher(chance2).matches()) {
                        JOptionPane.showMessageDialog(this, chance2+" is not a valid range");
                        return null;
                    }else if(!NumberUtil.rational.matcher(chance2).matches()){
                        JOptionPane.showMessageDialog(this, chance2+" is not a rational");
                        return null;
                    }

                    return special + " " + amount2 + " " + chance2;
                }
            }

            return null;
        }

        private enum DropTypes{
	        Drop, DropTable, Special;
	    }
	}


}
