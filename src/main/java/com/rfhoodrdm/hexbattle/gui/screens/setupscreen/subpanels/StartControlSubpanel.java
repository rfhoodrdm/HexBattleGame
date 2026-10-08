package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.FlowLayout;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.ButtonStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.GameTextButton;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.GameTranslucentPanel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.PanelStyles;
import com.rfhoodrdm.hexbattle.service.screennavigator.ScreenNavigator;

@Component
public class StartControlSubpanel extends GameTranslucentPanel {

	private static final long serialVersionUID = 1L;

	public StartControlSubpanel(ScreenNavigator screenNavigator) {
		FlowLayout layout = new FlowLayout(
				FlowLayout.CENTER,
				PanelStyles.SECTION_GAP,
				PanelStyles.PANEL_PADDING);
		setLayout(layout);

		GameTextButton startGameButton = new GameTextButton("Start Game", ButtonStyles.IMPERIAL_OPTION);
		startGameButton.addActionListener(
				event -> screenNavigator.transitionToScreen(ScreenName.SKIRMISH_SCREEN));

		GameTextButton cancelButton = new GameTextButton("Cancel", ButtonStyles.IMPERIAL_OPTION);
		cancelButton.addActionListener(
				event -> screenNavigator.transitionToScreen(ScreenName.TITLE_SCREEN));

		add(startGameButton);
		add(cancelButton);
	}

}
