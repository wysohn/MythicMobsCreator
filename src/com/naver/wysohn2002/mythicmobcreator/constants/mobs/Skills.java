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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.naver.wysohn2002.mythicmobcreator.main.editors.defaults.EmptyListEditor;
import com.naver.wysohn2002.mythicmobcreator.util.CustomValue;

public class Skills extends ArrayList<String> implements CustomValue{
    @Override
    public JPanel getEditor() {
        return new Editor(this);
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

	private class Editor extends EmptyListEditor{

        public Editor(List<String> targetList) {
            super(targetList);
            // TODO Auto-generated constructor stub
        }

	}
}
