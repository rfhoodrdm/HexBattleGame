package com.rfhoodrdm.hexbattle.gui;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.util.UUID;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event.MapSelectionChangedEvent;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.MapPreviewSubpanel;
import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;
import com.rfhoodrdm.hexbattle.utility.MapRendererUtil;

class MapPreviewSubpanelTests {

	@Test
	void mapSelectionRendersPreviewAtAvailablePanelSize() throws Exception {
		MapRendererUtil mapRendererUtil = mock(MapRendererUtil.class);
		MapPreviewSubpanel mapPreviewSubpanel = new MapPreviewSubpanel(mapRendererUtil);
		MapTemplate mapTemplate = createMapTemplate();

		SwingUtilities.invokeAndWait(() -> {
			mapPreviewSubpanel.setSize(320, 320);
			Insets insets = mapPreviewSubpanel.getInsets();
			int previewWidth = mapPreviewSubpanel.getWidth() - insets.left - insets.right;
			int previewHeight = mapPreviewSubpanel.getHeight() - insets.top - insets.bottom;
			BufferedImage preview = new BufferedImage(
					previewWidth, previewHeight, BufferedImage.TYPE_INT_ARGB);
			when(mapRendererUtil.renderMapPreview(
					previewWidth, previewHeight, Optional.of(mapTemplate)))
					.thenReturn(preview);

			MapSelectionChangedEvent event = new MapSelectionChangedEvent(Optional.of(mapTemplate));
			mapPreviewSubpanel.receiveMapSelectionChangedEvent(event);
			mapPreviewSubpanel.dispatchEvent(new ComponentEvent(
					mapPreviewSubpanel, ComponentEvent.COMPONENT_RESIZED));
		});

		Insets insets = mapPreviewSubpanel.getInsets();
		int previewWidth = mapPreviewSubpanel.getWidth() - insets.left - insets.right;
		int previewHeight = mapPreviewSubpanel.getHeight() - insets.top - insets.bottom;
		verify(mapRendererUtil, atLeastOnce()).renderMapPreview(
				previewWidth, previewHeight, Optional.of(mapTemplate));
	}

	private MapTemplate createMapTemplate() {
		return new MapTemplate(
				"Preview Map",
				UUID.randomUUID(),
				Tileset.PLAINS,
				ArmyFaction.GOOD,
				ArmyFaction.EVIL,
				DeploymentType.ROUND_ROBIN_OPEN,
				DeploymentType.ROUND_ROBIN_OPEN,
				MapSide.NORTH,
				MapSide.SOUTH,
				WinCondition.ELIMINATE_ALL_ENEMY,
				WinCondition.ELIMINATE_ALL_ENEMY);
	}
}
