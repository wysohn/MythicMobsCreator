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
package com.naver.wysohn2002.mythicmobcreator.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.main.Main;

public class ClassSerializer {
    public static Object deserialize(Class<?> clazz, Map<String, Object> map) throws InstantiationException,
            IllegalAccessException, IllegalArgumentException, NoSuchFieldException, SecurityException {
        Object result = clazz.newInstance();
        if (map == null)
            return result;

        for (Entry<String, Object> entry : map.entrySet()) {
            try {
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
            } catch (Exception e) {
                Main.LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
