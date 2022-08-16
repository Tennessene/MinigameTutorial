package io.github.davidm98.minigametutorial.kits;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import io.github.davidm98.minigametutorial.MinigameTutorial;

public abstract class Kit implements Listener {

	public static Kit getKit(UUID uuid, KitType type) {
		switch (type) {
		case ARCHER:
			return new Archer(uuid);
		case BARBARIAN:
			return new Barbarian(uuid);
		default:
			return null;
		}
	}

	// Abstract class as we're not actually creating an instance of the kit
	// class - it just acts as a 'template' for classes that extend it.

	// These variables should be protected as we want to be able to access these
	// variables in the subclasses.
	protected final UUID uuid;
	protected final KitType type;
	protected final ItemStack[] items;

	public Kit(UUID uuid, KitType type, ItemStack... items) {
		this.uuid = uuid;
		this.type = type;
		this.items = items;

		Bukkit.getPluginManager().registerEvents(this, MinigameTutorial.getInstance());
	}

	// Called when a player is no longer using the kit. This allows us to remove
	// the listeners in this class.
	public void remove() {
		HandlerList.unregisterAll(this);
	}

	// If the kit requires something to be run on the start of the game.
	public abstract void onStart(Player player);

	// If the kit requires something to be done at a specific time of the game.
	public abstract void update(Player player, int time);

	public UUID getUUID() {
		return uuid;
	}

	public KitType getType() {
		return type;
	}

	public ItemStack[] getItems() {
		return items;
	}

}
