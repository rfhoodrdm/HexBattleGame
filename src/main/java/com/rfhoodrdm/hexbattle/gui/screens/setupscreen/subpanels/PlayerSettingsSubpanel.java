package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;

import com.rfhoodrdm.hexbattle.gui.basecomponent.DisplayNameListCellRenderer;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.ComboBoxStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.GameTextComboBox;
import com.rfhoodrdm.hexbattle.gui.basecomponent.label.GameTextLabel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.label.LabelStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.TitledGamePanel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.GameRadioButton;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.RadioButtonStyles;
import com.rfhoodrdm.hexbattle.state.app.constants.CPUStrategy;

public class PlayerSettingsSubpanel extends TitledGamePanel {

	private static final long serialVersionUID = 1L;
	private static final int COMPONENT_GAP = 8;

	private final GameRadioButton humanRadioButton;
	private final GameRadioButton computerRadioButton;
	private final GameTextComboBox<CPUStrategy> cpuStrategyComboBox;

	public PlayerSettingsSubpanel(String title) {
		super(title, false);
		setLayout(new GridBagLayout());

		humanRadioButton = new GameRadioButton(
				"Human", true, RadioButtonStyles.IMPERIAL_SELECTION);
		computerRadioButton = new GameRadioButton(
				"Computer", false, RadioButtonStyles.IMPERIAL_SELECTION);
		ButtonGroup playerController = new ButtonGroup();
		playerController.add(humanRadioButton);
		playerController.add(computerRadioButton);

		cpuStrategyComboBox = new GameTextComboBox<>(
				CPUStrategy.values(), ComboBoxStyles.IMPERIAL_MENU);
		cpuStrategyComboBox.setRenderer(new DisplayNameListCellRenderer<>(
				CPUStrategy.class, CPUStrategy::getDisplayName));
		cpuStrategyComboBox.setSelectedItem(CPUStrategy.AGGRESSIVE);

		addComponents();
		addListeners();
		updateCPUControlState();
	}

	private void addComponents() {
		add(humanRadioButton, createConstraints(0, 0, 0.50));
		add(computerRadioButton, createConstraints(1, 0, 0.50));

		GameTextLabel cpuStrategyLabel = new GameTextLabel(
				"CPU Strategy", LabelStyles.IMPERIAL_INFORMATION);
		add(cpuStrategyLabel, createConstraints(0, 1, 0.40));
		add(cpuStrategyComboBox, createConstraints(1, 1, 0.60));
	}

	private void addListeners() {
		humanRadioButton.addActionListener(event -> updateCPUControlState());
		computerRadioButton.addActionListener(event -> updateCPUControlState());
	}

	private void updateCPUControlState() {
		cpuStrategyComboBox.setEnabled(computerRadioButton.isSelected());
	}

	private GridBagConstraints createConstraints(int gridX, int gridY, double weightX) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.weightx = weightX;
		constraints.weighty = 0.50;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(
				COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP);
		return constraints;
	}
}
