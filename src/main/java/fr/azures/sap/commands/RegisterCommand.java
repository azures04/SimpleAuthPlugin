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

public class RegisterCommand implements CommandExecutor {
	PlayerStateManager players;

	public RegisterCommand() {
		this.players = SimpleAuthPlugin.players;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			try {
			if (this.players.isPlayerRegistered(sender.getName())) {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] You're already registred with this username.");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "please login you,");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "using this command : /login <PASSWORD>");
			} else if (args.length >= 2) {
				if (args[0] != args[1]) {
					sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] Wrong, passwords are different, please retype it.");
				} else {
					Player player = (Player)sender;
					boolean canPlayNow = this.players.register(args[0], args[1]);
					if (canPlayNow) {
						player.setAllowFlight(false);
						player.setInvisible(false);
						String var10000 = String.valueOf(ChatColor.YELLOW);
						Bukkit.broadcastMessage(var10000 + player.getName() + " joined the game");
						sender.sendMessage(String.valueOf(ChatColor.GREEN) + "Succesfully registered.");
					} else {
						player.kickPlayer("Internal Server Error 500" + "\r\nPlease contact the server admin.");
					}
				}
			} else {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "Missing password.");
				sender.sendMessage(String.valueOf(ChatColor.RED) + "Usage : /register <PASSWORD> <PASSWORD>");
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
