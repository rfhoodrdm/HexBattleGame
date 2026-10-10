package com.rfhoodrdm.hexbattle.state.app.constants;

import lombok.Getter;

public enum Tileset {
	PLAINS("Plains"),
	SWAMP("Swamp");

	@Getter
	private final String displayName;

	Tileset(String displayName) {
		this.displayName = displayName;
	}
}
