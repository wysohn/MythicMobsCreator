package com.naver.wysohn2002.mythicmobcreator.main.editors.defaults;

public class DoubleEditor extends NumberEditor {

    public DoubleEditor(WrapEventHandler<Double> handle, Double def) {
        super(handle, def);
    }

    @Override
    protected void setNumber(WrapEventHandler handle, String input) throws NumberFormatException {
        handle.onSet(Double.parseDouble(input));
    }
}
