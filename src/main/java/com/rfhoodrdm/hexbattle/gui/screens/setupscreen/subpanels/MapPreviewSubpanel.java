package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.swing.SwingUtilities;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.TitledGamePanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event.MapSelectionChangedEvent;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;
import com.rfhoodrdm.hexbattle.utility.MapRendererUtil;

@Component
public class MapPreviewSubpanel extends TitledGamePanel {

	private static final long serialVersionUID = 1L;

	private final MapRendererUtil mapRendererUtil;

	private Optional<MapTemplate> selectedMapTemplateMaybe = Optional.empty();
	private BufferedImage previewImage;

	public MapPreviewSubpanel(MapRendererUtil mapRendererUtil) {
		super("Map Preview");
		this.mapRendererUtil = mapRendererUtil;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				refreshPreviewImage();
			}
		});
	}

	@EventListener
	public void receiveMapSelectionChangedEvent(MapSelectionChangedEvent event) {
		Runnable updatePreview = () -> {
			selectedMapTemplateMaybe = event.selectedMapTemplate();
			refreshPreviewImage();
		};
		if (SwingUtilities.isEventDispatchThread()) {
			updatePreview.run();
		} else {
			SwingUtilities.invokeLater(updatePreview);
		}
	}

	private void refreshPreviewImage() {
		Insets insets = getInsets();
		int previewWidth = getWidth() - insets.left - insets.right;
		int previewHeight = getHeight() - insets.top - insets.bottom;
		if (previewWidth <= 0 || previewHeight <= 0) {
			previewImage = null;
			return;
		}

		previewImage = mapRendererUtil.renderMapPreview(
				previewWidth, previewHeight, selectedMapTemplateMaybe);
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (previewImage == null) {
			return;
		}

		Insets insets = getInsets();
		graphics.drawImage(previewImage, insets.left, insets.top, this);
	}

}
