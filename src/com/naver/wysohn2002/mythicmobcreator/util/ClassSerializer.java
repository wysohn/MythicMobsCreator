package com.naver.wysohn2002.mythicmobcreator.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ClassSerializer {
	public static Object deserialize(Class<?> clazz, Map<String, Object> map) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException, NoSuchFieldException, SecurityException {
		Object result = clazz.newInstance();
		if(map == null)
			return result;
		
		for (Entry<String, Object> entry : map.entrySet()) {
			Field field = clazz.getField(entry.getKey());
			
			if (field.getType().isEnum()) {
				field.set(result, Enum.valueOf((Class<Enum>) field.getType(), String.valueOf(entry.getValue())));
			} else if (List.class.isAssignableFrom(field.getType())) {
				List list = (List) field.getType().newInstance();
				list.addAll((Collection) entry.getValue());
				field.set(result, list);
			} else if (ConfigurationSerializable.class.isAssignableFrom(field.getType())) {
				Map extracted = ReflectionHelper.extractMap((ConfigurationSection) entry.getValue());
				field.set(result, deserialize(field.getType(), extracted));
			} else {
				field.set(result, entry.getValue());
			}
		}

		return result;
	}
	
	public static Map<String, Object> serialize(Object obj) throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> ser = new HashMap<String, Object>();
		
		for(Field field : obj.getClass().getFields()){
			if(field.get(obj) == null)
				continue;

			Object val = field.get(obj);
			if(val == null)
				continue;
			
			if(field.getType().isEnum()){
				ser.put(field.getName(), val.toString());
			}else if(ConfigurationSerializable.class.isAssignableFrom(field.getType())){
				ser.put(field.getName(), serialize(val));
			}else{
				ser.put(field.getName(), val);
			}
		}
		
		return ser;
	}
}
