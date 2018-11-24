package com.lostshard.Lostshard.Spells.Structures;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import com.lostshard.Lostshard.Spells.MagicStructure;

public class FireWalk extends MagicStructure {

	public static void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		if (!event.getTo().getBlock().getState().equals(event.getFrom().getBlock().getState()))
			for (final MagicStructure ms : getMagicStructures())
				if (ms instanceof FireWalk)
					if (ms.getCreatorUUID().equals(player.getUniqueId()))
						if (event.getTo().getBlock().getType().equals(Material.AIR)) {
							event.getTo().getBlock().setType(Material.FIRE);
							ms.addBlock(event.getTo().getBlock());
						}
	}

	public FireWalk(ArrayList<Block> blocks, UUID uuid, int numTicksTillCleanup) {
		super(blocks, uuid, numTicksTillCleanup);
	}

	@Override
	public void lastThing() {
		final Player player = Bukkit.getPlayer(this.getCreatorUUID());
		if (player != null)
			player.sendMessage(ChatColor.GRAY + "The flames on your feet have extinguished.");
	}

}
