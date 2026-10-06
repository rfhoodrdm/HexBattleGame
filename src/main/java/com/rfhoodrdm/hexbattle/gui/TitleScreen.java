package com.rfhoodrdm.hexbattle.gui;

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
public class TitleScreen extends JPanel implements RequiresLoadedData {

	private static final long serialVersionUID = 1L;
	private static final String SPLASH_SCREEN_ASSET_NAME = "background/SplashScreen.png";

	private BufferedImage backgroundImage;

	@Override
	public List<AssetRequest> getAssetRequests() {
		AssetRequest splashScreenRequest = new AssetRequest(AssetType.IMAGE, SPLASH_SCREEN_ASSET_NAME);
		return List.of(splashScreenRequest);
	}

	@Override
	public void receiveLoadedAssets(List<Asset> assetList) {
		ImageAsset splashScreenAsset = assetList.stream()
				.filter(ImageAsset.class::isInstance)
				.map(ImageAsset.class::cast)
				.filter(asset -> SPLASH_SCREEN_ASSET_NAME.equals(asset.name()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("The splash screen image was not loaded"));
		backgroundImage = splashScreenAsset.image();
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
