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

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Villager.Profession;

import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;

public  class Options implements ConfigurationSerializable{
	public Boolean AlwaysShowName;
	public Number AttackSpeed;
	public Boolean Collidable;
	public Boolean Despawn;
	public Number FollowRange;
	public Boolean Glowing;
	public Boolean Invincible;
	public Number KnockbackResistance;
	public Number MaxCombatDistance;
	public Number MovementSpeed;
	public Boolean NoAI;
	public Number NoDamageTicks;
	public Boolean NoGravity;
	public Boolean Persistent;
	public Boolean PreventItemPickup; 
	public Boolean PreventLeashing;
	public Boolean PreventMobKillDrops;
	public Boolean PreventOtherDrops;
	public Boolean PreventRandomEquipment;
	public Boolean PreventRenaming;
	public Boolean RepeatAllSkills;
	public Boolean ShowHealth;
	public Boolean Silent;
	public Boolean HasArms;
	public Boolean HasGravity;
	public Boolean Invisible;
	public String ItemBody;
	public String ItemFeet;
	public String ItemHand;
	public String ItemHead;
	public String ItemLegs;
	public Boolean Marker;
	public Boolean Small;
	public Pose Pose;
	public Boolean Jockey;
	public Number ExplosionRadius;
	public Number FuseTicks;
	public Boolean SuperCharged;
	public Boolean PreventSuicide;
	public Boolean PreventTeleport;
	public Material Block;
	public Number BlockData;
	public HorseArmor HorseArmor;
	public Boolean HorseCarryingChest;
	public HorseColor HorseColor;
	public Boolean HorseSaddled;
	public HorseStyle HorseStyle;
	public Boolean HorseTamed;
	public HorseType HorseType;
	public Ocelot Ocelot;
	public Boolean Saddled;
	public RabbitType RabbitType;
	public Boolean Baby;
	public Boolean PreventBlockInfection;
	public Boolean PreventSnowFormation;
	public Number ExplosionYield;
	public Boolean Incendiary;
	public VillagerType VillagerType;
	public Number ReinforcementsChance;
	public Profession Profession;
	public Number Age;
	public Boolean AgeLock;
	public Number Color;
	public Boolean Angry;
	public Boolean PreventSlimeSplit;
	public Number Size;
	public Boolean Tameable;
	
	//////////////////////////////////////////////////////////////////
	public static Options deserialize(Map<String, Object> ser){
		try {
			return (Options) ClassSerializer.deserialize(Options.class, ser);
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
	
	public static class Pose implements ConfigurationSerializable{
		public String Head;
		public String Body;
		public String LeftArm;
		public String RightArm;
		public String LeftLeg;
		public String RightLeg;
		
		//////////////////////////////////////////////////////////////////
		public static Pose deserialize(Map<String, Object> ser){
			try {
				return (Pose) ClassSerializer.deserialize(Pose.class, ser);
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
	
	public static enum HorseArmor{
		IRON, GOLD, DIAMOND;
	}
	
	public static enum HorseColor{
		BLACK, BROWN, CHESTNUT, CREAMY, DARK_BROWN, GRAY, WHITE;
	}
	
	public static enum HorseStyle{
		BLACK_DOTS, WHITE, WHITE_DOTS, WHITEFIELD;
	}
	
	public static enum HorseType{
		HORSE, SKELETON_HORSE, ZOMBIE_HORSE;
	}
	
	public static enum Ocelot{
		BLACK_CAT, RED_CAT, SIAMESE_CAT, WILD_OCELOT;
	}
	
	public static enum RabbitType{
		BLACK, BLACK_AND_WHITE, BROWN, GOLD, SALT_AND_PEPPER, THE_KILLER_BUNNY, WHITE;
	}
	
	public static enum VillagerType{
		BLACKSMITH, BUTCHER, FARMER, LIBRARIAN, PRIEST;
	}
}
