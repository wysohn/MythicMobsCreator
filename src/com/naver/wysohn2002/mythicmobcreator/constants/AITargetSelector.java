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

public class AITargetSelector extends ArrayList<String> implements Inserter{
	
	public void addTargets(Targets target, String... params){
		String paramSer = appendParams(params).trim();
		this.add(this.size()+" "+target+" "+paramSer);
	}

	private String appendParams(String[] params) {
		StringBuilder builder = new StringBuilder();
		for(String param : params)
			builder.append(param+" ");
		return builder.toString();
	}
	
	@Override
	public int numParams() {
		return 2;
	}

	@Override
	public void add(List<String> params) {
		Targets target = Targets.valueOf(params.get(0));
		if(target.hasParams){
			addTargets(target, params.get(1));
		}else{
			addTargets(target);
		}
	}
	
	@Override
	public Class getParamType(int index) {
		return index == 0 ? Targets.class : String.class;
	}
	
	@Override
	public void set(int index, List<String> params) {
		Targets target = Targets.valueOf(params.get(0));
		if(target.hasParams){
			this.set(index, index+" "+target+" "+params.get(1));
		}else{
			this.set(index, index+" "+target);
		}
		
	}
	
	public static enum Targets{
		clear,
		attacker,
		monsters,
		players,
		villagers,
		golems,
		OtherFaction ,
		OtherFactionMonsters ,
		OtherFactionVillagers ,
		SpecificFaction(true),
		SpecificFactionMonsters(true),
		
		;
		private final boolean hasParams;
		private Targets() {
			hasParams = false;
		}
		private Targets(boolean hasParams) {
			this.hasParams = hasParams;
		}
		public boolean isHasParams() {
			return hasParams;
		}
	}
}
