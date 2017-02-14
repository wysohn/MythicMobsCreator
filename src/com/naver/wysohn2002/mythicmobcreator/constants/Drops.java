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
