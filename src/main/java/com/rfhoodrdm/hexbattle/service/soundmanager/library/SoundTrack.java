package com.rfhoodrdm.hexbattle.service.soundmanager.library;

import javax.sound.sampled.Clip;

import lombok.Getter;
import lombok.Setter;

public enum SoundTrack {

	INTRO_1("Intro_1.aif"),
	
	SETUP_1("Setup_1.aif"),
	
	SKIRMISH_1("Skirmish_1.aif"),
	SKIRMISH_2("Skirmish_2.aif"),
	SKIRMISH_3("Skirmish_3.aif"),
	SKIRMISH_4("Skirmish_4.aif"),
	SKIRMISH_5("Skirmish_5.aif");

	@Getter
	private String trackName;
	
	@Getter @Setter
	private Clip soundTrackClip;
	
	SoundTrack(String trackName) {
		this.trackName = trackName;
	}
}
