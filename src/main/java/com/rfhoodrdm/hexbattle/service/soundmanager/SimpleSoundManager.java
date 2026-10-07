package com.rfhoodrdm.hexbattle.service.soundmanager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.SoundAsset;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrack;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrackSequence;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SimpleSoundManager implements SoundManager, RequiresLoadedData {

	private static final String MUSIC_DIRECTORY = "music/";

	private Optional<SoundTrackSequence> currentlyPlayingSequenceMaybe = Optional.empty();
	private int currentTrackIndex = -1;
	private Clip currentClip;
	private LineListener currentLineListener;
	private long playbackGeneration;

	@Override
	public List<AssetRequest> getAssetRequests() {
		return Arrays.stream(SoundTrack.values())
				.map(this::createAssetRequest)
				.toList();
	}

	@Override
	public synchronized void receiveLoadedAssets(List<Asset> assetList) {
		Objects.requireNonNull(assetList, "Asset list must not be null");

		Map<String, Clip> loadedClipsByName = new HashMap<>();
		for (Asset asset : assetList) {
			if (asset instanceof SoundAsset soundAsset) {
				loadedClipsByName.put(soundAsset.name(), soundAsset.sound());
			}
		}

		for (SoundTrack soundTrack : SoundTrack.values()) {
			String assetName = getAssetName(soundTrack);
			Clip loadedClip = loadedClipsByName.get(assetName);
			if (loadedClip == null) {
				throw new IllegalArgumentException("The sound track was not loaded: " + assetName);
			}
			soundTrack.setSoundTrackClip(loadedClip);
		}
	}

	@Override
	public synchronized void playSoundTrackSequence(SoundTrackSequence sequenceToPlay) {
		Objects.requireNonNull(sequenceToPlay, "Sound track sequence must not be null");
		if (currentlyPlayingSequenceMaybe.filter(sequenceToPlay::equals).isPresent()) {
			return;
		}

		stopCurrentClip();
		playbackGeneration++;
		currentlyPlayingSequenceMaybe = Optional.of(sequenceToPlay);
		playTrackAtIndex(0);
	}

	@Override
	public synchronized void stopPlayingAllSoundTracks() {
		stopCurrentClip();
		playbackGeneration++;
		currentlyPlayingSequenceMaybe = Optional.empty();
		currentTrackIndex = -1;
	}

	@Override
	public synchronized Optional<SoundTrackSequence> getCurrentlyPlayingSequenceMaybe() {
		return currentlyPlayingSequenceMaybe;
	}

	@PreDestroy
	public synchronized void closeSoundTracks() {
		stopPlayingAllSoundTracks();
		for (SoundTrack soundTrack : SoundTrack.values()) {
			Clip soundTrackClip = soundTrack.getSoundTrackClip();
			if (soundTrackClip != null) {
				soundTrackClip.close();
				soundTrack.setSoundTrackClip(null);
			}
		}
	}

	private AssetRequest createAssetRequest(SoundTrack soundTrack) {
		return new AssetRequest(AssetType.SOUND, getAssetName(soundTrack));
	}

	private String getAssetName(SoundTrack soundTrack) {
		return MUSIC_DIRECTORY + soundTrack.getTrackName();
	}

	private void playTrackAtIndex(int trackIndex) {
		SoundTrackSequence soundTrackSequence = currentlyPlayingSequenceMaybe.orElseThrow();
		List<SoundTrack> soundTrackList = soundTrackSequence.getSoundTrackList();
		if (soundTrackList.isEmpty()) {
			throw new IllegalStateException("Sound track sequence must contain at least one track");
		}

		currentTrackIndex = trackIndex;
		SoundTrack soundTrack = soundTrackList.get(currentTrackIndex);
		Clip soundTrackClip = soundTrack.getSoundTrackClip();
		if (soundTrackClip == null) {
			throw new IllegalStateException("Sound track has not been loaded: " + soundTrack.getTrackName());
		}

		currentClip = soundTrackClip;
		long listenerGeneration = playbackGeneration;
		currentLineListener = event -> processLineEvent(event, soundTrackClip, listenerGeneration);
		soundTrackClip.setFramePosition(0);
		soundTrackClip.addLineListener(currentLineListener);
		soundTrackClip.start();
	}

	private synchronized void processLineEvent(
			LineEvent event, Clip eventClip, long listenerGeneration) {
		if (event.getType() != LineEvent.Type.STOP
				|| listenerGeneration != playbackGeneration
				|| eventClip != currentClip
				|| currentlyPlayingSequenceMaybe.isEmpty()
				|| eventClip.getFramePosition() < eventClip.getFrameLength()) {
			return;
		}

		removeCurrentLineListener();
		List<SoundTrack> soundTrackList = currentlyPlayingSequenceMaybe.orElseThrow().getSoundTrackList();
		int nextTrackIndex = (currentTrackIndex + 1) % soundTrackList.size();
		playTrackAtIndex(nextTrackIndex);
	}

	private void stopCurrentClip() {
		if (currentClip == null) {
			return;
		}

		removeCurrentLineListener();
		currentClip.stop();
		currentClip.setFramePosition(0);
		currentClip = null;
	}

	private void removeCurrentLineListener() {
		if (currentClip != null && currentLineListener != null) {
			currentClip.removeLineListener(currentLineListener);
		}
		currentLineListener = null;
	}

}
