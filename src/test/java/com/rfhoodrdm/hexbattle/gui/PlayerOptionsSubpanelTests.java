package com.rfhoodrdm.hexbattle.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.GameTextComboBox;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.PlayerOptionsSubPanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.PlayerSettingsSubpanel;
import com.rfhoodrdm.hexbattle.state.app.constants.CPUStrategy;

class PlayerOptionsSubpanelTests {

	private PlayerSettingsSubpanel playerOneSettings;
	private PlayerSettingsSubpanel playerTwoSettings;

	@BeforeEach
	void setUp() throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			PlayerOptionsSubPanel playerOptionsSubpanel = new PlayerOptionsSubPanel();
			List<PlayerSettingsSubpanel> settingsPanels = Arrays.stream(
					playerOptionsSubpanel.getComponents())
					.filter(PlayerSettingsSubpanel.class::isInstance)
					.map(PlayerSettingsSubpanel.class::cast)
					.toList();
			playerOneSettings = settingsPanels.get(0);
			playerTwoSettings = settingsPanels.get(1);
		});
	}

	@Test
	void bothPlayersDefaultToHumanWithAggressiveStrategyDisabled() {
		assertTrue(findButton(playerOneSettings, "Human").isSelected());
		assertTrue(findButton(playerTwoSettings, "Human").isSelected());
		assertFalse(findButton(playerOneSettings, "Computer").isSelected());
		assertFalse(findButton(playerTwoSettings, "Computer").isSelected());

		GameTextComboBox<?> playerOneStrategy = findComboBox(playerOneSettings);
		GameTextComboBox<?> playerTwoStrategy = findComboBox(playerTwoSettings);
		assertEquals(CPUStrategy.AGGRESSIVE, playerOneStrategy.getSelectedItem());
		assertEquals(CPUStrategy.AGGRESSIVE, playerTwoStrategy.getSelectedItem());
		assertFalse(playerOneStrategy.isEnabled());
		assertFalse(playerTwoStrategy.isEnabled());
	}

	@Test
	void selectingComputerEnablesOnlyThatPlayersStrategy() throws Exception {
		SwingUtilities.invokeAndWait(() -> findButton(playerOneSettings, "Computer").doClick());

		assertTrue(findButton(playerOneSettings, "Computer").isSelected());
		assertFalse(findButton(playerOneSettings, "Human").isSelected());
		assertTrue(findComboBox(playerOneSettings).isEnabled());
		assertTrue(findButton(playerTwoSettings, "Human").isSelected());
		assertFalse(findComboBox(playerTwoSettings).isEnabled());
	}

	@Test
	void switchingBackToHumanDisablesStrategy() throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			findButton(playerOneSettings, "Computer").doClick();
			findButton(playerOneSettings, "Human").doClick();
		});

		assertTrue(findButton(playerOneSettings, "Human").isSelected());
		assertFalse(findComboBox(playerOneSettings).isEnabled());
	}

	private AbstractButton findButton(PlayerSettingsSubpanel panel, String text) {
		for (Component component : panel.getComponents()) {
			if (component instanceof AbstractButton button && text.equals(button.getText())) {
				return button;
			}
		}
		throw new IllegalStateException("Button was not found: " + text);
	}

	private GameTextComboBox<?> findComboBox(PlayerSettingsSubpanel panel) {
		for (Component component : panel.getComponents()) {
			if (component instanceof GameTextComboBox<?> comboBox) {
				return comboBox;
			}
		}
		throw new IllegalStateException("CPU strategy combo box was not found");
	}
}
