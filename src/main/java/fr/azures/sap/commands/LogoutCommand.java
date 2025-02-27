package fr.azures.sap.commands;

import fr.azures.sap.SimpleAuthPlugin;
import fr.azures.sap.auth.PlayerStateManager;
import java.sql.SQLException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogoutCommand implements CommandExecutor {
	PlayerStateManager players;

	public LogoutCommand() {
		this.players = SimpleAuthPlugin.players;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			try {
			if (!this.players.isPlayerRegistered(sender.getName())) {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] You're even not registred with this username.");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please register you first,");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "using this command : /register <PASSWORD> <PASSWORD>");
			} else if (!this.players.isPlayerLogged(sender.getName())) {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] You're even not logged with this username.");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please login you first,");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "using this command : /login <PASSWORD>");
			} else {
				this.players.addPlayerToNotLoggedTable(sender.getName());
				Player player = (Player)sender;
				player.setInvisible(true);
				player.setAllowFlight(true);
				sender.sendMessage(String.valueOf(ChatColor.GREEN) + "Succesfully logged out.");
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return true;
		} else {
			return false;
		}
	}
}
