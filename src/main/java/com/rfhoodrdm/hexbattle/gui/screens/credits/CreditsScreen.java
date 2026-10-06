package com.rfhoodrdm.hexbattle.gui.screens.credits;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.ButtonStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.GameTextButton;
import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.ImageAsset;
import com.rfhoodrdm.hexbattle.service.screennavigator.ScreenNavigator;

@Component
public class CreditsScreen extends JPanel implements RequiresLoadedData {

	private static final long serialVersionUID = 1L;
	private static final String BACKGROUND_ASSET_NAME = "background/CreditsScreen.png";
	private static final int SCREEN_PADDING = 40;
	private static final int COMPONENT_GAP = 16;
	private static final int BOTTOM_PADDING = 12;
	
	private static final int BUTTON_WIDTH = 220;
	private static final int BUTTON_HEIGHT = 48;

	private BufferedImage backgroundImage;

	public CreditsScreen(CreditsTranslucentPanel creditsPanel, ScreenNavigator screenNavigator) {
		setLayout(new BorderLayout(0, COMPONENT_GAP));
		setBorder(BorderFactory.createEmptyBorder(
				SCREEN_PADDING, SCREEN_PADDING, BOTTOM_PADDING, SCREEN_PADDING));
		setOpaque(false);
		add(creditsPanel, BorderLayout.CENTER);
		add(createBackButtonPanel(screenNavigator), BorderLayout.SOUTH);
	}

	private JPanel createBackButtonPanel(ScreenNavigator screenNavigator) {
		Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
		GameTextButton backButton = new GameTextButton("Back", ButtonStyles.IMPERIAL_OPTION);

		backButton.setPreferredSize(buttonSize);
		backButton.setMinimumSize(buttonSize);
		backButton.setMaximumSize(buttonSize);
		backButton.addActionListener(event -> screenNavigator.transitionToScreen(ScreenName.TITLE_SCREEN));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.add(backButton);
		return buttonPanel;
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
				.orElseThrow(() -> new IllegalArgumentException("The credits background image was not loaded"));
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
