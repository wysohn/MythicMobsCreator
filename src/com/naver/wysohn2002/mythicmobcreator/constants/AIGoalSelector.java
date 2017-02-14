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

public class AIGoalSelector extends ArrayList<String> implements Inserter{
	public void addGoal(Goals goal, String... params){
		String paramSer = appendParams(params).trim();
		this.add(this.size()+" "+goal+" "+paramSer);
	}
	
	private String createParams(String[] split) {
		if(split.length < 3)
			return null;
		
		StringBuilder builder = new StringBuilder();
		for(int i = 2; i < split.length; i++){
			builder.append(split[i]);
		}
		
		return builder.toString();
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
		Goals goal = Goals.valueOf(params.get(0));
		if(goal.hasParams){
			addGoal(goal, params.get(1));
		}else{
			addGoal(goal);
		}
	}
	
	@Override
	public Class getParamType(int index) {
		return index == 0 ? Goals.class : String.class;
	}
	
	@Override
	public void set(int index, List<String> params) {
		Goals goal = Goals.valueOf(params.get(0));
		if(goal.hasParams){
			this.set(index, index+" "+goal+" "+params.get(1));
		}else{
			this.set(index, index+" "+goal);
		}
		
	}
	
	public static enum Goals{
		clear,
		breakdoors,
		eatgrass,
		swim,
		lookatplayers,
		opendoors,
		closedoors,
		randomlookaround,
		avoidcreepers,
		avoidskeletons,
		avoidzombies,
		fleesun,
		meleeattack,
		movetowardstarget,
		randomstroll,
		restrictsun,
		fleeplayers,
		fleegolems,
		fleevillagers,
		spiderattack,
		leapattarget,
		moveindoors,
		movethroughvillage,
		movetowardsrestriction,
		patrol(true),
		gotolocation(true),
		gotoowner,
		arrowattack,
		skeletonbowattack,
		;
		
		private final boolean hasParams;
		private Goals() {
			hasParams = false;
		}
		private Goals(boolean hasParams) {
			this.hasParams = hasParams;
		}
		public boolean isHasParams() {
			return hasParams;
		}
		
	}


}
