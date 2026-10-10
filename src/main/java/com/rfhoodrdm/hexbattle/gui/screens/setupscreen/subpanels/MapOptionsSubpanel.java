package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.ButtonGroup;
import javax.swing.SwingUtilities;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.gui.basecomponent.DisplayNameListCellRenderer;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.ComboBoxStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.GameTextComboBox;
import com.rfhoodrdm.hexbattle.gui.basecomponent.label.GameTextLabel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.label.LabelStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.TitledGamePanel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.GameRadioButton;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.RadioButtonStyles;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event.MapSelectionChangedEvent;
import com.rfhoodrdm.hexbattle.state.app.constants.Tileset;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

@Component
public class MapOptionsSubpanel extends TitledGamePanel {

	private static final long serialVersionUID = 1L;
	private static final int COMPONENT_GAP = 8;

	private final GameTextComboBox<Tileset> tilesetComboBox;
	private final GameRadioButton usePresetsRadioButton;
	private final GameRadioButton customSettingsRadioButton;
	private final ForcesOptionsSubpanel playerOneForcesPanel;
	private final ForcesOptionsSubpanel playerTwoForcesPanel;

	private Optional<MapTemplate> selectedMapTemplateMaybe = Optional.empty();

	public MapOptionsSubpanel() {
		super("Map Options");
		setLayout(new GridBagLayout());

		tilesetComboBox = new GameTextComboBox<>(
				Tileset.values(), ComboBoxStyles.IMPERIAL_MENU);
		tilesetComboBox.setRenderer(new DisplayNameListCellRenderer<>(
				Tileset.class, Tileset::getDisplayName));
		usePresetsRadioButton = new GameRadioButton(
				"Use Presets", true, RadioButtonStyles.IMPERIAL_SELECTION);
		customSettingsRadioButton = new GameRadioButton(
				"Custom Settings", false, RadioButtonStyles.IMPERIAL_SELECTION);
		ButtonGroup settingsType = new ButtonGroup();
		settingsType.add(usePresetsRadioButton);
		settingsType.add(customSettingsRadioButton);

		playerOneForcesPanel = new ForcesOptionsSubpanel("Player 1 Forces");
		playerTwoForcesPanel = new ForcesOptionsSubpanel("Player 2 Forces");

		addComponents();
		addListeners();
		updateControlState();
	}

	@EventListener
	public void receiveMapSelectionChangedEvent(MapSelectionChangedEvent event) {
		Runnable updateSelection = () -> applyMapSelection(event.selectedMapTemplate());
		if (SwingUtilities.isEventDispatchThread()) {
			updateSelection.run();
		} else {
			SwingUtilities.invokeLater(updateSelection);
		}
	}

	private void addComponents() {
		GameTextLabel tilesetLabel = new GameTextLabel(
				"Preferred Tileset", LabelStyles.IMPERIAL_INFORMATION);
		add(tilesetLabel, createConstraints(0, 0, 0.35, 0.0));
		add(tilesetComboBox, createConstraints(1, 0, 0.65, 0.0));
		add(usePresetsRadioButton, createConstraints(0, 1, 0.50, 0.0));
		add(customSettingsRadioButton, createConstraints(1, 1, 0.50, 0.0));
		add(playerOneForcesPanel, createConstraints(0, 2, 0.50, 1.0));
		add(playerTwoForcesPanel, createConstraints(1, 2, 0.50, 1.0));
	}

	private void addListeners() {
		usePresetsRadioButton.addActionListener(event -> useMapPresets());
		customSettingsRadioButton.addActionListener(event -> updateControlState());
	}

	private void applyMapSelection(Optional<MapTemplate> mapTemplateMaybe) {
		selectedMapTemplateMaybe = mapTemplateMaybe;
		mapTemplateMaybe.ifPresent(mapTemplate -> {
			tilesetComboBox.setSelectedItem(mapTemplate.defaultTileset());
			populateForceDefaults(mapTemplate);
		});
		updateControlState();
	}

	private void useMapPresets() {
		selectedMapTemplateMaybe.ifPresent(this::populateForceDefaults);
		updateControlState();
	}

	private void populateForceDefaults(MapTemplate mapTemplate) {
		playerOneForcesPanel.setSelections(
				mapTemplate.playerOneFaction(),
				mapTemplate.playerOneDeploymentType(),
				mapTemplate.playerOneMapSide(),
				mapTemplate.playerOneWinCondition());
		playerTwoForcesPanel.setSelections(
				mapTemplate.playerTwoFaction(),
				mapTemplate.playerTwoDeploymentType(),
				mapTemplate.playerTwoMapSide(),
				mapTemplate.playerTwoWinCondition());
	}

	private void updateControlState() {
		boolean hasSelectedMap = selectedMapTemplateMaybe.isPresent();
		boolean customSettingsEnabled = hasSelectedMap && customSettingsRadioButton.isSelected();

		tilesetComboBox.setEnabled(hasSelectedMap);
		usePresetsRadioButton.setEnabled(hasSelectedMap);
		customSettingsRadioButton.setEnabled(hasSelectedMap);
		playerOneForcesPanel.setOptionsEnabled(customSettingsEnabled);
		playerTwoForcesPanel.setOptionsEnabled(customSettingsEnabled);
	}

	private GridBagConstraints createConstraints(
			int gridX,
			int gridY,
			double weightX,
			double weightY) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.weightx = weightX;
		constraints.weighty = weightY;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(
				COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP);
		return constraints;
	}

}
