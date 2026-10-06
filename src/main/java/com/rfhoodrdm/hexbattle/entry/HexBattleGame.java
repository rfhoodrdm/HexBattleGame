package com.rfhoodrdm.hexbattle.entry;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.gui.Gui;
import com.rfhoodrdm.hexbattle.service.dataloader.DataLoader;
import com.rfhoodrdm.hexbattle.service.dataloader.DataLoaderException;
import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class HexBattleGame  implements ApplicationRunner{

	private final List<RequiresLoadedData> componentsThatRequireData;
	private final DataLoader dataLoader;
	private final Gui gui;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		try {
			for (RequiresLoadedData component : componentsThatRequireData) {
				component.receiveLoadedAssets(dataLoader.loadAssets(component.getAssetRequests()));
			}
		} catch (DataLoaderException exception) {
			log.error("The application could not load a required asset", exception);
			System.exit(1);
		}
		
		gui.setVisible(true);
		
		//start the various threads.
	}
}
