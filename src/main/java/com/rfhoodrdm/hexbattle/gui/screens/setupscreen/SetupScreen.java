package com.rfhoodrdm.hexbattle.gui.screens.setupscreen;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.PanelStyles;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.MapOptionsSubpanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.MapPreviewSubpanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.MapSelectSubpanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.PlayerOptionsSubPanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.StartControlSubpanel;
import com.rfhoodrdm.hexbattle.service.dataloader.RequiresLoadedData;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.Asset;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetRequest;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.AssetType;
import com.rfhoodrdm.hexbattle.service.dataloader.asset.ImageAsset;

@Component
public class SetupScreen extends JPanel implements RequiresLoadedData {

	private static final long serialVersionUID = 1L;
	private static final String BACKGROUND_ASSET_NAME = "background/SetupScreen.png";
	private static final double LEFT_SECTION_WEIGHT = 0.60;
	private static final double RIGHT_SECTION_WEIGHT = 1.0 - LEFT_SECTION_WEIGHT;
	private static final double MAP_SELECT_WEIGHT = 0.25;
	private static final double MAP_PREVIEW_WEIGHT = 1.0 - MAP_SELECT_WEIGHT;

	private BufferedImage backgroundImage;

	public SetupScreen(
			MapSelectSubpanel mapSelectSubpanel,
			MapPreviewSubpanel mapPreviewSubpanel,
			MapOptionsSubpanel mapOptionsSubpanel,
			PlayerOptionsSubPanel playerOptionsSubpanel,
			StartControlSubpanel startControlSubpanel) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createEmptyBorder(
				PanelStyles.PANEL_PADDING,
				PanelStyles.PANEL_PADDING,
				PanelStyles.PANEL_PADDING,
				PanelStyles.PANEL_PADDING));
		setOpaque(false);

		JPanel leftSection = createLeftSection(mapSelectSubpanel, mapPreviewSubpanel);
		JPanel rightSection = createRightSection(
				mapOptionsSubpanel, playerOptionsSubpanel, startControlSubpanel);
		add(leftSection, createRootConstraints(0, LEFT_SECTION_WEIGHT, PanelStyles.SECTION_GAP));
		add(rightSection, createRootConstraints(1, RIGHT_SECTION_WEIGHT, 0));
	}

	private JPanel createLeftSection(
			MapSelectSubpanel mapSelectSubpanel, MapPreviewSubpanel mapPreviewSubpanel) {
		JPanel leftSection = createTransparentGridPanel();
		GridBagConstraints mapSelectConstraints = createVerticalConstraints(0, MAP_SELECT_WEIGHT, true);
		leftSection.add(mapSelectSubpanel, mapSelectConstraints);

		SquarePanelContainer previewContainer = new SquarePanelContainer(mapPreviewSubpanel);
		GridBagConstraints previewConstraints = createVerticalConstraints(1, MAP_PREVIEW_WEIGHT, false);
		leftSection.add(previewContainer, previewConstraints);
		return leftSection;
	}

	private JPanel createRightSection(
			MapOptionsSubpanel mapOptionsSubpanel,
			PlayerOptionsSubPanel playerOptionsSubpanel,
			StartControlSubpanel startControlSubpanel) {
		JPanel rightSection = createTransparentGridPanel();
		rightSection.add(mapOptionsSubpanel, createVerticalConstraints(0, 0.5, true));
		rightSection.add(playerOptionsSubpanel, createVerticalConstraints(1, 0.5, true));

		GridBagConstraints startControlConstraints = createVerticalConstraints(2, 0.0, false);
		startControlConstraints.fill = GridBagConstraints.HORIZONTAL;
		rightSection.add(startControlSubpanel, startControlConstraints);
		return rightSection;
	}

	private JPanel createTransparentGridPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		return panel;
	}

	private GridBagConstraints createRootConstraints(int gridX, double weightX, int rightInset) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = 0;
		constraints.weightx = weightX;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, rightInset);
		return constraints;
	}

	private GridBagConstraints createVerticalConstraints(
			int gridY, double weightY, boolean includeBottomGap) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = gridY;
		constraints.weightx = 1.0;
		constraints.weighty = weightY;
		constraints.fill = GridBagConstraints.BOTH;
		int bottomInset = includeBottomGap ? PanelStyles.SECTION_GAP : 0;
		constraints.insets = new Insets(0, 0, bottomInset, 0);
		return constraints;
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
				.orElseThrow(() -> new IllegalArgumentException("The setup background image was not loaded"));
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
