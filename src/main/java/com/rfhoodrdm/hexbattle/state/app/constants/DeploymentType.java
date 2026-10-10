package com.rfhoodrdm.hexbattle.state.app.constants;

import lombok.Getter;

public enum DeploymentType {
	ROUND_ROBIN_SECRET("Round Robin, Secret"),
	ROUND_ROBIN_OPEN("Round Robin, Open"),
	ROLLING_DEPLOYMENT("Rolling Deployment"),
	RANDOMIZED("Randomized");
	
	@Getter
	private final String displayName;
	
	DeploymentType(String displayName) {
		this.displayName = displayName;
	}

}
