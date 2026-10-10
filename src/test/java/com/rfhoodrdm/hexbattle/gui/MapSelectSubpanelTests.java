package com.rfhoodrdm.hexbattle.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.List;
import java.util.UUID;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event.MapSelectionChangedEvent;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.ButtonStyles;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels.MapSelectSubpanel;
import com.rfhoodrdm.hexbattle.service.maploader.MapFileLoader;
import com.rfhoodrdm.hexbattle.state.app.SetupState;
import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;
import com.rfhoodrdm.hexbattle.state.map.MapSelectionOption;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;
import com.rfhoodrdm.hexbattle.state.map.MapTemplates;
import com.rfhoodrdm.hexbattle.state.map.MapTemplatesLoadedEvent;

class MapSelectSubpanelTests {

	private MapTemplate fieldMap;
	private MapTemplate swampMap;
	private SetupState setupState;
	private MapSelectSubpanel mapSelectSubpanel;
	private ApplicationEventPublisher eventPublisher;

	@BeforeEach
	void setUp() throws Exception {
		fieldMap = createMapTemplate("Default Field", Tileset.PLAINS);
		swampMap = createMapTemplate("Default Swamp", Tileset.SWAMP);
		MapTemplates mapTemplates = mock(MapTemplates.class);
		List<MapSelectionOption> options = List.of(
				new MapSelectionOption(fieldMap.mapName(), fieldMap.id()),
				new MapSelectionOption(swampMap.mapName(), swampMap.id()));
		when(mapTemplates.getDefaultMapIds()).thenReturn(options);
		when(mapTemplates.getMapById(fieldMap.id())).thenReturn(java.util.Optional.of(fieldMap));
		when(mapTemplates.getMapById(swampMap.id())).thenReturn(java.util.Optional.of(swampMap));

		setupState = new SetupState();
		MapFileLoader mapFileLoader = mock(MapFileLoader.class);
		eventPublisher = mock(ApplicationEventPublisher.class);
		SwingUtilities.invokeAndWait(() -> {
			mapSelectSubpanel = new MapSelectSubpanel(
					mapTemplates, setupState, mapFileLoader, eventPublisher);
			mapSelectSubpanel.receiveMapTemplatesLoadedEvent(new MapTemplatesLoadedEvent());
		});
	}

	private MapTemplate createMapTemplate(String mapName, Tileset tileset) {
		return new MapTemplate(
				mapName,
				UUID.randomUUID(),
				tileset,
				ArmyFaction.GOOD,
				ArmyFaction.EVIL,
				DeploymentType.ROUND_ROBIN_OPEN,
				DeploymentType.ROUND_ROBIN_OPEN,
				MapSide.NORTH,
				MapSide.SOUTH,
				WinCondition.ELIMINATE_ALL_ENEMY,
				WinCondition.ELIMINATE_ALL_ENEMY);
	}

	@Test
	void firstBuiltInMapIsInitiallySelected() {
		assertEquals(fieldMap, setupState.getSelectedMapTemplateMaybe().orElseThrow());
		verify(eventPublisher).publishEvent(
				new MapSelectionChangedEvent(java.util.Optional.of(fieldMap)));
		assertTrue(findComboBox().isEnabled());
		AbstractButton loadMapFileButton = findButton("Load Map File");
		assertFalse(loadMapFileButton.isEnabled());
		assertEquals(ButtonStyles.MENU_BUTTON_WIDTH, loadMapFileButton.getMinimumSize().width);
		assertEquals(ButtonStyles.MENU_BUTTON_HEIGHT, loadMapFileButton.getMinimumSize().height);
	}

	@Test
	void switchingSourcesClearsAndRestoresBuiltInSelection() throws Exception {
		SwingUtilities.invokeAndWait(() -> findButton("From File").doClick());

		assertTrue(setupState.getSelectedMapTemplateMaybe().isEmpty());
		assertFalse(findComboBox().isEnabled());
		assertTrue(findButton("Load Map File").isEnabled());

		SwingUtilities.invokeAndWait(() -> findButton("Built-in").doClick());

		assertEquals(fieldMap, setupState.getSelectedMapTemplateMaybe().orElseThrow());
	}

	@Test
	void changingComboSelectionUpdatesSetupState() throws Exception {
		SwingUtilities.invokeAndWait(() -> findComboBox().setSelectedIndex(1));

		assertEquals(swampMap, setupState.getSelectedMapTemplateMaybe().orElseThrow());
	}

	@SuppressWarnings("unchecked")
	private JComboBox<MapSelectionOption> findComboBox() {
		for (Component component : mapSelectSubpanel.getComponents()) {
			if (component instanceof JComboBox<?> comboBox) {
				return (JComboBox<MapSelectionOption>) comboBox;
			}
		}
		throw new IllegalStateException("Map selection combo box was not found");
	}

	private AbstractButton findButton(String text) {
		for (Component component : mapSelectSubpanel.getComponents()) {
			if (component instanceof AbstractButton button && text.equals(button.getText())) {
				return button;
			}
		}
		throw new IllegalStateException("Button was not found: " + text);
	}
}
