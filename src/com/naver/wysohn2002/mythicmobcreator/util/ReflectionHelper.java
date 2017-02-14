package com.naver.wysohn2002.mythicmobcreator.util;

import java.lang.reflect.Field;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class ReflectionHelper {
	public static Map extractMap(ConfigurationSection section)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if(section == null)
			return null;
		
		Field field = section.getClass().getDeclaredField("map");
		field.setAccessible(true);
		return (Map) field.get(section);
	}
}
