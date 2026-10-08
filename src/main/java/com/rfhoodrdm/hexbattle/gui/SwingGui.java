package com.rfhoodrdm.hexbattle.gui;

import java.awt.CardLayout;
import java.awt.GraphicsEnvironment;
import java.util.EnumSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.config.GameConfig;
import com.rfhoodrdm.hexbattle.gui.screens.credits.CreditsScreen;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.SetupScreen;
import com.rfhoodrdm.hexbattle.gui.screens.skirmish.SkirmishScreen;
import com.rfhoodrdm.hexbattle.gui.screens.titlescreen.TitleScreen;
import com.rfhoodrdm.hexbattle.service.screennavigator.ScreenNavigator;
import com.rfhoodrdm.hexbattle.state.app.AppState;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SwingGui implements Gui {

	private final GameConfig gameConfig;
	private final AppState appState;
	private final ScreenNavigator screenNavigator;
	private final CardLayout cardLayout = new CardLayout();
	private final JPanel screenContainer = new JPanel(cardLayout);
	private final Set<ScreenName> registeredScreens = EnumSet.noneOf(ScreenName.class);

	private GameFrame gameFrame;

	public SwingGui(
			GameConfig gameConfig,
			AppState appState,
			ScreenNavigator screenNavigator,
			TitleScreen titleScreen,
			CreditsScreen creditsScreen,
			SetupScreen setupScreen,
			SkirmishScreen skirmishScreen) {
		this.gameConfig = gameConfig;
		this.appState = appState;
		this.screenNavigator = screenNavigator;
		registerScreen(ScreenName.TITLE_SCREEN, titleScreen);
		registerScreen(ScreenName.CREDITS_SCREEN, creditsScreen);
		registerScreen(ScreenName.SETUP_SCREEN, setupScreen);
		registerScreen(ScreenName.SKIRMISH_SCREEN, skirmishScreen);
	}

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
			gameFrame = new GameFrame(gameConfig, screenContainer, appState, screenNavigator);
		}
		gameFrame.setVisible(isVisible);
	}

	@Override
	public void showGameScreen(ScreenName screenToShow) {
		if (!registeredScreens.contains(screenToShow)) {
			log.error("Cannot show unregistered game screen: {}", screenToShow);
			return;
		}

		Runnable showScreen = () -> {
			cardLayout.show(screenContainer, screenToShow.name());
			appState.setCurrentScreen(screenToShow);
		};
		if (SwingUtilities.isEventDispatchThread()) {
			showScreen.run();
		} else {
			SwingUtilities.invokeLater(showScreen);
		}
	}

	private void registerScreen(ScreenName screenName, JPanel screen) {
		screenContainer.add(screen, screenName.name());
		registeredScreens.add(screenName);
	}

}
