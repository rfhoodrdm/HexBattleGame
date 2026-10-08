package com.rfhoodrdm.hexbattle.gui.screens.skirmish;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.ImageAsset;

@Component
public class SkirmishScreen extends JPanel implements RequiresLoadedData {

	private static final long serialVersionUID = 1L;
	private static final String BACKGROUND_ASSET_NAME = "background/SkirmishScreen.png";

	private BufferedImage backgroundImage;

	public SkirmishScreen() {
		setOpaque(false);
	}

	@Override
	public List<AssetRequest> getAssetRequests() {
		AssetRequest backgroundRequest = new AssetRequest(AssetType.IMAGE, BACKGROUND_ASSET_NAME);
		return List.of(backgroundRequest);
	}

	@Override
	public void receiveLoadedAssets(List<Asset> assetList) {
		ImageAsset backgroundAsset = assetList.stream()
				.filter(ImageAsset.class::isInstance)
				.map(ImageAsset.class::cast)
				.filter(asset -> BACKGROUND_ASSET_NAME.equals(asset.name()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("The skirmish background image was not loaded"));
		backgroundImage = backgroundAsset.image();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (backgroundImage == null) {
			return;
		}

		double widthScale = (double) getWidth() / backgroundImage.getWidth();
		double heightScale = (double) getHeight() / backgroundImage.getHeight();
		double fillScale = Math.max(widthScale, heightScale);
		int scaledWidth = (int) Math.ceil(backgroundImage.getWidth() * fillScale);
		int scaledHeight = (int) Math.ceil(backgroundImage.getHeight() * fillScale);
		int imageX = (getWidth() - scaledWidth) / 2;
		int imageY = (getHeight() - scaledHeight) / 2;
		graphics.drawImage(backgroundImage, imageX, imageY, scaledWidth, scaledHeight, this);
	}

}
