package com.rfhoodrdm.hexbattle.state.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.MapAsset;

class MapTemplatesTests {

	@Test
	void requestsAndStoresBuiltInMapsInConfiguredOrder() {
		ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
		MapTemplates mapTemplates = new MapTemplates(eventPublisher);
		UUID fieldId = UUID.randomUUID();
		UUID swampId = UUID.randomUUID();

		List<AssetRequest> expectedRequests = List.of(
				new AssetRequest(AssetType.MAP, "Default Field"),
				new AssetRequest(AssetType.MAP, "Default Swamp"));
		assertEquals(expectedRequests, mapTemplates.getAssetRequests());

		List<Asset> loadedMaps = List.of(
				new MapAsset("Default Swamp", swampId),
				new MapAsset("Default Field", fieldId));
		mapTemplates.receiveLoadedAssets(loadedMaps);

		List<MapSelectionOption> expectedOptions = List.of(
				new MapSelectionOption("Default Field", fieldId),
				new MapSelectionOption("Default Swamp", swampId));
		assertEquals(expectedOptions, mapTemplates.getDefaultMapIds());
		assertEquals("Default Field", mapTemplates.getMapById(fieldId).orElseThrow().mapName());
		verify(eventPublisher).publishEvent(new MapTemplatesLoadedEvent());
	}
}
