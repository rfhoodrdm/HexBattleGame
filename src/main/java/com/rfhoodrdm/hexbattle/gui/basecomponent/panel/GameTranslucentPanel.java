package com.rfhoodrdm.hexbattle.gui.basecomponent.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GameTranslucentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final boolean paintTranslucentBackground;

	public GameTranslucentPanel() {
		this(true);
	}

	public GameTranslucentPanel(boolean paintTranslucentBackground) {
		this.paintTranslucentBackground = paintTranslucentBackground;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if (!paintTranslucentBackground) {
			return;
		}

		Graphics2D graphics2D = (Graphics2D) graphics.create();
		try {
			graphics2D.setColor(PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR);
			graphics2D.fillRect(0, 0, getWidth(), getHeight());
		} finally {
			graphics2D.dispose();
		}
	}
}
