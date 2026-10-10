package com.rfhoodrdm.hexbattle.state.app.constants;

import lombok.Getter;

public enum WinCondition {
	ELIMINATE_ALL_ENEMY("Eliminate All Enemies"),
	ELIMINATE_ENEMY_LEADER("Eliminate Enemy Leader"),
	CAPTURE_SETTLEMENT("Capture Settlement"),
	KING_OF_THE_HILL("King of the Hill");

	@Getter
	private final String displayName;

	WinCondition(String displayName) {
		this.displayName = displayName;
	}
}
