package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import com.rfhoodrdm.hexbattle.gui.basecomponent.DisplayNameListCellRenderer;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.ComboBoxStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.GameTextComboBox;
import com.rfhoodrdm.hexbattle.gui.basecomponent.label.GameTextLabel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.label.LabelStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.TitledGamePanel;
import com.rfhoodrdm.hexbattle.state.app.constants.ArmyFaction;
import com.rfhoodrdm.hexbattle.state.app.constants.DeploymentType;
import com.rfhoodrdm.hexbattle.state.app.constants.MapSide;
import com.rfhoodrdm.hexbattle.state.app.constants.WinCondition;

public class ForcesOptionsSubpanel extends TitledGamePanel {

	private static final long serialVersionUID = 1L;
	private static final int COMPONENT_GAP = 8;

	private final GameTextComboBox<ArmyFaction> factionComboBox;
	private final GameTextComboBox<DeploymentType> deploymentTypeComboBox;
	private final GameTextComboBox<MapSide> mapSideComboBox;
	private final GameTextComboBox<WinCondition> winConditionComboBox;

	public ForcesOptionsSubpanel(String title) {
		super(title, false);
		setLayout(new GridBagLayout());

		factionComboBox = createFactionComboBox();
		deploymentTypeComboBox = createDeploymentTypeComboBox();
		mapSideComboBox = createMapSideComboBox();
		winConditionComboBox = createWinConditionComboBox();

		addOptionRow(0, "Faction", factionComboBox);
		addOptionRow(1, "Deployment Type", deploymentTypeComboBox);
		addOptionRow(2, "Map Side", mapSideComboBox);
		addOptionRow(3, "Win Condition", winConditionComboBox);
	}

	public void setSelections(
			ArmyFaction faction,
			DeploymentType deploymentType,
			MapSide mapSide,
			WinCondition winCondition) {
		factionComboBox.setSelectedItem(faction);
		deploymentTypeComboBox.setSelectedItem(deploymentType);
		mapSideComboBox.setSelectedItem(mapSide);
		winConditionComboBox.setSelectedItem(winCondition);
	}

	public void setOptionsEnabled(boolean enabled) {
		setEnabled(enabled);
		factionComboBox.setEnabled(enabled);
		deploymentTypeComboBox.setEnabled(enabled);
		mapSideComboBox.setEnabled(enabled);
		winConditionComboBox.setEnabled(enabled);
	}

	private GameTextComboBox<ArmyFaction> createFactionComboBox() {
		GameTextComboBox<ArmyFaction> comboBox = new GameTextComboBox<>(
				ArmyFaction.values(), ComboBoxStyles.IMPERIAL_MENU);
		comboBox.setRenderer(new DisplayNameListCellRenderer<>(
				ArmyFaction.class, ArmyFaction::getDisplayName));
		return comboBox;
	}

	private GameTextComboBox<DeploymentType> createDeploymentTypeComboBox() {
		GameTextComboBox<DeploymentType> comboBox = new GameTextComboBox<>(
				DeploymentType.values(), ComboBoxStyles.IMPERIAL_MENU);
		comboBox.setRenderer(new DisplayNameListCellRenderer<>(
				DeploymentType.class, DeploymentType::getDisplayName));
		return comboBox;
	}

	private GameTextComboBox<MapSide> createMapSideComboBox() {
		GameTextComboBox<MapSide> comboBox = new GameTextComboBox<>(
				MapSide.values(), ComboBoxStyles.IMPERIAL_MENU);
		comboBox.setRenderer(new DisplayNameListCellRenderer<>(
				MapSide.class, MapSide::getDisplayName));
		return comboBox;
	}

	private GameTextComboBox<WinCondition> createWinConditionComboBox() {
		GameTextComboBox<WinCondition> comboBox = new GameTextComboBox<>(
				WinCondition.values(), ComboBoxStyles.IMPERIAL_MENU);
		comboBox.setRenderer(new DisplayNameListCellRenderer<>(
				WinCondition.class, WinCondition::getDisplayName));
		return comboBox;
	}

	private void addOptionRow(int row, String labelText, GameTextComboBox<?> comboBox) {
		GameTextLabel label = new GameTextLabel(labelText, LabelStyles.IMPERIAL_INFORMATION);
		add(label, createConstraints(0, row, 0.40));
		add(comboBox, createConstraints(1, row, 0.60));
	}

	private GridBagConstraints createConstraints(int gridX, int gridY, double weightX) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.weightx = weightX;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(
				COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP);
		return constraints;
	}
}
