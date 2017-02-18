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

public class Conditions extends ArrayList<String> implements CustomValue {

    @Override
    public JPanel getEditor() {
        return new Editor(this);
    }

    public static enum GeneralCondition{
        globalscore(true, false),
        altitude(true, false),
        biome(false, true),
        crouching(false, true),
        distancefromspawn(false, true),
        entitytype(false, true),
        fallspeed(true, false),
        gliding(false, true),
        haspotioneffect(true, false),
        hastag(true, false),
        height(false, true),
        heightabove(false, true),
        heightbelow(false, true),
        holding(false, true),
        inblock(false, true),
        incombat,
        inregion(false, true),
        notinregion(false, true),
        lastsignal(true, false),
        level(false, true),
        lightlevel(true, false),
        lunarphase(true, false),
        mobsinchunk(false, true),
        mobsinworld(false, true),
        mobtype(false, true),
        offgcd,
        onblock(false, true),
        onground(false, true),
        outside(false, true),
        inside(false, true),
        playerkills(false, true),
        playernotwithin(false, true),
        targetnotwithin(false, true),
        playerwithin(false, true),
        targetwithin(false, true),
        raining(false, true),
        score(true, false),
        stance(true, false),
        sunny(false, true),
        targetdistance (false, true),
        thundering(false, true),
        world(false, true),
        worldtime(false, true),
        ;
        private final boolean hasParam;//x{...}
        private final boolean hasVar;//x{...} [var]
        private GeneralCondition(boolean hasParam, boolean hasVar){
            this.hasParam = hasParam;
            this.hasVar = hasVar;

        }
        private GeneralCondition(){
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
