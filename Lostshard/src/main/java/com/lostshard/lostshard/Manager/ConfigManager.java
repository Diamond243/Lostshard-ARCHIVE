package com.lostshard.lostshard.Manager;

import org.bukkit.configuration.file.FileConfiguration;

import com.lostshard.lostshard.Data.Variables;
import com.lostshard.lostshard.Handlers.DamageHandler;
import com.lostshard.lostshard.Main.Lostshard;

public class ConfigManager {

	static ConfigManager manager = new ConfigManager();
	
	FileConfiguration config;
	Lostshard plugin;
	
	public ConfigManager() {
	}

	public static ConfigManager getManager() {
		return manager;
	}
	
	public void setConfig(Lostshard plugin) {
		this.plugin = plugin;
		reload();
	}

	public void reload() {
		plugin.reloadConfig();
		config = plugin.getConfig();
		Variables.mysqlDriver = config.getString("MYSQL.driver");
		Variables.mysqlUrl = config.getString("MYSQL.url");
		Variables.mysqlUsername = config.getString("MYSQL.username");
		Variables.mysqlPassword = config.getString("MYSQL.password");
		
		Variables.motd = config.getString("GENERAL.motd");
		
		DamageHandler.base = config.getDouble("DAMAGE.base");
		DamageHandler.armor = config.getDouble("DAMAGE.armor");
		DamageHandler.magic = config.getDouble("DAMAGE.magic");
		DamageHandler.resistance = config.getDouble("DAMAGE.resistance");
		DamageHandler.hardhat = config.getDouble("DAMAGE.hardhat");
		DamageHandler.arrow = config.getDouble("DAMAGE.arrow");
		DamageHandler.hand = config.getDouble("DAMAGE.hand");
		DamageHandler.swords = config.getDouble("DAMAGE.swords");
		DamageHandler.diamondSword = config.getDouble("DAMAGE.diamondSword");
		DamageHandler.ironSword = config.getDouble("DAMAGE.ironSword");
		DamageHandler.goldSword = config.getDouble("DAMAGE.goldSword");
		DamageHandler.stoneSword = config.getDouble("DAMAGE.stoneSword");
		DamageHandler.woodSword = config.getDouble("DAMAGE.woodSword");
	}
}