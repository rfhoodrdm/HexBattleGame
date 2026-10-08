package com.rfhoodrdm.hexbattle.gui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Component;
import java.awt.Container;

import javax.swing.AbstractButton;

import org.junit.jupiter.api.Test;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.StartControlSubpanel;
import com.rfhoodrdm.hexbattle.gui.screens.titlescreen.TitleScreen;
import com.rfhoodrdm.hexbattle.service.screennavigator.ScreenNavigator;

class ScreenButtonNavigationTests {

	@Test
	void titleNewGameButtonRequestsSetupScreen() {
		ScreenNavigator screenNavigator = mock(ScreenNavigator.class);
		TitleScreen titleScreen = new TitleScreen(screenNavigator);

		findButton(titleScreen, "New Game").doClick();

		verify(screenNavigator).transitionToScreen(ScreenName.SETUP_SCREEN);
	}

	@Test
	void setupStartButtonRequestsSkirmishScreen() {
		ScreenNavigator screenNavigator = mock(ScreenNavigator.class);
		StartControlSubpanel startControlSubpanel = new StartControlSubpanel(screenNavigator);

		findButton(startControlSubpanel, "Start Game").doClick();

		verify(screenNavigator).transitionToScreen(ScreenName.SKIRMISH_SCREEN);
	}

	@Test
	void setupCancelButtonRequestsTitleScreen() {
		ScreenNavigator screenNavigator = mock(ScreenNavigator.class);
		StartControlSubpanel startControlSubpanel = new StartControlSubpanel(screenNavigator);

		findButton(startControlSubpanel, "Cancel").doClick();

		verify(screenNavigator).transitionToScreen(ScreenName.TITLE_SCREEN);
	}

	private AbstractButton findButton(Container container, String buttonText) {
		for (Component component : container.getComponents()) {
			if (component instanceof AbstractButton button && buttonText.equals(button.getText())) {
				return button;
			}
			if (component instanceof Container childContainer) {
				try {
					return findButton(childContainer, buttonText);
				} catch (IllegalArgumentException exception) {
					// Continue looking through sibling containers.
				}
			}
		}
		throw new IllegalArgumentException("No button found with text: " + buttonText);
	}
}
