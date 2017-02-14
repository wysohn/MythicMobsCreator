package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.ArrayList;
import java.util.List;
import com.naver.wysohn2002.mythicmobcreator.util.Inserter;

public class LevelModifiers extends ArrayList<String> implements Inserter{
	public void addModifier(Modifiers modifier, Number value){
		this.add(modifier + " " + value);
	}
	
	@Override
	public int numParams() {
		return 2;
	}

	@Override
	public void add(List<String> params) {
		Modifiers modifier = Modifiers.valueOf(params.get(0));
		Number num;
		if(params.get(1).contains(".")){
			num = Double.parseDouble(params.get(1));
		}else{
			num = Integer.parseInt(params.get(1));
		}
		addModifier(modifier, num);
	}

	@Override
	public Class getParamType(int index) {
		return index == 0 ? Modifiers.class : Number.class;
	}
	
	@Override
	public void set(int index, List<String> params) {
		Modifiers modifier = Modifiers.valueOf(params.get(0));
		Number num;
		if(params.get(1).contains(".")){
			num = Double.parseDouble(params.get(1));
		}else{
			num = Integer.parseInt(params.get(1));
		}
		this.set(index, modifier + " " + num);
	}
	
	public static enum Modifiers{
		Health,
		Damage,
		KnockbackResistance,
		Power,
		Armor,
		MovementSpeed,
		AttackSpeed;
	}
}
