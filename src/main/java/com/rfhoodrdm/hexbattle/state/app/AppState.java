package com.rfhoodrdm.hexbattle.state.app;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;

import lombok.extern.slf4j.Slf4j;

/**
 * Containts state for the application itself. E.g. is sound muted? What volume level if not.
 */
@Component
@Slf4j
public class AppState {

	private volatile ScreenName currentScreen = ScreenName.TITLE_SCREEN;

	public ScreenName getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(ScreenName currentScreen) {
		this.currentScreen = Objects.requireNonNull(currentScreen, "Current screen must not be null");
	}
}
