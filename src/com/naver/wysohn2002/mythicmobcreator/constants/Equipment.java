package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.ArrayList;
import java.util.List;

import com.naver.wysohn2002.mythicmobcreator.util.Inserter;

public class Equipment extends ArrayList<String> implements Inserter{
	public Equipment(){
		super(6);
	}
	
	@Override
	public int numParams() {
		return 2;
	}

	@Override
	public void add(List<String> params) {
		String item = params.get(0);
		int pos = Integer.parseInt(params.get(1));
		this.add(item+":"+pos);
	}

	@Override
	public void set(int index, List<String> params) {
		String item = params.get(0);
		int pos = Integer.parseInt(params.get(1));
		this.set(index, item+":"+pos);
	}

	@Override
	public Class getParamType(int index) {
		return String.class;
	}
}
