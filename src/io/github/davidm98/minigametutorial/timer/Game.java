package io.github.davidm98.minigametutorial.timer;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.davidm98.minigametutorial.MinigameTutorial;
import io.github.davidm98.minigametutorial.arena.Arena;
import io.github.davidm98.minigametutorial.arena.GameState;
import io.github.davidm98.minigametutorial.kits.Kit;
import io.github.davidm98.minigametutorial.util.Helper;

public class Game extends BukkitRunnable {

	private final Arena arena;
	private int time;

	public Game(Arena arena) {
		this.arena = arena;
		this.time = 0;
	}

	public void start() {
		arena.setState(GameState.STARTED);
		arena.broadcast(ChatColor.AQUA + "The game has started!");

		// Give players their kit items and invoke onStart method.
		for (Entry<UUID, Kit> entry : arena.getKits().entrySet()) {
			Player player = Bukkit.getPlayer(entry.getKey());
			Kit kit = entry.getValue();

			Helper.clearInventoryAndEffects(player);

			// Invoke onStart method.
			kit.onStart(player);

			// Teleport players to the arena spawn point.
			player.teleport(arena.getLocation());

			for (int i = 0; i < kit.getItems().length; i++) {
				player.getInventory().addItem(kit.getItems()[i]);
			}
		}
		this.runTaskTimer(MinigameTutorial.getInstance(), 0L, 20L);
	}

	@Override
	public void run() {

		// Loop through all the kits in the arena and update them, passing in
		// the time variable from the game.
		for (Entry<UUID, Kit> entry : arena.getKits().entrySet()) {
			entry.getValue().update(Bukkit.getPlayer(entry.getKey()), time);
		}

		time++;
	}

}
