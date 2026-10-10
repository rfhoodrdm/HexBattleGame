package com.rfhoodrdm.hexbattle.state.app.constants;

import lombok.Getter;

public enum ArmyFaction {
	GOOD("Good"),
	EVIL("Evil");

	@Getter
	private final String displayName;

	ArmyFaction(String displayName) {
		this.displayName = displayName;
	}
}
