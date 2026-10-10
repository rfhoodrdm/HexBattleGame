package com.rfhoodrdm.hexbattle.state.app.constants;

import lombok.Getter;

public enum MapSide {
	NORTH("North"),
	SOUTH("South"),
	WEST("West"),
	EAST("East");

	@Getter
	private final String displayName;

	MapSide(String displayName) {
		this.displayName = displayName;
	}
}
