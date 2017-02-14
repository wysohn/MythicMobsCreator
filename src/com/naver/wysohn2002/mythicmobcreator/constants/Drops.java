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

public class Drops extends ArrayList<String> implements Inserter{
	public void addDrop(String item, int amount, double chance){
		this.add(item + " " + amount + " " + chance);
	}
	
	public void addDropTable(String dropTable){
		this.add(dropTable);
	}
	
	public void setDrop(int index, String item, int amount, double chance){
		this.set(index, item + " " + amount + " " + chance);
	}
	
	public void setDropTable(int index, String dropTable){
		this.set(index, dropTable);
	}
	
	@Override
	public int numParams() {
		return 3;
	}

	@Override
	public void add(List<String> params) {
		if(params.size() == 1){
			addDropTable(params.get(0));		
		}else{
			addDrop(params.get(0), Integer.parseInt(params.get(1)), Double.parseDouble(params.get(2)));
		}
	}

	@Override
	public void set(int index, List<String> params) {
		if(params.size() == 1){
			setDropTable(index, params.get(0));		
		}else{
			setDrop(index, params.get(0), Integer.parseInt(params.get(1)), Double.parseDouble(params.get(2)));
		}
	}

	@Override
	public Class getParamType(int index) {
		return String.class;
	}
	
/*	public static enum SpecialDrops{
		champions_exp,
		skillapi_exp,
		heroesexp,
		mcmmo_exp,
		exp,
		money,
		mythicdrop(true),
		phatloot(true),
		;
		
		private final boolean hasParam;

		private SpecialDrops() {
			this.hasParam = false;
		}
		
		private SpecialDrops(boolean hasParam) {
			this.hasParam = hasParam;
		}

		public boolean isHasParam() {
			return hasParam;
		}
		
		@Override
		public String toString(){
			return super.toString().replaceAll("_", "-");
		}
	}*/
}
