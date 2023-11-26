package io.github.davidm98.minigametutorial.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.davidm98.minigametutorial.Manager;
import io.github.davidm98.minigametutorial.kits.Kit;
import io.github.davidm98.minigametutorial.timer.Countdown;
import io.github.davidm98.minigametutorial.timer.Game;

public class Arena {

	private final int id;
	private ArrayList<UUID> players;
	private HashMap<UUID, Kit> kits;
	private GameState state;
	private final Location location;

	private final Countdown countdown;
	private final Game game;

	private final int requiredPlayers;
	private final String prefix;

	public Arena(int id, Location location) {
		this.id = id;
		this.players = new ArrayList<UUID>();
		this.kits = new HashMap<UUID, Kit>();
		// Initialise the arena's game state as waiting - what it will be when
		// the arena is created.
		this.state = GameState.WAITING;
		this.location = location;

		this.countdown = new Countdown(this);
		this.game = new Game(this);

		// The players required for the countdown to start.
		this.requiredPlayers = 2;
		this.prefix = ChatColor.GRAY + "[" + ChatColor.GREEN + "Arena " + id + ChatColor.GRAY + "] " + ChatColor.RESET;

		// Add the arena to the arena list in the manager class.
		Manager.getInstance().addArena(this);
	}

	public void reset() {
		// Clear all variables that should not continue their values into the
		// next round played in this arena.
		this.players.clear();
		this.kits.clear();
		this.state = GameState.WAITING;
	}

	public void broadcast(String message) {
		for (int i = 0; i < players.size(); i++) {
			Bukkit.getPlayer(players.get(i)).sendMessage(prefix + message);
		}
	}

	public void addPlayer(UUID uuid) {
		players.add(uuid);

		// Check whether to start the countdown. Make sure that the countdown
		// isn't already running.
		if (!countdown.isRunning() && players.size() >= requiredPlayers) {
			countdown.start(60);
		}
	}

	public void removePlayer(UUID uuid) {
		players.remove(uuid);

		// Remove the kit data for the UUID from the arena.
		final Kit kit = getKit(uuid);

		if (kit != null) {
			kit.remove();
		}

		kits.remove(kit);

		// Win detection. Check if the game has actually started first, though,
		// because if we don't do this, errors could occur.
		if (state == GameState.STARTED && players.size() == 1) {
			game.cancel();

			// Get the last player.
			Player winner = Bukkit.getPlayer(players.get(0));

			Bukkit.broadcastMessage(
					ChatColor.GREEN + "" + ChatColor.BOLD + winner.getName() + " won arena " + id + "!");

			removePlayer(winner.getUniqueId());

			// Reset the arena.
			reset();
		}
	}

	public Kit getKit(UUID uuid) {
		return kits.get(uuid);
	}

	public void setKit(UUID uuid, Kit kit) {
		kits.put(uuid, kit);
	}

	public boolean contains(UUID uuid) {
		return players.contains(uuid);
	}

	// Getters and setters for private variables.
	public ArrayList<UUID> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<UUID> players) {
		this.players = players;
	}

	public HashMap<UUID, Kit> getKits() {
		return kits;
	}

	public void setKits(HashMap<UUID, Kit> kits) {
		this.kits = kits;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Location getLocation() {
		return location;
	}

	public int getId() {
		return id;
	}

	public Countdown getCountdown() {
		return countdown;
	}

	public Game getGame() {
		return game;
	}

	public int getRequiredPlayers() {
		return requiredPlayers;
	}

	public String getPrefix() {
		return prefix;
	}

}
