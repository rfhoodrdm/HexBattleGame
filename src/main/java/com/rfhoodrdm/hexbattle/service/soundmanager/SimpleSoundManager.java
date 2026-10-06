package com.rfhoodrdm.hexbattle.service.soundmanager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.soundmanager.library.SoundTrackSequence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleSoundManager implements SoundManager, RequiresLoadedData {

	@Override
	public List<AssetRequest> getAssetRequests() {
		return List.of(); //stub value. TODO: implement.
	}

	@Override
	public void receiveLoadedAssets(List<Asset> assetList) {
		//TODO: implement.
	}

	@Override
	public void playSoundTrackSequence(SoundTrackSequence sequenceToPlay) {
		//TODO: implement.
		
	}

	@Override
	public void stopPlayingAllSoundTracks() {
		//TODO: implement.
		
	}
}
