package com.lostshard.lostshard.Spells.Spells;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lostshard.lostshard.Spells.Scroll;
import com.lostshard.lostshard.Spells.Spell;
import com.lostshard.lostshard.Utils.Output;

public class SPL_MoonJump extends Spell {

	public SPL_MoonJump(Scroll scroll) {
		super(scroll);
	}

	@Override
	public void doAction(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 600, 6));
		Output.positiveMessage(player, "You suddenly feel lighter... ");
	}

	@Override
	public void preAction(Player player) {

	}

	@Override
	public boolean verifyCastable(Player player) {
		return true;
	}

}
