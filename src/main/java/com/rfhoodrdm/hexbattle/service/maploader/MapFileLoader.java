package com.rfhoodrdm.hexbattle.service.maploader;

import java.nio.file.Path;

import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

public interface MapFileLoader {

	public MapTemplate loadMapTemplateFromFile(Path pathToMapFile);
}
