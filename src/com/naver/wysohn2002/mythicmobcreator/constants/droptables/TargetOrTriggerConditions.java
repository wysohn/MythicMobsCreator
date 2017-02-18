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
package com.naver.wysohn2002.mythicmobcreator.constants.droptables;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.EmptyListEditor;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;

public class TargetOrTriggerConditions extends ArrayList<String> implements CustomValue {

    @Override
    public JPanel getEditor() {
        return new Editor(this);
    }

    public static enum TargetTriggerCondition{
        distance(true, false),
        lineofsight(false, true),
        owner(false, true),
        ;
        private final boolean hasParam;//x{...}
        private final boolean hasVar;//x{...} [var]
        private TargetTriggerCondition(boolean hasParam, boolean hasVar){
            this.hasParam = hasParam;
            this.hasVar = hasVar;

        }
        private TargetTriggerCondition(){
            this.hasParam = false;
            this.hasVar = false;
        }
    }

    private class Editor extends EmptyListEditor{
        private JPanel panelNorth;

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


        }

    }
}
