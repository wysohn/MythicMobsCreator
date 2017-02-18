package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

public class IntegerEditor extends NumberEditor {
    public IntegerEditor(WrapEventHandler<Integer> handle, Double def) {
        super(handle, def);
    }

    @Override
    protected void setNumber(WrapEventHandler handle, String input) throws NumberFormatException {
        handle.onSet(Integer.parseInt(input));
    }
}
