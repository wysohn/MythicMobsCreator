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

public class LevelModifiers implements ConfigurationSerializable{
    public Number Health;
    public Number Damage;
    public Number KnockbackResistance;
    public Number Power;
    public Number Armor;
    public Number MovementSpeed;
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
}