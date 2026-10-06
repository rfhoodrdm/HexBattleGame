package com.rfhoodrdm.hexbattle.service.screennavigator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.gui.Gui;
import com.rfhoodrdm.hexbattle.service.soundmanager.SoundManager;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrackSequence;

class ScreenManagerServiceTests {

	@ParameterizedTest
	@EnumSource(ScreenName.class)
	void transitionShowsScreenAndSelectsItsSoundTrack(ScreenName screenName) {
		RecordingGui gui = new RecordingGui();
		RecordingSoundManager soundManager = new RecordingSoundManager();
		ScreenManager screenManager = new ScreenManagerService(gui, soundManager);

		ScreenTransitionRequest request = new ScreenTransitionRequest(screenName);
		screenManager.processScreenTransitionRequest(request);

		assertEquals(screenName, gui.shownScreen);
		assertEquals(expectedSoundTrackSequence(screenName), soundManager.playedSequence);
	}

	private SoundTrackSequence expectedSoundTrackSequence(ScreenName screenName) {
		return switch (screenName) {
			case TITLE_SCREEN, CREDITS_SCREEN -> SoundTrackSequence.TITLE_AND_CREDITS;
			case SETUP_SCREEN -> SoundTrackSequence.SETUP;
			case SKIRMISH_SCREEN -> SoundTrackSequence.SKIRMISH;
		};
	}

	private static class RecordingGui implements Gui {

		private ScreenName shownScreen;

		@Override
		public void setVisible(boolean isVisible) {
		}

		@Override
		public void showGameScreen(ScreenName screenToShow) {
			shownScreen = screenToShow;
		}
	}

	private static class RecordingSoundManager implements SoundManager {

		private SoundTrackSequence playedSequence;

		@Override
		public void playSoundTrackSequence(SoundTrackSequence sequenceToPlay) {
			playedSequence = sequenceToPlay;
		}

		@Override
		public void stopPlayingAllSoundTracks() {
		}
	}
}
