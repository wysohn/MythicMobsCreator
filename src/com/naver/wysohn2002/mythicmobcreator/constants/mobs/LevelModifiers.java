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
import com.naver.wysohn2002.mythicmobcreator.util.Randomizable;

public class LevelModifiers extends Randomizable<LevelModifiers> implements ConfigurationSerializable{
    @RandomLimit(doubleMax = 512.0)
    public Number Health;
    @RandomLimit(doubleMax = 10.0)
    public Number Damage;
    @RandomLimit(doubleMax = 0.5)
    public Number KnockbackResistance;
    @RandomLimit(intMax = 10, doubleMax = 10.0)
    public Number Power;
    @RandomLimit(intMax = 10, doubleMax = 10.0)
    public Number Armor;
    @RandomLimit(doubleMin = 0.01, doubleMax = 0.1)
    public Number MovementSpeed;
    @RandomLimit(doubleMin = 0.5, doubleMax = 0.8)
    public Number AttackSpeed;

    ///////////////////////////////////////////////////////////////////////////////////////////
    public static LevelModifiers deserialize(Map<String, Object> ser) {
        try {
            return (LevelModifiers) ClassSerializer.deserialize(LevelModifiers.class, ser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        try {
            return ClassSerializer.serialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public LevelModifiers createInstance() {
        return new LevelModifiers();
    }
}
