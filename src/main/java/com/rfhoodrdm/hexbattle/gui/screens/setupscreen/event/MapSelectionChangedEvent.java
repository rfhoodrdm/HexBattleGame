package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event;

import java.util.Optional;
import java.util.Objects;

import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

public record MapSelectionChangedEvent(Optional<MapTemplate> selectedMapTemplate) {

	public MapSelectionChangedEvent {
		Objects.requireNonNull(selectedMapTemplate, "Selected map template optional must not be null");
	}
}
