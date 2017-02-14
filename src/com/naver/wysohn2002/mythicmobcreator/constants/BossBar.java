package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;

public class BossBar implements ConfigurationSerializable {
	public Boolean Enabled;
	public String Title;
	public Integer Range;
	public Color Color;
	public Style Style;
	public Boolean CreateFog;
	public Boolean DarkenSky;
	public Boolean PlayMusic;
	
	//////////////////////////////////////////////////////////////////
	public static BossBar deserialize(Map<String, Object> ser){
		try {
			return (BossBar) ClassSerializer.deserialize(BossBar.class, ser);
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

	public enum Color{
		PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE;
	}
	
	public enum Style{
		SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20;
	}
}
