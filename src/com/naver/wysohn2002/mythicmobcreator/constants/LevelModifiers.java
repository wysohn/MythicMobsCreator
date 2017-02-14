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
