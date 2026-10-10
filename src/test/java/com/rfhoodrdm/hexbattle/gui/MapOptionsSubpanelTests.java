package com.rfhoodrdm.hexbattle.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event.MapSelectionChangedEvent;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.ForcesOptionsSubpanel;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.MapOptionsSubpanel;
import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

class MapOptionsSubpanelTests {

	private MapOptionsSubpanel mapOptionsSubpanel;
	private MapTemplate mapTemplate;

	@BeforeEach
	void setUp() throws Exception {
		mapTemplate = new MapTemplate(
				"Test Map",
				UUID.randomUUID(),
				Tileset.SWAMP,
				ArmyFaction.EVIL,
				ArmyFaction.GOOD,
				DeploymentType.ROLLING_DEPLOYMENT,
				DeploymentType.RANDOMIZED,
				MapSide.WEST,
				MapSide.EAST,
				WinCondition.CAPTURE_SETTLEMENT,
				WinCondition.KING_OF_THE_HILL);
		SwingUtilities.invokeAndWait(() -> mapOptionsSubpanel = new MapOptionsSubpanel());
	}

	@Test
	void controlsRemainDisabledUntilMapIsSelected() {
		assertFalse(findButton("Use Presets").isEnabled());
		assertFalse(findButton("Custom Settings").isEnabled());
		for (JComboBox<?> comboBox : findComboBoxes(mapOptionsSubpanel)) {
			assertFalse(comboBox.isEnabled());
		}
	}

	@Test
	void mapSelectionPopulatesDefaultsInPresetMode() throws Exception {
		publishSelection(Optional.of(mapTemplate));

		List<JComboBox<?>> mapOptionComboBoxes = findDirectComboBoxes(mapOptionsSubpanel);
		assertEquals(Tileset.SWAMP, mapOptionComboBoxes.getFirst().getSelectedItem());
		assertTrue(mapOptionComboBoxes.getFirst().isEnabled());
		assertTrue(findButton("Use Presets").isSelected());
		for (JComboBox<?> comboBox : findForceComboBoxes()) {
			assertFalse(comboBox.isEnabled());
		}
		assertEquals(ArmyFaction.EVIL, findForceComboBoxes().get(0).getSelectedItem());
		assertEquals(ArmyFaction.GOOD, findForceComboBoxes().get(4).getSelectedItem());
	}

	@Test
	void customSettingsEnableForceSelectors() throws Exception {
		publishSelection(Optional.of(mapTemplate));
		SwingUtilities.invokeAndWait(() -> findButton("Custom Settings").doClick());

		assertTrue(findButton("Custom Settings").isSelected());
		for (JComboBox<?> comboBox : findForceComboBoxes()) {
			assertTrue(comboBox.isEnabled());
		}
	}

	@Test
	void presetsRestoreDefaultsAndDisableForceSelectors() throws Exception {
		publishSelection(Optional.of(mapTemplate));
		List<JComboBox<?>> forceComboBoxes = findForceComboBoxes();
		SwingUtilities.invokeAndWait(() -> {
			forceComboBoxes.get(0).setSelectedItem(ArmyFaction.GOOD);
			findButton("Use Presets").doClick();
		});

		assertEquals(ArmyFaction.EVIL, forceComboBoxes.get(0).getSelectedItem());
		for (JComboBox<?> comboBox : forceComboBoxes) {
			assertFalse(comboBox.isEnabled());
		}
	}

	@Test
	void clearingSelectionDisablesAllControls() throws Exception {
		publishSelection(Optional.of(mapTemplate));
		publishSelection(Optional.empty());

		assertFalse(findButton("Use Presets").isEnabled());
		assertFalse(findButton("Custom Settings").isEnabled());
		for (JComboBox<?> comboBox : findComboBoxes(mapOptionsSubpanel)) {
			assertFalse(comboBox.isEnabled());
		}
	}

	private void publishSelection(Optional<MapTemplate> selection) throws Exception {
		MapSelectionChangedEvent event = new MapSelectionChangedEvent(selection);
		SwingUtilities.invokeAndWait(() -> mapOptionsSubpanel.receiveMapSelectionChangedEvent(event));
	}

	private AbstractButton findButton(String text) {
		for (Component component : findComponents(mapOptionsSubpanel)) {
			if (component instanceof AbstractButton button && text.equals(button.getText())) {
				return button;
			}
		}
		throw new IllegalStateException("Button was not found: " + text);
	}

	private List<JComboBox<?>> findForceComboBoxes() {
		List<JComboBox<?>> comboBoxes = new ArrayList<>();
		for (Component component : mapOptionsSubpanel.getComponents()) {
			if (component instanceof ForcesOptionsSubpanel forcesPanel) {
				comboBoxes.addAll(findComboBoxes(forcesPanel));
			}
		}
		return comboBoxes;
	}

	private List<JComboBox<?>> findDirectComboBoxes(Container container) {
		List<JComboBox<?>> comboBoxes = new ArrayList<>();
		for (Component component : container.getComponents()) {
			if (component instanceof JComboBox<?> comboBox) {
				comboBoxes.add(comboBox);
			}
		}
		return comboBoxes;
	}

	private List<JComboBox<?>> findComboBoxes(Container container) {
		List<JComboBox<?>> comboBoxes = new ArrayList<>();
		for (Component component : findComponents(container)) {
			if (component instanceof JComboBox<?> comboBox) {
				comboBoxes.add(comboBox);
			}
		}
		return comboBoxes;
	}

	private List<Component> findComponents(Container container) {
		List<Component> components = new ArrayList<>();
		for (Component component : container.getComponents()) {
			components.add(component);
			if (component instanceof Container childContainer) {
				components.addAll(findComponents(childContainer));
			}
		}
		return components;
	}
}
