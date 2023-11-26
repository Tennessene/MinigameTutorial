package io.github.davidm98.minigametutorial.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.davidm98.minigametutorial.util.Helper;

public class JoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Helper.teleportToSpawn(player);
	}

}
