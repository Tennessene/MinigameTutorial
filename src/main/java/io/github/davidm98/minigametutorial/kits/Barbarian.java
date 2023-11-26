package io.github.davidm98.minigametutorial.kits;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import io.github.davidm98.minigametutorial.util.Helper;

public class Barbarian extends Kit {

	public Barbarian(UUID uuid) {
		super(uuid, KitType.BARBARIAN, Helper.createItem(ChatColor.GREEN, "Barbarian's Sword", Material.STONE_SWORD));
	}

	@Override
	public void onStart(Player player) {

	}

	@Override
	public void update(Player player, int time) {

	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		final Player player = (Player) event.getEntity();

		// Check if it is not the player that is triggering this listener. If
		// so, return out of the method.
		if (uuid != player.getUniqueId()) {
			return;
		}

		// Reduce damage by 1 health (1/2 a heart).
		event.setDamage(event.getDamage() - 1);
	}

}
