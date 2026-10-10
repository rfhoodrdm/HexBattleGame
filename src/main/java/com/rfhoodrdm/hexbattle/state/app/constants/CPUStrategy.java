package com.rfhoodrdm.hexbattle.state.app.constants;

import lombok.Getter;

public enum CPUStrategy {
	AGGRESSIVE("Aggressive"),
	DEFENSIVE("Defensive");

	@Getter
	private final String displayName;

	CPUStrategy(String displayName) {
		this.displayName = displayName;
	}
}
