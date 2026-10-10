package com.rfhoodrdm.hexbattle.service.maploader;

import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleMapFileLoader implements MapFileLoader {
	
	@Override
	public MapTemplate loadMapTemplateFromFile(Path pathToMapFile) {
		//Stub return value. TODO: implement loading of map file template from file, when it is defined.
		return new MapTemplate(
				"Loaded Map",
				UUID.randomUUID(),
				Tileset.PLAINS,
				ArmyFaction.GOOD,
				ArmyFaction.EVIL,
				DeploymentType.ROUND_ROBIN_OPEN,
				DeploymentType.ROUND_ROBIN_OPEN,
				MapSide.NORTH,
				MapSide.SOUTH,
				WinCondition.ELIMINATE_ALL_ENEMY,
				WinCondition.ELIMINATE_ALL_ENEMY);
	}

}
