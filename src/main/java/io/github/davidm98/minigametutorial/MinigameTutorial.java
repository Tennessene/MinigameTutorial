package io.github.davidm98.minigametutorial;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.davidm98.minigametutorial.commands.CommandArena;
import io.github.davidm98.minigametutorial.commands.CommandKit;
import io.github.davidm98.minigametutorial.listeners.DeathListener;
import io.github.davidm98.minigametutorial.listeners.JoinListener;
import io.github.davidm98.minigametutorial.listeners.LeaveListener;
import io.github.davidm98.minigametutorial.listeners.PreventionListener;

public class MinigameTutorial extends JavaPlugin {

	private static MinigameTutorial instance;

	public static MinigameTutorial getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		// Create arenas.
		Manager.setup();

		registerCommands();
		registerListeners();
	}

	@Override
	public void onDisable() {

	}

	private void registerCommands() {
		getCommand("arena").setExecutor(new CommandArena());
		getCommand("kit").setExecutor(new CommandKit());
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new DeathListener(), this);
		pm.registerEvents(new PreventionListener(), this);
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new LeaveListener(), this);
	}

}
