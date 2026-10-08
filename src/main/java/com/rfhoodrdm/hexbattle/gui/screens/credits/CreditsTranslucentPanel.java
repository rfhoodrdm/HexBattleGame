package com.rfhoodrdm.hexbattle.gui.screens.credits;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.config.CreditsConfig;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.PanelStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.textarea.GameTextArea;
import com.rfhoodrdm.hexbattle.gui.basecomponent.textarea.TextAreaStyles;

@Component
public class CreditsTranslucentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR;
	private static final int TEXT_PADDING = 24;

	public CreditsTranslucentPanel(CreditsConfig creditsConfig) {
		setLayout(new BorderLayout());
		setOpaque(false);

		GameTextArea creditsTextArea = new GameTextArea(
				creditsConfig.getCreditsText(),
				TextAreaStyles.IMPERIAL_DECREE);
		creditsTextArea.setBorder(BorderFactory.createEmptyBorder(
				TEXT_PADDING, TEXT_PADDING, TEXT_PADDING, TEXT_PADDING));
		add(creditsTextArea, BorderLayout.CENTER);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		Graphics2D graphics2D = (Graphics2D) graphics.create();
		try {
			graphics2D.setColor(BACKGROUND_COLOR);
			graphics2D.fillRect(0, 0, getWidth(), getHeight());
		} finally {
			graphics2D.dispose();
		}
	}

}
