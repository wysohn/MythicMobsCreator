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
package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;

import com.naver.wysohn2002.mythicmobcreator.constants.mobs.AIGoalSelector;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.AITargetSelector;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.BossBar;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.DamageModifiers;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Disguise;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Drops;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Equipment;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.LevelModifiers;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Modules;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Options;
import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Skills;
import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;
import com.naver.wysohn2002.mythicmobcreator.util.Randomizable;

public class Mobs extends Randomizable<Mobs> implements ConfigurationSerializable{
    {
        customizedRandom = new CustomizedRandom(){
            @Override
            public boolean isCustomRandom(Class<?> clazz) {
                return clazz == EntityType.class;
            }

            @Override
            public Object onRandomize(Class<?> clazz) {
                Class<? extends Enum> cast = (Class<? extends Enum>) clazz;
                Enum[] enums = cast.getEnumConstants();
                List<Enum> livingEntities = new ArrayList<>();
                for(Enum e : enums)
                    if(((EntityType) e).isAlive())
                        livingEntities.add(e);
                return livingEntities.get(random.nextInt(livingEntities.size()));
            }};
    }
	public EntityType Type;
	public String Display;
	@RandomLimit(doubleMax = 1024.0)
	public Number Health;
	@RandomLimit(doubleMax = 20.0)
	public Number Damage;
	@RandomLimit(doubleMax = 20.0)
	public Number Armor;
	public BossBar BossBar;
	public String Faction;
	public String Mount;
	public Options Options;
	public Modules Modules;
	public AIGoalSelector AIGoalSelector;
	public AITargetSelector AITargetSelector;
	public Drops Drops;
	public Drops DropsPerLevel;
	public DamageModifiers DamageModifiers;
	public Equipment Equipment;
	public String KillMessages;
	public LevelModifiers LevelModifiers;
	public Disguise Disguise;
	public Skills Skills;

	//////////////////////////////////////////////////////////////////
	public static Mobs deserialize(Map<String, Object> ser){
		try {
			return (Mobs) ClassSerializer.deserialize(Mobs.class, ser);
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

    @Override
    public Mobs createInstance() {
        return new Mobs();
    }


}
