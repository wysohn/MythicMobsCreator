package com.naver.wysohn2002.mythicmobcreator.util;

import java.util.List;

public interface Inserter {
	int numParams();
	void add(List<String> params);
	void set(int index, List<String> params);
	Class getParamType(int index);
}
