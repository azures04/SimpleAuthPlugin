package fr.azures.sap.listeners;

import fr.azures.sap.SimpleAuthPlugin;
import fr.azures.sap.auth.PlayerStateManager;
import java.sql.SQLException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {
	
	PlayerStateManager players;

	public PlayerListener() {
		this.players = SimpleAuthPlugin.players;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		try {
			this.players.addPlayerToNotLoggedTable(player.getName());
			player.setAllowFlight(true);
			player.setInvisible(true);
			event.setJoinMessage("");
			if (this.players.isPlayerRegistered(player.getName())) {
				player.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please login you,");
				player.sendMessage(String.valueOf(ChatColor.BLUE) + "using this command : /login <PASSWORD>");
			} else {
				player.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please register you,");
				player.sendMessage(String.valueOf(ChatColor.BLUE) + "using this command : /register <PASSWORD> <PASSWORD>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player)event.getEntity();
			try {
				if (this.players.isPlayerLogged(player.getName())) {
					event.setCancelled(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		try {
			if (!this.players.isPlayerLogged(player.getName())) {
				event.setCancelled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
