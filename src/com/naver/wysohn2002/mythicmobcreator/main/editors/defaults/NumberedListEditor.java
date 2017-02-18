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

import java.util.List;
import java.util.ListIterator;

public class NumberedListEditor extends EmptyListEditor {
    public NumberedListEditor(List<String> targetList) {
        super(targetList);

        for(ListIterator<String> iter = targetList.listIterator(); iter.hasNext();){
            String value = iter.next();
            iter.set(value.split(" ", 2)[1]);
        }
    }

    @Override
    protected void updateJList() {
        String[] strs = new String[list.size()];
        for(int i = 0; i < list.size(); i++){
            strs[i] = i+" "+list.get(i);
        }
        jList.setListData(strs);
    }
}
