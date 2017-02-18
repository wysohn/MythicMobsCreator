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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

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
			return super.name().replaceAll("_", " ").toLowerCase();
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
	    private JPanel panelNorth;

	    private JComboBox<Mechanics> mechanicsSelector;
	    private JComboBox<Targeters> targetSelector;
	    private JComboBox<Triggers> triggerSelector;

	    private JCheckBox mechHasOption;
	    private JCheckBox trgHasOption;
	    private JCheckBox trigHasOption;
	    private  JCheckBox healthHasOption;
	    private JCheckBox chanceHasOption;

	    private JTextField optionFieldMech;
	    private JTextField optionFieldTrg;
	    private JTextField optionFieldTrig;
	    private JTextField optionFieldHealth;
	    private JTextField optionFieldChance;

        public Editor(List<String> targetList) {
            super(targetList);

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BorderLayout());

            // tweak jList
            this.mainPanel.remove(this.scrollPane);
            listPanel.add(this.scrollPane, BorderLayout.CENTER);
            jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            this.mainPanel.add(listPanel);

            panelNorth = new JPanel();
            listPanel.add(panelNorth, BorderLayout.NORTH);

            panelNorth.setLayout(new GridLayout(0, 3, 5, 5));

            mechanicsSelector = new JComboBox<Mechanics>(Mechanics.values());
            panelNorth.add(mechanicsSelector);
            mechanicsSelector.setEditable(false);

            mechHasOption = new JCheckBox("option");
            panelNorth.add(mechHasOption);

            mechHasOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                    boolean selected = abstractButton.getModel().isSelected();

                    optionFieldMech.setEnabled(selected);
                }
            });

            optionFieldMech = new JTextField();
            optionFieldMech.setEnabled(false);
            panelNorth.add(optionFieldMech);
            optionFieldMech.setColumns(10);

            targetSelector = new JComboBox<Targeters>(Targeters.values());
            panelNorth.add(targetSelector);
            targetSelector.setEditable(false);

            trgHasOption = new JCheckBox("option");
            panelNorth.add(trgHasOption);

            trgHasOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                    boolean selected = abstractButton.getModel().isSelected();

                    optionFieldTrg.setEnabled(selected);
                }
            });

            optionFieldTrg = new JTextField();
            optionFieldTrg.setEnabled(false);
            panelNorth.add(optionFieldTrg);
            optionFieldTrg.setColumns(10);

            triggerSelector = new JComboBox<Triggers>(Triggers.values());
            panelNorth.add(triggerSelector);
            triggerSelector.setEditable(false);

            trigHasOption = new JCheckBox("option");
            panelNorth.add(trigHasOption);

            trigHasOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                    boolean selected = abstractButton.getModel().isSelected();

                    optionFieldTrig.setEnabled(selected);
                }
            });

            optionFieldTrig = new JTextField();
            optionFieldTrig.setEnabled(false);
            optionFieldTrig.setColumns(10);
            panelNorth.add(optionFieldTrig);

            JLabel lblNewLabel_1 = new JLabel("HealthModifier:");
            lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
            panelNorth.add(lblNewLabel_1);

            healthHasOption = new JCheckBox("option");
            panelNorth.add(healthHasOption);

            healthHasOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                    boolean selected = abstractButton.getModel().isSelected();

                    optionFieldHealth.setEnabled(selected);
                }
            });

            optionFieldHealth = new JTextField();
            optionFieldHealth.setEnabled(false);
            panelNorth.add(optionFieldHealth);
            optionFieldHealth.setColumns(10);

            JLabel lblNewLabel = new JLabel("Chance:");
            lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            panelNorth.add(lblNewLabel);

            chanceHasOption = new JCheckBox("option");
            panelNorth.add(chanceHasOption);

            chanceHasOption.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                    boolean selected = abstractButton.getModel().isSelected();

                    optionFieldChance.setEnabled(selected);
                }
            });

            optionFieldChance = new JTextField();
            optionFieldChance.setEnabled(false);
            panelNorth.add(optionFieldChance);
            optionFieldChance.setColumns(10);

            clearSelections();
        }

        @Override
        protected void onListItemSelected(List<Integer> selectedIndices) {

        }

        @Override
        protected void onAdd() {
            String str = buildString();
            if(str.length() > 0)
                list.add(str);

            this.updateJList();

            clearSelections();
        }

        @Override
        protected void onEdit() {
            int selected = jList.getSelectedIndex();
            if(selected < 0)
                return;

            String str = buildString();
            if(str.length() > 0)
                list.set(selected, str);

            this.updateJList();

            clearSelections();
        }

        private void clearSelections() {
            triggerSelector.setSelectedItem(null);
            targetSelector.setSelectedItem(null);
        }

        private String buildString() {
            StringBuilder builder = new StringBuilder();
            String temp = null;

            temp = mechanics();
            if(temp != null){
                builder.append(temp);
                builder.append(" ");
            }

            temp = targeters();
            if(temp != null){
                builder.append(temp);
                builder.append(" ");
            }

            temp = triggers();
            if(temp != null){
                builder.append(temp);
                builder.append(" ");
            }

            temp = healthModifier();
            if(temp != null){
                builder.append(temp);
                builder.append(" ");
            }

            temp = chance();
            if(temp != null){
                builder.append(temp);
                builder.append(" ");
            }

            return builder.toString().trim();
        }

        private String chance() {
            if(chanceHasOption.isSelected()){
                return optionFieldChance.getText();
            }else{
                return null;
            }
        }

        private String healthModifier() {
            if(healthHasOption.isSelected()){
                return optionFieldHealth.getText();
            }else{
                return null;
            }
        }

        private String triggers() {
            Triggers trig = (Triggers) triggerSelector.getSelectedItem();
            if(trig == null)
                return null;

            String trigstr = trig.toString();
            if (trigHasOption.isSelected() && trig.hasParam) {
                return trigstr + ":" + optionFieldTrig.getText();
            } else {
                return trigstr;
            }
        }

        private String targeters() {
            Targeters targ = (Targeters) targetSelector.getSelectedItem();
            if(targ == null)
                return null;

            String targstr = targ.toString();
            if(trgHasOption.isSelected()){
                if(targ.isVanilla){
                    return targstr +"["+optionFieldTrg.getText()+"]";
                }else{
                    return targstr +"{"+optionFieldTrg.getText()+"}";
                }
            }else{
                return targstr;
            }
        }

        private String mechanics() {
            Mechanics mech = (Mechanics) mechanicsSelector.getSelectedItem();
            if (mech == null)
                return null;

            String mechstr = mech.toString();
            if (mechHasOption.isSelected()) {
                return mechstr + "{" + optionFieldMech.getText() + "}";
            } else {
                return mechstr;
            }
        }
	}
}
