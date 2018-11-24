package com.lostshard.Lostshard.Objects.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.lostshard.Lostshard.Main.Lostshard;
import com.lostshard.Lostshard.Manager.PlayerManager;
import com.lostshard.Lostshard.Objects.PlayerListSet;

@MappedSuperclass
public class Group {

	@Transient
	public PlayerManager pm = PlayerManager.getManager();

	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@Type(type = "uuid-char")
	private PlayerListSet members = new PlayerListSet();
	
	@ElementCollection
	@LazyCollection(LazyCollectionOption.FALSE)
	@Type(type = "uuid-char")
	private PlayerListSet invited = new PlayerListSet();

	public void addInvited(UUID invite) {
		if (!this.invited.contains(invite))
			this.invited.add(invite);
	}

	public PlayerListSet getInvited() {
		return this.invited;
	}

	public PlayerListSet getMembers() {
		return this.members;
	}

	public List<Player> getOnlineMembers() {
		final List<Player> rs = new ArrayList<Player>();
		for (final UUID pUUID : this.members) {
			final Player p = Bukkit.getPlayer(pUUID);
			if (p != null)
				rs.add(p);
		}
		return rs;
	}
	
	public boolean isDead() {
		for (final UUID member : this.members)
			if (Bukkit.getPlayer(member) == null)
				this.members.remove(member);
		if (this.members.size() <= 0)
			return true;
		return false;
	}

	public boolean isInvited(UUID invite) {
		if (this.invited.contains(invite))
			return true;
		return false;
	}

	public boolean isMember(OfflinePlayer player) {
		return this.isMember(player.getUniqueId());
	}

	public boolean isMember(Player player) {
		return this.isMember(player.getUniqueId());
	}

	public boolean isMember(UUID member) {
		return this.members.contains(member);
	}

	public void sendMessage(String message) {
		for (final UUID member : this.members) {
			final Player memberPlayer = Bukkit.getPlayer(member);
			if (memberPlayer != null) {
				Lostshard.log.finest(message);
				memberPlayer.sendMessage(message);
			}
		}
	}

	public void setInvited(PlayerListSet invited) {
		this.invited = invited;
	}

	public void setMembers(PlayerListSet members) {
		this.members = members;
	}
}
