package com.rfhoodrdm.hexbattle.service.dataloader;

import java.util.List;

import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;


/**
 * Loads assets from the application's runtime classpath.
 */
public interface DataLoader {

	/**
	 * Loads every requested asset.
	 *
	 * @param assetRequests requests to load
	 * @return the loaded assets
	 * @throws DataLoaderException if any request cannot be loaded
	 */
	List<Asset> loadAssets(List<AssetRequest> assetRequests);
}
