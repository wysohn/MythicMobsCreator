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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;
import com.naver.wysohn2002.mythicmobcreator.util.Randomizable;

public class Disguise extends Randomizable<Disguise> implements ConfigurationSerializable {
    public Type Type;
    public Boolean Burning;
    public Boolean Inivisible;
    public Boolean ShowName;
    public Boolean Sneaking;
    public Boolean Sprinting;
    @RandomLimit(bool = false)
    public Boolean ModifyBoundingBox;
    public Boolean Glowing;
    public Boolean Gliding;
    public String Skin;
    public String Player;

	//////////////////////////////////////////////////////////////////
	public static Disguise deserialize(Map<String, Object> ser){
		try {
			return (Disguise) ClassSerializer.deserialize(Disguise.class, ser);
		} catch (Exception e){
			e.printStackTrace();
		}

		return new Disguise();
	}
	@Override
	public Map<String, Object> serialize() {
		try {
			return ClassSerializer.serialize(this);
		} catch (Exception e){
			e.printStackTrace();
		}

		return new HashMap<String, Object>();
	}

	public enum Type{
	    arrow,
	    babyzombievillager,
	    bat,
	    blaze,
	    block,
	    boat,
	    cave_spider,
	    chicken,
	    cow,
	    creeper,
	    donkey,
	    dropped_item,
	    egg,
	    ender_crystal,
	    ender_dragon,
	    ender_pearl,
	    ender_signal,
	    enderman,
	    experience_orb,
	    fireball,
	    firework,
	    fishing_hook,
	    ghast,
	    giant,
	    horse,
	    iron_golem,
	    item_frame,
	    leash_hitch,
	    magma_cube,
	    minecart,
	    minecart_chest,
	    minecart_furnace,
	    minecart_hopper,
	    minecart_mob_spawner,
	    minecart_tnt,
	    mule,
	    mushroom_cow,
	    ocelot,
	    painting,
	    pig,
	    pig_zombie,
	    player,
	    polar_bear,
	    primed_tnt,
	    sheep,
	    silverfish,
	    skeleton,
	    skeleton_horse,
	    slime,
	    small_fireball,
	    snowball,
	    snowman,
	    spider,
	    splash_potion,
	    squid,
	    thrown_exp_bottle,
	    undead_horse,
	    villager,
	    witch,
	    wither,
	    wither_skeleton,
	    wither_skull,
	    wolf,
	    zombie,
	    zombievillager,
	    ;
	}

    @Override
    public Disguise createInstance() {
        return new Disguise();
    }
}
