package com.rfhoodrdm.hexbattle.state.map;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.MapAsset;
import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MapTemplates implements RequiresLoadedData {

	public static final List<String> DEFAULT_MAP_ASSET_LIST = List.of("Default Field", "Default Swamp");

	private final ApplicationEventPublisher eventPublisher;
	private final Map<UUID, MapTemplate> mapTemplatesMap = new LinkedHashMap<>();

	@Override
	public List<AssetRequest> getAssetRequests() {
		return DEFAULT_MAP_ASSET_LIST.stream()
				.map(mapName -> new AssetRequest(AssetType.MAP, mapName))
				.toList();
	}

	@Override
	public void receiveLoadedAssets(List<Asset> assetList) {
		Objects.requireNonNull(assetList, "Asset list must not be null");
		Map<String, MapAsset> loadedMapsByName = new LinkedHashMap<>();
		for (Asset asset : assetList) {
			if (asset instanceof MapAsset mapAsset) {
				loadedMapsByName.put(mapAsset.name(), mapAsset);
			}
		}

		mapTemplatesMap.clear();
		for (String defaultMapName : DEFAULT_MAP_ASSET_LIST) {
			MapAsset mapAsset = loadedMapsByName.get(defaultMapName);
			if (mapAsset == null) {
				throw new IllegalArgumentException("The default map was not loaded: " + defaultMapName);
			}

			MapTemplate mapTemplate = new MapTemplate(
					mapAsset.name(),
					mapAsset.id(),
					Tileset.PLAINS,
					ArmyFaction.GOOD,
					ArmyFaction.EVIL,
					DeploymentType.ROUND_ROBIN_OPEN,
					DeploymentType.ROUND_ROBIN_OPEN,
					MapSide.NORTH,
					MapSide.SOUTH,
					WinCondition.ELIMINATE_ALL_ENEMY,
					WinCondition.ELIMINATE_ALL_ENEMY);
			mapTemplatesMap.put(mapTemplate.id(), mapTemplate);
		}

		MapTemplatesLoadedEvent event = new MapTemplatesLoadedEvent();
		eventPublisher.publishEvent(event);
	}

	/**
	 * Provides the display name and identifier for each built-in map.
	 *
	 * @return map selection options in their configured display order
	 */
	public List<MapSelectionOption> getDefaultMapIds() {
		return mapTemplatesMap.values().stream()
				.map(mapTemplate -> new MapSelectionOption(mapTemplate.mapName(), mapTemplate.id()))
				.toList();
	}

	public Optional<MapTemplate> getMapById(UUID mapId) {
		return Optional.ofNullable(mapTemplatesMap.get(mapId));
	}

}
