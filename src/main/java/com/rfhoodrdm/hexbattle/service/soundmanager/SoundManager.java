package com.rfhoodrdm.hexbattle.service.soundmanager;

import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrackSequence;

public interface SoundManager {

	/**
	 * Starts playing a sound track sequence if it is not already playing.
	 * Starts from the first track listed in the sequence.
	 */
	public void playSoundTrackSequence(SoundTrackSequence sequenceToPlay);
	
	/**
	 * Stops playing all sound tracks immediately.
	 */
	public void stopPlayingAllSoundTracks();
}
