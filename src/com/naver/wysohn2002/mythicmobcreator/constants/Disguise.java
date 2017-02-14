package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;

public class Disguise implements ConfigurationSerializable {
	//////////////////////////////////////////////////////////////////
	public static Disguise deserialize(Map<String, Object> ser){
/*		try {
			return (Disguise) ClassSerializer.deserialize(Disguise.class, ser);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;*/
		
		return new Disguise();
	}
	@Override
	public Map<String, Object> serialize() {
/*		try {
			return ClassSerializer.serialize(this);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;*/
		
		return new HashMap<String, Object>();
	}
}
