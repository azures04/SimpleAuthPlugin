package fr.azures.sap;

import fr.azures.sap.auth.PlayerStateManager;
import fr.azures.sap.commands.ChangePasswordCommand;
import fr.azures.sap.commands.LoginCommand;
import fr.azures.sap.commands.LogoutCommand;
import fr.azures.sap.commands.RegisterCommand;
import fr.azures.sap.listeners.PlayerListener;
import java.sql.SQLException;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleAuthPlugin extends JavaPlugin {
	
	public static PlayerStateManager players;
	public static String pluginFolder;
	public static String pluginName;
	
	public void onEnable() {
		try {
			this.pluginFolder = getDataFolder().getAbsolutePath();
			this.pluginName = getName();
			this.players = new PlayerStateManager();
			this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
			this.getCommand("register").setExecutor(new RegisterCommand());
			this.getCommand("login").setExecutor(new LoginCommand());
			this.getCommand("logout").setExecutor(new LogoutCommand());
			this.getCommand("changepassword").setExecutor(new ChangePasswordCommand());
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.onEnable();
	}
}
