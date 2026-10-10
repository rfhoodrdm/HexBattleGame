package com.rfhoodrdm.hexbattle.state.map;

import java.util.UUID;

import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;

public record MapTemplate(
		String mapName, 
		UUID id, 
		Tileset defaultTileset,
		ArmyFaction playerOneFaction,
		ArmyFaction playerTwoFaction,
		DeploymentType playerOneDeploymentType,
		DeploymentType playerTwoDeploymentType,
		MapSide playerOneMapSide,
		MapSide playerTwoMapSide,
		WinCondition playerOneWinCondition,
		WinCondition playerTwoWinCondition) {

}
