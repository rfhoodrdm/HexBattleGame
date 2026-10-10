package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.TitledGamePanel;

@Component
public class PlayerOptionsSubPanel extends TitledGamePanel {

	private static final long serialVersionUID = 1L;
	private static final int COMPONENT_GAP = 8;

	public PlayerOptionsSubPanel() {
		super("Player Options");
		setLayout(new GridBagLayout());

		PlayerSettingsSubpanel playerOneSettings = new PlayerSettingsSubpanel("Player 1");
		PlayerSettingsSubpanel playerTwoSettings = new PlayerSettingsSubpanel("Player 2");
		add(playerOneSettings, createConstraints(0));
		add(playerTwoSettings, createConstraints(1));
	}

	private GridBagConstraints createConstraints(int gridX) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = 0;
		constraints.weightx = 0.50;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(
				COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP);
		return constraints;
	}

}
