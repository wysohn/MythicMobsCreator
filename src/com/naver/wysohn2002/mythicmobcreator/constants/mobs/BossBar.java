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
package com.naver.wysohn2002.mythicmobcreator.constants.mobs;

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
