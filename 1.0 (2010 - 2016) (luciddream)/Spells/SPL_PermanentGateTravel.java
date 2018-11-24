package com.lostshard.RPG.Spells;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.lostshard.RPG.PseudoPlayer;
import com.lostshard.RPG.PseudoPlayerHandler;
import com.lostshard.RPG.RPG;
import com.lostshard.RPG.External.Database;
import com.lostshard.RPG.Plots.Plot;
import com.lostshard.RPG.Plots.PlotHandler;
import com.lostshard.RPG.Skills.Magery;
import com.lostshard.RPG.Skills.Rune;
import com.lostshard.RPG.Skills.Runebook;
import com.lostshard.RPG.Utils.Output;
import com.lostshard.RPG.Utils.Utils;

public class SPL_PermanentGateTravel extends Spell {
	private static final String 	_name = "Permanent Gate Travel";
	private static final String 	_spellWords = "Gatius Permenatus";
	private static final int 		_castingDelay = 20;
	private static final int 		_cooldownTicks = 20;
	private static final int		_manaCost = 100;
	private static final int[]		_reagentCost = {287,331,49};
	private static final int 		_minMagery = 840;
	
	public String getName() 		{ return _name; }
	public String getSpellWords() 	{ return _spellWords; }
	public int getCastingDelay() 	{ return _castingDelay; }
	public int getCooldownTicks()	{ return _cooldownTicks; }
	public int getManaCost() 		{ return _manaCost; }
	public int[] getReagentCost() 	{ return _reagentCost; }
	public int getMinMagery() 		{ return _minMagery; }
	
	public int getPageNumber()		{ return 8; }
	//public boolean isWandable()					 	{ return false; }
	
	String _response;
	public String getPrompt() 						{ return "What rune would you like to open a permanent gate to?"; }
	public void setResponse(String response)		{ _response = response; }
	
	/* Used to confirm that the spell can be cast, so, for example, if you were
	 * attempting to teleport to some location that was blocked, we would figure
	 * that out here and cancel the spell.
	 */
	public boolean verifyCastable(Player player, PseudoPlayer pseudoPlayer) {
		if(player.getWorld() == RPG._newmap && !player.isOp()) {
			Output.simpleError(player, "You cannot do that here.");
			return false;
		}
		
		if(player.getWorld() == RPG._hungryWorld) {
			Output.simpleError(player, "You can't do that here!");
			return false;
		}
		
		return true;
	}
	
	/* Used for anything that must be handled as soon as the spell is cast,
	 * for example targeting a location for a delayed spell.
	 */
	public void preAction(Player player) {
		Output.positiveMessage(player, "You begin casting Permanent Gate Travel...");
	}
	
	/* The meat of the spell code, this is what happens when the spell is
	 * actually activated and should be doing something.
	 */
	public void doAction(Player player) {
		//System.out.println("RSPNS: "+_response);
		PseudoPlayer pseudoPlayer = PseudoPlayerHandler.getPseudoPlayer(player.getName());
		
		Runebook runebook = pseudoPlayer.getRunebook();
		ArrayList<Rune> runes = runebook.getRunes();
		Rune runeFound = null;
		
		int count = 0;
		for(Rune rune : runes) {
			if(!player.isOp() && !pseudoPlayer.isLargerBank() && (count >= 8))
				break;
			if(rune.getLabel().equalsIgnoreCase(_response)) {
				runeFound = rune;
				break;
			}
			count++;
		}
		
		if(runeFound == null) {
			Output.simpleError(player, "You do not have a rune with that name, re-cast spell.");
			pseudoPlayer.setCantCastTicks(_cooldownTicks);
			return;
		}
		
		Location runeLoc = runeFound.getLocation();
		Plot plot = PlotHandler.findPlotAt(runeLoc);
		if((plot == null) || !plot.isProtected() || plot.isOwner(player.getName()) || plot.isCoOwner(player.getName())) {
			plot = PlotHandler.findPlotAt(player.getLocation());
			if((plot == null) || !plot.isProtected() || plot.isOwner(player.getName()) || plot.isCoOwner(player.getName())) {
				// allowed to make perm gate
			}
			else {
				Output.simpleError(player, "Cannot permanent gate from there, not a co-owner of the plot.");
				return;
			}
			
			if(!isValidRuneLocation(player, player.getLocation())) {
				//Output.simpleError(player, "Your current location is blocked.");
				return;
			}
			
			if(!isValidRuneLocation(player, runeLoc)) {
				//Output.simpleError(player, "That location is blocked.");
				return;
			}
			
			Location loc = player.getLocation();
			
			Block destBlock = runeLoc.getWorld().getBlockAt(runeLoc.getBlockX(), runeLoc.getBlockY(), runeLoc.getBlockZ());
			Block srcBlock = player.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			Block extraDestBlock = runeLoc.getWorld().getBlockAt(runeLoc.getBlockX(), runeLoc.getBlockY()+1, runeLoc.getBlockZ());
			Block extraSrcBlock = player.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ());
			ArrayList<Block> blocks = new ArrayList<Block>();
			blocks.add(srcBlock);
			blocks.add(destBlock);
			blocks.add(extraSrcBlock);
			blocks.add(extraDestBlock);
			
			RPG._portalPhysics = false;
			for(int i=0; i<blocks.size(); i++) {
				Block b = blocks.get(i);
				Utils.loadChunkAtLocation(b.getLocation());
				b.getWorld().getBlockAt(b.getLocation()).setType(Material.PORTAL);
			}
			RPG._portalPhysics = true;
			
			PermanentGate gate = new PermanentGate(blocks, player.getName());
			int id = Database.addPermanentGate(gate);
			gate.setId(id);
			Magery.addMagicStructure(gate);
		}
		else Output.simpleError(player, "Cannot permanent gate to there, not a friend of the plot.");
	}
}
