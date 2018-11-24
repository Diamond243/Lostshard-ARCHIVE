package com.lostshard.lostshard.Spells.Spells;

import org.bukkit.entity.Player;

import com.lostshard.lostshard.Spells.Scroll;
import com.lostshard.lostshard.Spells.Spell;

public class SPL_HealSelf extends Spell {

	public SPL_HealSelf(Scroll scroll) {
		super(scroll);
	}

	@Override
	public void doAction(Player player) {
		double health = player.getHealth();
		health += 10;
		if (health > 20)
			health = 20;
		player.setHealth(health);
	}

	@Override
	public void preAction(Player player) {

	}

	@Override
	public boolean verifyCastable(Player player) {
		return true;
	}

}
