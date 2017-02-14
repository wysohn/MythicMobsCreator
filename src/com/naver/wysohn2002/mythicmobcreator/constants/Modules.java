package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;

public class Modules implements ConfigurationSerializable {
	public Boolean ThreatTable;
	public Boolean ImmunityTable;
	
	//////////////////////////////////////////////////////////////////
	public static Modules deserialize(Map<String, Object> ser){
		try {
			return (Modules) ClassSerializer.deserialize(Modules.class, ser);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public Map<String, Object> serialize() {
		try {
			return ClassSerializer.serialize(this);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
