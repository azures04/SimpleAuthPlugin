package fr.azures.sap.commands;

import fr.azures.sap.SimpleAuthPlugin;
import fr.azures.sap.auth.PlayerStateManager;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {
	PlayerStateManager players;

	public LoginCommand() {
		this.players = SimpleAuthPlugin.players;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			try {
			if (!this.players.isPlayerRegistered(sender.getName())) {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] You're not registred with this username.");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please register you,");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "using this command : /register <PASSWORD> <PASSWORD>");
			} else if (args.length >= 1) {
				Player player = (Player)sender;
				boolean canPlayNow = this.players.login(player.getName(), args[0]);
				if (canPlayNow) {
					player.setAllowFlight(false);
					player.setInvisible(false);
					String var10000 = String.valueOf(ChatColor.YELLOW);
					Bukkit.broadcastMessage(var10000 + player.getName() + " joined the game");
					sender.sendMessage(String.valueOf(ChatColor.GREEN) + "Succesfully logged in.");
				} else {
					player.kickPlayer(String.valueOf(ChatColor.RED) + "Wrong password !");
				}
			} else {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "Missing arguments.");
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
