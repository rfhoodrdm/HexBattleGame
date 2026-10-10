package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.Dimension;
import java.awt.FlowLayout;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.ButtonStyle;
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

		GameTextButton startGameButton = createMenuSizedButton(
				"Start Game", ButtonStyles.IMPERIAL_ACTIVATION);
		startGameButton.addActionListener(
				event -> screenNavigator.transitionToScreen(ScreenName.SKIRMISH_SCREEN));

		GameTextButton cancelButton = createMenuSizedButton(
				"Cancel", ButtonStyles.IMPERIAL_CANCELLATION);
		cancelButton.addActionListener(
				event -> screenNavigator.transitionToScreen(ScreenName.TITLE_SCREEN));

		add(startGameButton);
		add(cancelButton);
	}

	private GameTextButton createMenuSizedButton(String text, ButtonStyle style) {
		Dimension buttonSize = new Dimension(
				ButtonStyles.MENU_BUTTON_WIDTH,
				ButtonStyles.MENU_BUTTON_HEIGHT);
		GameTextButton button = new GameTextButton(text, style);
		button.setPreferredSize(buttonSize);
		button.setMinimumSize(buttonSize);
		button.setMaximumSize(buttonSize);
		return button;
	}

}
