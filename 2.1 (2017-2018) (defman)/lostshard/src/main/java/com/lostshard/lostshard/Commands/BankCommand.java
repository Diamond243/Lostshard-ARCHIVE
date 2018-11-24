package com.lostshard.lostshard.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.lostshard.lostshard.Intake.Sender;
import com.lostshard.lostshard.Intake.Vanish;
import com.lostshard.lostshard.Manager.NPCManager;
import com.lostshard.lostshard.Manager.PlayerManager;
import com.lostshard.lostshard.NPC.NPC;
import com.lostshard.lostshard.Objects.Wallet;
import com.lostshard.lostshard.Objects.Player.PseudoPlayer;
import com.lostshard.lostshard.Utils.ItemUtils;
import com.lostshard.lostshard.Utils.Output;
import com.lostshard.lostshard.Utils.Utils;
import com.sk89q.intake.Command;
import com.sk89q.intake.parametric.annotation.Range;

/**
 * @author Jacob Rosborg
 *
 */
public class BankCommand {

	PlayerManager pm = PlayerManager.getManager();
	NPCManager npcm = NPCManager.getManager();

	/**
	 * @param sender
	 *
	 *            Let player access bank.
	 */
	@Command(aliases={"bank"}, 
			desc = "Opens your bank", 
			help="Opens a your bank inventory while close to a banker")
	public void bank(@Sender Player player, @Sender PseudoPlayer pPlayer) {
		for (final NPC npc : this.npcm.getBankers())
			if (Utils.isWithin(player.getLocation(), npc.getLocation(), Variables.bankRadius)) {
				Inventory inv = pPlayer.getBank().getInventory();
				player.openInventory(inv);
				return;
			}
		Output.simpleError(player, "You are not close enough to a bank.");
		return;
	}

	/**
	 * @param sender
	 * @param args
	 *
	 *            Let player pay money to another player.
	 */
	@Command(aliases={"pay"}, 
			desc = "Sends money to antoher player", 
			help="Sends a money to another online player", 
			usage="<player> <amount>")
	public void pay(CommandSender sender, @Vanish Player targetPlayer, @Range(min=1) int amount) {
		if (amount < 1)
			sender.sendMessage(ChatColor.DARK_RED + "Amount must be greater than 0.");
		Wallet from = null;
		final PseudoPlayer tpPlayer = this.pm.getPlayer(targetPlayer);
		if (sender instanceof Player) {
			final Player player = (Player) sender;
			if (player == targetPlayer) {
				Output.simpleError(player, "You may not pay yourself.");
				return;
			}
			final PseudoPlayer pPlayer = this.pm.getPlayer(player);
			if (!pPlayer.getWallet().contains(amount)) {
				Output.simpleError(player, "You can't affort to pay " + targetPlayer.getName() + " "
						+ Utils.getDecimalFormater().format(amount) + "gc.");
				return;
			}
		}
		tpPlayer.getWallet().add(from, amount, "payment");
		sender.sendMessage(ChatColor.GOLD + "You have paid " + targetPlayer.getName() + " "
				+ Utils.getDecimalFormater().format(amount) + "gc.");
		Output.positiveMessage(targetPlayer,
				sender.getName() + " has paid you " + Utils.getDecimalFormater().format(amount) + "gc.");
	}

	/**
	 * @param sender
	 * @param args
	 *
	 *            Let players tradegold into goldcoins.
	 */
	@Command(aliases={"tradegold"}, 
			desc = "Trade gold ingots into gold coins", 
			help="Trade gold ingots at a bank into gold coins, at a rate of 100 gold coins to 1 gold ingot", 
			usage="<amount>")
	public void tradegold(@Sender Player player, @Sender PseudoPlayer pPlayer, @Range(min=1) int amount) {
		final NPC npc = this.npcm.getBanker(player.getLocation());
		if (npc == null) {
			Output.simpleError(player, "You are not close enough to a bank.");
			return;
		}
		if (player.getInventory().contains(Material.GOLD_INGOT, amount)) {
			pPlayer.getWallet().add(null, amount * Variables.goldIngotValue, "tradegold");
			ItemUtils.removeItem(player.getInventory(), Material.GOLD_INGOT, amount);
			Output.positiveMessage(player,
					"You have traded " + amount + " gold ingots into " + amount * Variables.goldIngotValue + " gc.");
		} else
			Output.simpleError(player, "You dont have " + amount + " gold ingots in your inventory.");
	}

}
