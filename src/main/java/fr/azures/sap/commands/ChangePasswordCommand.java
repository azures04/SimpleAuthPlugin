package fr.azures.sap.commands;

import fr.azures.sap.SimpleAuthPlugin;
import fr.azures.sap.auth.PlayerStateManager;
import java.sql.SQLException;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePasswordCommand implements CommandExecutor {
	PlayerStateManager players;

	public ChangePasswordCommand() {
		this.players = SimpleAuthPlugin.players;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			try {
			if (!this.players.isPlayerRegistered(sender.getName())) {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] You're even not registred with this username.");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please register you first,");
				sender.sendMessage(String.valueOf(ChatColor.RED) + "using this command : /register <PASSWORD> <PASSWORD>");
			} else if (!this.players.isPlayerLogged(sender.getName())) {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] You muste be logged to change your password.");
				sender.sendMessage(String.valueOf(ChatColor.BLUE) + "[SERVER] Please login you first,");
				sender.sendMessage(String.valueOf(ChatColor.RED) + "using this command : /login <PASSWORD>");
			} else if (args.length >= 2) {
				if (args[0] != args[1]) {
					sender.sendMessage(String.valueOf(ChatColor.RED) + "[SERVER] Wrong, passwords are different, please retype it.");
				} else {
					this.players.changePassword(label, label);
					sender.sendMessage(String.valueOf(ChatColor.GREEN) + "Password succesfully changed.");
				}
			} else {
				sender.sendMessage(String.valueOf(ChatColor.RED) + "Missing password.");
				sender.sendMessage(String.valueOf(ChatColor.RED) + "Usage : /changepassword <NEW PASSWORD> <NEW PASSWORD>");
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return true;
		} else {
			System.out.println("Only players can use this command.");
			return false;
		}
	}
}
