package com.lostshard.lostshard.DamageBalance.Armor;

import org.bukkit.Material;

public enum ArmorChestplate {

	DIAMON(Material.DIAMOND_HELMET, 0),
	IRON(Material.DIAMOND_HELMET, 0),
	CHAIN(Material.DIAMOND_HELMET, 0),
	LEATHER(Material.DIAMOND_HELMET, 0),
	GOLD(Material.DIAMOND_HELMET, 0),
	;
	
	private final Material type;
	private final double modifier;
	
	private ArmorChestplate(Material type, double modifier) {
		this.type = type;
		this.modifier = modifier;
	}

	/**
	 * @return the type
	 */
	public Material getType() {
		return type;
	}

	/**
	 * @return the modifier
	 */
	public double getModifier() {
		return modifier;
	}
}
