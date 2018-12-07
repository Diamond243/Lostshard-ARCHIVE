package com.lostshard.Lostshard.Objects.Player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.lostshard.Lostshard.Manager.PlayerManager;

public class PseudoScoreboard {

	static PlayerManager pm = PlayerManager.getManager();

	protected static ScoreboardManager manager = Bukkit.getScoreboardManager();

	protected Scoreboard board = manager.getNewScoreboard();

	private UUID playerUUID;

	protected Team murder = this.board.registerNewTeam("Murder");
	protected Team criminal = this.board.registerNewTeam("Criminal");
	protected Team lawfull = this.board.registerNewTeam("Lawfull");

	protected Objective stats = this.board.registerNewObjective("stats", "dummy");

	protected Score gc = this.stats.getScore(ChatColor.YELLOW + "Gold Coins:");
	protected Score mana = this.stats.getScore(ChatColor.YELLOW + "Mana:");
	protected Score stamina = this.stats.getScore(ChatColor.YELLOW + "Stamina:");
	protected Score build = this.stats.getScore(ChatColor.YELLOW + "Build:");
	protected Score mc = this.stats.getScore(ChatColor.YELLOW + "Murder Counts:");
	protected Score rank = this.stats.getScore(ChatColor.YELLOW + "Rank:");

	@SuppressWarnings("deprecation")
	public PseudoScoreboard(UUID uuid) {

		this.playerUUID = uuid;

		final Player player = Bukkit.getPlayer(uuid);
		if (player == null)
			return;

		final PseudoPlayer pPlayer = pm.getPlayer(player);

		this.stats.setDisplayName(ChatColor.GOLD + "- Statistics -");

		this.gc.setScore(pPlayer.getWallet().intValue());

		this.mana.setScore(pPlayer.getMana());

		this.stamina.setScore(pPlayer.getStamina());

		this.build.setScore(pPlayer.getCurrentBuildId());

		this.mc.setScore(pPlayer.getMurderCounts());

		// this.rank.setScore(pPlayer.getRank());

		player.setScoreboard(this.board);
		this.stats.setDisplaySlot(DisplaySlot.SIDEBAR);

		this.murder.setPrefix(ChatColor.RED + "");
		this.criminal.setPrefix(ChatColor.GRAY + "");
		this.lawfull.setPrefix(ChatColor.BLUE + "");

		this.lawfull.setCanSeeFriendlyInvisibles(false);
		this.criminal.setCanSeeFriendlyInvisibles(false);
		this.murder.setCanSeeFriendlyInvisibles(false);

		this.lawfull.setAllowFriendlyFire(true);
		this.criminal.setAllowFriendlyFire(true);
		this.murder.setAllowFriendlyFire(true);
		for (final PseudoPlayer pP : pm.getPlayers())
			if (pP.isMurderer())
				murder.addPlayer(Bukkit.getPlayer(pP.getPlayerUUID()));
			else if (pP.isCriminal())
				criminal.addPlayer(Bukkit.getPlayer(pP.getPlayerUUID()));
			else
				lawfull.addPlayer(Bukkit.getPlayer(pP.getPlayerUUID()));
		
		updateTeams();
	}

	public UUID getPlayerUUID() {
		return this.playerUUID;
	}

	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
	}

	public void updateBuild(int build) {
		this.build.setScore(build);
	}

	public void updateMana(int mana) {
		this.mana.setScore(mana);
	}

	public void updateMoney(int money) {
		this.gc.setScore(money);
	}

	public void updateMurderCounts(int murderCounts) {
		this.mc.setScore(murderCounts);
	}

	public void updateRank(int rank) {
		this.rank.setScore(rank);
	}

	public void updateStamina(int stamina) {
		this.stamina.setScore(stamina);
	}

	@SuppressWarnings("deprecation")
	public void updateTeams() {
		final PseudoPlayer pPlayer = pm.getPlayer(this.playerUUID);
		final Player player = Bukkit.getPlayer(this.playerUUID);
		if (pPlayer.isMurderer()) {
			for (final PseudoPlayer pP : pm.getPlayers())
				if (pP.getScoreboard() != null) {
					pP.getScoreboard().murder.addPlayer(player);
				}
		} else if (pPlayer.isCriminal()) {
			for (final PseudoPlayer pP : pm.getPlayers())
				if (pP.getScoreboard() != null)
					pP.getScoreboard().criminal.addPlayer(player);
		} else
			for (final PseudoPlayer pP : pm.getPlayers())
				if (pP.getScoreboard() != null)
					pP.getScoreboard().lawfull.addPlayer(player);
	}

}
