package com.rfhoodrdm.hexbattle.service.soundmanager.library;

import java.util.List;

import static com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrack.*;

public enum SoundTrackSequence {
	TITLE_AND_CREDITS( List.of(INTRO_1) ),
	SETUP( List.of(SETUP_1) ),
	SKIRMISH( List.of(SKIRMISH_1, SKIRMISH_2, SKIRMISH_3, SKIRMISH_4, SKIRMISH_5) );
	
	private final List<SoundTrack> soundTrackList;
	
	SoundTrackSequence( List<SoundTrack> soundTrackList) {
		this.soundTrackList = soundTrackList;
	}
	
	public List<SoundTrack> getSoundTrackList() {
		return List.copyOf(soundTrackList);
	}
}
