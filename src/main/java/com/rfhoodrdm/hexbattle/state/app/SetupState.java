package com.rfhoodrdm.hexbattle.state.app;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SetupState {

	@Getter
	private Optional<MapTemplate> selectedMapTemplateMaybe = Optional.empty();
	
	public void setSelectedMapTemplate(MapTemplate mapTemplate) {
		selectedMapTemplateMaybe = Optional.ofNullable(mapTemplate);
	}
	
	public void clearSelectedMap() {
		selectedMapTemplateMaybe = Optional.empty();
	}
}
