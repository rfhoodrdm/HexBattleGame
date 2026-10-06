package com.rfhoodrdm.hexbattle.gui.screens.titlescreen;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.rfhoodrdm.hexbattle.gui.basecomponent.button.ButtonStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.GameTextButton;
import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.ImageAsset;
import com.rfhoodrdm.hexbattle.service.screennavigator.ScreenNavigator;
import com.rfhoodrdm.hexbattle.common.constants.ScreenName;

@org.springframework.stereotype.Component
public class TitleScreen extends JPanel implements RequiresLoadedData {

	private static final long serialVersionUID = 1L;
	private static final String SPLASH_SCREEN_ASSET_NAME = "background/SplashScreen.png";
	private static final int BUTTON_WIDTH = 220;
	private static final int BUTTON_HEIGHT = 48;
	private static final int BUTTON_GAP = 12;
	
	private static final double BUTTON_SPACER_WEIGHT_TOP = 0.70;
	private static final double BUTTON_SPACER_WEIGHT_BOTTOM = 1.0 - BUTTON_SPACER_WEIGHT_TOP;
	
	private BufferedImage backgroundImage;

	public TitleScreen(ScreenNavigator screenNavigator) {
		setLayout(new GridBagLayout());
		setOpaque(false);
		add(createTopSpacer(), createTopSpacerConstraints());
		add(createButtonPanel(screenNavigator), createButtonPanelConstraints());
		add(createBottomSpacer(), createBottomSpacerConstraints());
	}

	private Component createTopSpacer() {
		return Box.createVerticalGlue();
	}

	private GridBagConstraints createTopSpacerConstraints() {
		GridBagConstraints constraints = createFullWidthConstraints();
		constraints.gridy = 0;
		constraints.weighty = BUTTON_SPACER_WEIGHT_TOP;
		constraints.fill = GridBagConstraints.BOTH;
		return constraints;
	}

	private JPanel createButtonPanel(ScreenNavigator screenNavigator) {
		JButton newGameButton = createButton("New Game");
		JButton creditsButton = createButton("Credits");
		creditsButton.addActionListener(event -> screenNavigator.transitionToScreen(ScreenName.CREDITS_SCREEN));
		JButton exitButton = createButton("Exit");
		exitButton.addActionListener(event -> System.exit(0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(newGameButton);
		buttonPanel.add(Box.createVerticalStrut(BUTTON_GAP));
		buttonPanel.add(creditsButton);
		buttonPanel.add(Box.createVerticalStrut(BUTTON_GAP));
		buttonPanel.add(exitButton);
		return buttonPanel;
	}

	private JButton createButton(String text) {
		Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
		GameTextButton button = new GameTextButton(text, ButtonStyles.IMPERIAL_OPTION);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setPreferredSize(buttonSize);
		button.setMinimumSize(buttonSize);
		button.setMaximumSize(buttonSize);
		return button;
	}

	private GridBagConstraints createButtonPanelConstraints() {
		GridBagConstraints constraints = createFullWidthConstraints();
		constraints.gridy = 1;
		constraints.weighty = 0.0;
		constraints.anchor = GridBagConstraints.NORTH;
		return constraints;
	}

	private Component createBottomSpacer() {
		return Box.createVerticalGlue();
	}

	private GridBagConstraints createBottomSpacerConstraints() {
		GridBagConstraints constraints = createFullWidthConstraints();
		constraints.gridy = 2;
		constraints.weighty = BUTTON_SPACER_WEIGHT_BOTTOM;
		constraints.fill = GridBagConstraints.BOTH;
		return constraints;
	}

	private GridBagConstraints createFullWidthConstraints() {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 0, 0);
		return constraints;
	}

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
