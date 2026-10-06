package com.rfhoodrdm.hexbattle.gui;

import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.config.GameConfig;
import com.rfhoodrdm.hexbattle.gui.screens.titlescreen.TitleScreen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SwingGui implements Gui {

	private final GameConfig gameConfig;
	private final TitleScreen titleScreen;

	private GameFrame gameFrame;

	@Override
	public void setVisible(boolean isVisible) {
		if (GraphicsEnvironment.isHeadless()) {
			log.warn("The game window cannot be shown in a headless environment");
			return;
		}

		SwingUtilities.invokeLater(() -> showGameFrame(isVisible));
	}

	private void showGameFrame(boolean isVisible) {
		if (gameFrame == null) {
			gameFrame = new GameFrame(gameConfig, titleScreen);
		}
		gameFrame.setVisible(isVisible);
	}

}
