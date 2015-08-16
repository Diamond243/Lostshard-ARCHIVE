package com.lostshard.Lostshard.Objects.InventoryGUI;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIItem {
	private String displayName;
	private Material displayItem;
	private List<String> lore;
	private int data = 0;
	private GUIClick click;

	@SuppressWarnings("deprecation")
	public GUIItem(ItemStack item) {
		this.displayName = ((item.hasItemMeta()) && (item.getItemMeta().hasDisplayName())
				? item.getItemMeta().getDisplayName() : "UNKNOWN");
		this.displayItem = item.getType();
		this.lore = ((item.hasItemMeta()) && (item.getItemMeta().hasLore()) ? item.getItemMeta().getLore() : null);
		this.data = item.getData().getData();
	}

	public GUIItem(String displayName, Material displayItem) {
		this.displayName = displayName;
		this.displayItem = displayItem;
	}

	public GUIItem(String displayName, Material displayItem, int data) {
		this(displayName, displayItem);
	}

	public GUIItem(String displayName, Material displayItem, int data, String... lore) {
		this.displayName = displayName;
		this.displayItem = displayItem;
		this.data = data;
		this.lore = Arrays.asList(lore);
	}

	public GUIItem(String displayName, Material displayItem, GUIClick click) {
		this(displayName, displayItem);
		setClick(click);
	}

	public GUIItem(String displayName, Material displayItem, int data, GUIClick click) {
		this(displayName, displayItem, data);
		setClick(click);
	}

	public GUIItem(String displayName, Material displayItem, int data, GUIClick click, String... lore) {
		this(displayName, displayItem, data, lore);
		setClick(click);
	}

	public GUIItem(ItemStack item, GUIClick click) {
		this(item);
		this.click = click;
	}

	public ItemStack getItemStack() {
		ItemStack stack = new ItemStack(this.displayItem, 1, (byte) this.data);

		ItemMeta meta = stack.getItemMeta();
		if (this.displayName != null) {
			meta.setDisplayName(this.displayName);
		}
		if (this.lore != null) {
			meta.setLore(this.lore);
		}
		stack.setItemMeta(meta);

		return stack;
	}

	public GUIClick getClick() {
		return this.click;
	}

	public void setClick(GUIClick click) {
		this.click = click;
	}
}
