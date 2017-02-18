package com.naver.wysohn2002.mythicmobcreator.constants.droptables;

public enum Action{
    required,
    cancel,
    power(true),
    cast(true),
    castinstead(true),
    ;
    private final boolean hasVar;
    private Action(boolean hasVar){
        this.hasVar = hasVar;
    }
    private Action(){
        this.hasVar = false;
    }
}