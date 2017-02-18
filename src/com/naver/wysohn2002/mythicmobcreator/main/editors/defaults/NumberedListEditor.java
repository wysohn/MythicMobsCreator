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
