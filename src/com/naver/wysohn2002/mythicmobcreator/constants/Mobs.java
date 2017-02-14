package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;

import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;

public class Mobs implements ConfigurationSerializable{
	public EntityType Type = EntityType.ZOMBIE;
	public String Display;
	public Number Health;
	public Number Damage;
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




}
