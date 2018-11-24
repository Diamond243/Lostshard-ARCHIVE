package com.lostshard.RPG.Spells;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.lostshard.RPG.PseudoPlayer;
import com.lostshard.RPG.PseudoPlayerHandler;
import com.lostshard.RPG.Utils.Output;
import com.lostshard.RPG.Utils.Utils;

public class SPL_DRG_Lust extends Spell {
	private static final String 	_name = "Lust";
	private static final String 	_spellWords = "Fantasticus Valentinius";
	private static final int 		_castingDelay = 0;
	private static final int 		_cooldownTicks = 20;
	private static final int		_manaCost = 40;
	private static final int[]		_reagentCost = {122, 382};
	private static final int 		_minMagery = 1000;
	private static final int 		_range = 10;
	
	public String getName() 		{ return _name; }
	public String getSpellWords() 	{ return _spellWords; }
	public int getCastingDelay() 	{ return _castingDelay; }
	public int getCooldownTicks()	{ return _cooldownTicks; }
	public int getManaCost() 		{ return _manaCost; }
	public int[] getReagentCost() 	{ return _reagentCost; }
	public int getMinMagery() 		{ return _minMagery; }
	
	public int getPageNumber()		{ return 9; }
	/* Used to confirm that the spell can be cast, so, for example, if you were
	 * attempting to teleport to some location that was blocked, we would figure
	 * that out here and cancel the spell.
	 */
	
	public boolean verifyCastable(Player player, PseudoPlayer pseudoPlayer) {
		return true;
	}
	
	/* Used for anything that must be handled as soon as the spell is cast,
	 * for example targeting a location for a delayed spell.
	 */
	public void preAction(Player player) {
		
	}
	
	/* The meat of the spell code, this is what happens when the spell is
	 * actually activated and should be doing something.
	 */
	public void doAction(Player player) {
		PseudoPlayer pseudoPlayer = PseudoPlayerHandler.getPseudoPlayer(player.getName());
		pseudoPlayer.setCantCastTicks(_cooldownTicks);		

		Player[] players = Bukkit.getOnlinePlayers();
		for(Player p : players) {
			p.setHealth(20);
			p.sendMessage(ChatColor.LIGHT_PURPLE + player.getName()+" loves you ;)");
		}
	}
}
