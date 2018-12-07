package com.lostshard.Lostshard.Objects.InventoryGUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lostshard.Lostshard.Manager.SpellManager;
import com.lostshard.Lostshard.Objects.Player.PseudoPlayer;
import com.lostshard.Lostshard.Spells.Scroll;
import com.lostshard.Lostshard.Utils.Output;

public class ScrollGUI extends GUI {

	SpellManager sm = SpellManager.getManager();

	public ScrollGUI(PseudoPlayer pPlayer) {
		super("Scrolls", pPlayer);
		final Set<Scroll> scrolls = new HashSet<>(pPlayer.getScrolls());
		final GUIItem[] items = new GUIItem[scrolls.size()];
		int i = 0;
		for (Scroll s : scrolls) {
			
			final int amount = Collections.frequency(pPlayer.getScrolls(), s);
			final ItemStack item = new ItemStack(s.getReagentCost().get(0).getType(), amount);
			final ItemMeta itemMeta = item.getItemMeta();
			final List<String> lore = new ArrayList<String>();

			if (this.getPlayer().getSpellbook().contains(s))
				itemMeta.setDisplayName(ChatColor.GREEN + s.getName());
			else
				itemMeta.setDisplayName(ChatColor.RED + s.getName());

			lore.add(ChatColor.GOLD + "Amount: " + amount);

			lore.add(ChatColor.BLUE + "Mana cost: " + s.getManaCost());
			lore.add("You can add the scroll to your spellbook by clicking it");
			lore.add("You can use the scroll by shift clicking it");
			lore.add(ChatColor.GOLD + "Commands");
			lore.add("/scrolls use " + ChatColor.RED + "(scroll)");
			lore.add("/scrolls give " + ChatColor.RED + "(scroll)");
			lore.add("/scrolls spellbook " + ChatColor.RED + "(scroll)");
			itemMeta.setLore(lore);

			item.setItemMeta(itemMeta);
			items[i] = new GUIItem(item, (player, pPlayer1, item1, click, inv, slot) -> {
				if (click.equals(ClickType.LEFT)) {
					final Scroll scroll1 = s;
					if (pPlayer1.getSpellbook().contains(scroll1))
						return;
					pPlayer1.getSpellbook().add(scroll1);
					pPlayer1.getScrolls().remove(scroll1);

					final GUI gui = new ScrollGUI(pPlayer1);

					pPlayer1.getGui().setItems(gui.getItems());

					ScrollGUI.this.openInventory(player);

					Output.positiveMessage(player, "You have transferred " + scroll1.getName() + " to your spellbook.");
				} else if (click.equals(ClickType.SHIFT_LEFT)) {
					final Scroll scroll2 = s;
					if (scroll2 == null || !pPlayer1.getScrolls().contains(scroll2))
						return;
					if (ScrollGUI.this.sm.useScroll(player, scroll2)) {
						pPlayer1.getScrolls().remove(scroll2);
						ScrollGUI.this.forceClose();
					}
				}
			});
			i++;
		}
		this.setItems(items);
	}
}
