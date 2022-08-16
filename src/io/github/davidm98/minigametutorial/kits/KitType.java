package io.github.davidm98.minigametutorial.kits;

public enum KitType {

	BARBARIAN("Barbarian"), ARCHER("Archer");

	private final String name;

	private KitType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static boolean isKit(String name) {
		return getKit(name) != null; // It is a kit if the name isn't equal to
										// null (when the loop couldn't find a
										// kit the value returned by the getKit
										// method is null).
	}

	public static KitType getKit(String name) {
		// Loop through all the KitTypes and check if their name matches the
		// name String above. If so, return the KitType.

		for (KitType type : values()) {
			if (type.toString().equalsIgnoreCase(name)) {
				return type;
			}
		}

		return null;
	}

}
