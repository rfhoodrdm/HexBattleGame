package com.rfhoodrdm.hexbattle.service.soundmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.SoundAsset;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrack;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrackSequence;

class SimpleSoundManagerTests {

	private static final int FRAME_LENGTH = 100;

	private final Map<SoundTrack, Clip> clips = new EnumMap<>(SoundTrack.class);
	private SimpleSoundManager soundManager;

	@BeforeEach
	void setUp() {
		soundManager = new SimpleSoundManager();
		List<Asset> soundAssets = Arrays.stream(SoundTrack.values())
				.map(this::createSoundAsset)
				.map(Asset.class::cast)
				.toList();
		soundManager.receiveLoadedAssets(soundAssets);
	}

	@AfterEach
	void tearDown() {
		soundManager.closeSoundTracks();
	}

	@Test
	void requestsEverySoundTrackFromMusicDirectory() {
		List<AssetRequest> expectedRequests = Arrays.stream(SoundTrack.values())
				.map(track -> new AssetRequest(AssetType.SOUND, "music/" + track.getTrackName()))
				.toList();

		assertEquals(expectedRequests, soundManager.getAssetRequests());
	}

	@Test
	void requestingActiveSequenceDoesNotRestartIt() {
		Clip introClip = clips.get(SoundTrack.INTRO_1);

		soundManager.playSoundTrackSequence(SoundTrackSequence.TITLE_AND_CREDITS);
		soundManager.playSoundTrackSequence(SoundTrackSequence.TITLE_AND_CREDITS);

		verify(introClip, times(1)).start();
		assertEquals(SoundTrackSequence.TITLE_AND_CREDITS,
				soundManager.getCurrentlyPlayingSequenceMaybe().orElseThrow());
	}

	@Test
	void changingSequenceStopsOldTrackAndStartsFirstNewTrack() {
		Clip introClip = clips.get(SoundTrack.INTRO_1);
		Clip setupClip = clips.get(SoundTrack.SETUP_1);
		soundManager.playSoundTrackSequence(SoundTrackSequence.TITLE_AND_CREDITS);

		soundManager.playSoundTrackSequence(SoundTrackSequence.SETUP);

		verify(introClip).stop();
		verify(introClip, atLeastOnce()).setFramePosition(0);
		verify(setupClip).setFramePosition(0);
		verify(setupClip).start();
	}

	@Test
	void naturalCompletionAdvancesToNextTrack() {
		Clip firstClip = clips.get(SoundTrack.SKIRMISH_1);
		Clip secondClip = clips.get(SoundTrack.SKIRMISH_2);
		soundManager.playSoundTrackSequence(SoundTrackSequence.SKIRMISH);
		LineListener firstTrackListener = captureListener(firstClip);
		when(firstClip.getFramePosition()).thenReturn(FRAME_LENGTH);

		firstTrackListener.update(new LineEvent(firstClip, LineEvent.Type.STOP, FRAME_LENGTH));

		verify(secondClip).setFramePosition(0);
		verify(secondClip).start();
	}

	@Test
	void finalTrackLoopsBackToFirstTrack() {
		Clip introClip = clips.get(SoundTrack.INTRO_1);
		soundManager.playSoundTrackSequence(SoundTrackSequence.TITLE_AND_CREDITS);
		LineListener introListener = captureListener(introClip);
		when(introClip.getFramePosition()).thenReturn(FRAME_LENGTH);

		introListener.update(new LineEvent(introClip, LineEvent.Type.STOP, FRAME_LENGTH));

		verify(introClip, times(2)).start();
	}

	@Test
	void manualStopClearsStateWithoutAdvancing() {
		Clip firstClip = clips.get(SoundTrack.SKIRMISH_1);
		Clip secondClip = clips.get(SoundTrack.SKIRMISH_2);
		soundManager.playSoundTrackSequence(SoundTrackSequence.SKIRMISH);
		LineListener oldListener = captureListener(firstClip);
		when(firstClip.getFramePosition()).thenReturn(FRAME_LENGTH);

		soundManager.stopPlayingAllSoundTracks();
		oldListener.update(new LineEvent(firstClip, LineEvent.Type.STOP, FRAME_LENGTH));

		assertTrue(soundManager.getCurrentlyPlayingSequenceMaybe().isEmpty());
		verify(firstClip).stop();
		verify(secondClip, never()).start();
	}

	@Test
	void shutdownClosesEveryOwnedClip() {
		soundManager.closeSoundTracks();

		clips.values().forEach(clip -> verify(clip).close());
	}

	private SoundAsset createSoundAsset(SoundTrack soundTrack) {
		Clip clip = mock(Clip.class);
		when(clip.getFrameLength()).thenReturn(FRAME_LENGTH);
		clips.put(soundTrack, clip);
		return new SoundAsset(clip, "music/" + soundTrack.getTrackName());
	}

	private LineListener captureListener(Clip clip) {
		ArgumentCaptor<LineListener> listenerCaptor = ArgumentCaptor.forClass(LineListener.class);
		verify(clip).addLineListener(listenerCaptor.capture());
		return listenerCaptor.getValue();
	}
}
