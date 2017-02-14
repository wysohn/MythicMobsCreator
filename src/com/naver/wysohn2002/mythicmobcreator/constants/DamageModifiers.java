package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.ArrayList;
import java.util.List;

import com.naver.wysohn2002.mythicmobcreator.util.Inserter;
import com.naver.wysohn2002.mythicmobcreator.util.NumberUtil;

public class DamageModifiers extends ArrayList<String> implements Inserter{
	public void addModifier(Modifiers modifier, Number value){
		this.add(modifier + " " + value);
	}
	
	public void setModifier(int index, Modifiers modifier, Number value){
		this.set(index, modifier + " " + value);
	}
	
	@Override
	public int numParams() {
		return 2;
	}

	@Override
	public void add(List<String> params) {
		Modifiers modifier = Modifiers.valueOf(params.get(0));
		addModifier(modifier, NumberUtil.toNumber(params.get(1)));
	}

	@Override
	public void set(int index, List<String> params) {
		Modifiers modifier = Modifiers.valueOf(params.get(0));
		setModifier(index, modifier, NumberUtil.toNumber(params.get(1)));
	}

	@Override
	public Class getParamType(int index) {
		return index == 0 ? Modifiers.class : String.class;
	}
	
	public static enum Modifiers{
		DROWNING,
		BLOCK_EXPLOSION,
		ENTITY_EXPLOSION,
		VOID,
		LIGHTNING,
		SUICIDE,
		STARVATION,
		POISON,
		MAGIC,
		DRAGON_BREATH,
		WITHER,
		FALLING_BLOCK,
		THORNS,
		CUSTOM,
		LAVA,
		MELTING,
		FIRE_TICK,
		FIRE,
		HOT_FLOOR,
		FALL,
		SUFFOCATION,
		PROJECTILE,
		ENTITY_ATTACK,
		CONTACT,
		;
	}


}
