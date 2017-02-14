package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.ArrayList;
import java.util.List;

import com.naver.wysohn2002.mythicmobcreator.util.Inserter;
import com.naver.wysohn2002.mythicmobcreator.util.NumberUtil;

public class Skills extends ArrayList<String> implements Inserter{
	//Mechanics, String, Targeters, String, Triggers, String, String, Number
	//mechanic{option=value} @[targeter][{optional}] ~on[trigger] [health_modifier] [chance]
	
	public void add(String mech, String mechopt, Targeters tar, String taropt, Triggers trig, String trigopt,
			String healthModifier, Number chance) {
		this.add(getString(mech, mechopt, tar, taropt, trig, trigopt, healthModifier, chance));
	}
	
	private String getString(String mech, String mechopt, Targeters tar, String taropt, Triggers trig, String trigopt,
			String healthModifier, Number chance){
		if(mechopt != null && mechopt.length() < 1)
			mechopt = null;
		
		if(taropt != null && taropt.length() < 1)
			taropt = null;
		
		if(trigopt != null && trigopt.length() < 1)
			trigopt = null;
		
		if(healthModifier != null && healthModifier.length() < 1)
			healthModifier = null;
		
		return mech + (mechopt == null ? "" : "{"+mechopt+"}") + " "
				+ tar + (taropt == null ? "" : (tar.isVanilla ? "[" + taropt + "]" : "{" + taropt + "}")) + " "
				+ trig + (trigopt == null ? "" :(trig.hasParam ? "{" + trigopt + "}" : "")) + " "
				+ (healthModifier == null ? "" : healthModifier) + " "
				+ (chance == null ? "" : ""+chance);
	}
	
	@Override
	public int numParams() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public void add(List<String> params) {
		String mech = params.get(0);
		String mechopt = params.get(1);
		Targeters tar = Targeters.valueOf(params.get(2).replaceAll("@", ""));
		String taropt = params.get(3);
		Triggers trig = Triggers.valueOf(params.get(4).replaceAll("\\~on", ""));
		String trigopt = params.get(5);
		String healthModifier = params.get(6);
		Number chance = NumberUtil.toNumber(params.get(7));
		
		add(mech, mechopt, tar, taropt, trig, trigopt, healthModifier, chance);
	}

	@Override
	public void set(int index, List<String> params) {
		String mech = params.get(0);
		String mechopt = params.get(1);
		Targeters tar = Targeters.valueOf(params.get(2).replaceAll("@", ""));
		String taropt = params.get(3);
		Triggers trig = Triggers.valueOf(params.get(4).replaceAll("\\~on", ""));
		String trigopt = params.get(5);
		String healthModifier = params.get(6);
		Number chance = NumberUtil.toNumber(params.get(7));
		
		set(index, getString(mech, mechopt, tar, taropt, trig, trigopt, healthModifier, chance));
	}

	@Override
	public Class getParamType(int index) {
		switch(index){
		case 0:
			return Mechanics.class;
		case 2:
			return Targeters.class;
		case 4:
			return Triggers.class;
		case 7:
			return Number.class;
		default:
			return String.class;
		}
	}
	
	public static enum Mechanics{
		Arrow_Volley,
		Base_Damage,
		Command,
		Consume,
		Damage,
		Doppleganger,
		Explosion,
		Force_Pull,
		Heal,
		Heal_Percent,
		Ignite,
		JSON_Message,
		Leap,
		Lightning,
		Message,
		Modify_Score,
		Modify_Target_Score,
		Mount_Target,
		Potion,
		Prison,
		Pull,
		Rally,
		Random_Message,
		Remove,
		Send_Action_Message,
		Set_Gliding,
		Send_Title_Message,
		Set_Score,
		Set_Target_Score,
		Set_Stance,
		Shoot_Fireball,
		Shoot_Potion,
		Shoot_Skull,
		Signal,
		Spring,
		Summon,
		Teleport,
		TeleportTo,
		Threat,
		Throw,
		Velocity,
		Disguise,
		Dismount,
		Eject_Passenger,
		Equip,
		Global_Cooldown,
		Jump,
		Modify_Global_Score,
		Modify_Mob_Score,
		Mount,
		Remount,
		Set_Global_Score,
		Set_Mob_Score,
		Set_Level,
		Suicide,
		Weather,
		Delay,
		Missile,
		Orbital,
		Projectile,
		Shoot,
		Skill,
		Random_Skill,
		Totem,
		Activate_Spawner,
		Push_Button,
		Toggle_Lever,
		;
		
		@Override
		public String toString(){
			return super.name().replaceAll("_", "").toLowerCase();
		}
	}
	
	public static enum Targeters{
		Self,
		Target,
		Trigger,
		NearestPlayer,
		WolfOwner,
		Mount,
		LivingEntitiesInRadius,
		PlayersInRadius,
		MobsInRadius,
		EntitiesInRadius,
		PlayersInWorld,
		PlayersOnServer,
		PlayersInRing,
		PlayersNearOrigin,
		MobsNearOrigin,
		EntitiesNearOrigin,
		RandomThreatTarget,
		ThreatTable,
		ThreatTablePlayers,
		SelfLocation,
		TargetLocation,
		TriggerLocation,
		Location,
		Origin,
		Spawner,
		RLNTE,
		PlayerLocationsInRadius,
		Ring,
		Cone,
		EntitiesInCone,
		Line,
		EntitiesInLine,
		p(true),
		a(true),
		r(true),
		e(true),
		;
		private final boolean isVanilla;
		private Targeters(){
			this.isVanilla = false;
		}
		private Targeters(boolean isVanilla){
			this.isVanilla = isVanilla;
		}
		public boolean isVanilla() {
			return isVanilla;
		}
		@Override
		public String toString(){
			return "@"+this.name();
		}
	}
	
	public static enum Triggers{
		Combat,
		Attack,
		Damaged,
		Spawn,
		FirstSpawn,
		Death,
		Timer(true),
		Interact,
		KillPlayer,
		PlayerDeath,
		EnterCombat,
		DropCombat,
		ChangeTarget,
		Explode,
		Teleport,
		Signal(true),
		;
		private final boolean hasParam;
		private Triggers(){
			this.hasParam = false;
		}
		private Triggers(boolean hasParam){
			this.hasParam = hasParam;
		}
		public boolean hasParam() {
			return hasParam;
		}
		@Override
		public String toString(){
			return "~on"+this.name();
		}
	}
	
	
}
