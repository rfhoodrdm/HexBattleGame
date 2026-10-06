package com.rfhoodrdm.hexbattle.service.screennavigator;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.gui.Gui;
import com.rfhoodrdm.hexbattle.service.soundmanager.SoundManager;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrackSequence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScreenManagerService implements ScreenManager {

	private final Gui gui;
	private final SoundManager soundManager;
	
	@Override
	@EventListener
	public void processScreenTransitionRequest(ScreenTransitionRequest request) {
		gui.showGameScreen(request.screenToShow());

		SoundTrackSequence soundTrackSequence = getSoundTrackSequence(request);
		soundManager.playSoundTrackSequence(soundTrackSequence);
	}

	private SoundTrackSequence getSoundTrackSequence(ScreenTransitionRequest request) {
		return switch (request.screenToShow()) {
			case TITLE_SCREEN, CREDITS_SCREEN -> SoundTrackSequence.TITLE_AND_CREDITS;
			case SETUP_SCREEN -> SoundTrackSequence.SETUP;
			case SKIRMISH_SCREEN -> SoundTrackSequence.SKIRMISH;
		};
	}
}
