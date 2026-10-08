package com.rfhoodrdm.hexbattle.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;
import com.rfhoodrdm.hexbattle.config.GameConfig;
import com.rfhoodrdm.hexbattle.service.screennavigator.ScreenNavigator;
import com.rfhoodrdm.hexbattle.state.app.AppState;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = -3283069073884263018L;
	private static final String QUIT_CONFIRMATION_MESSAGE =
			"Are you sure you want to quit the current game?";
	private static final String QUIT_CONFIRMATION_TITLE = "Quit Current Game?";

	private final AppState appState;
	private final ScreenNavigator screenNavigator;

	public GameFrame(
			GameConfig gameConfig,
			JPanel screenContainer,
			AppState appState,
			ScreenNavigator screenNavigator) {
		this.appState = appState;
		this.screenNavigator = screenNavigator;

		String windowTitle = gameConfig.getGameName() + " " + gameConfig.getVersionNumber();
		setTitle(windowTitle);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setJMenuBar(createMenuBar());
		setContentPane(screenContainer);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		addWindowListener(createWindowCloseListener());
	}

	private JMenuBar createMenuBar() {
		JMenuItem newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.addActionListener(event -> startNewGame());

		JMenuItem returnToTitleMenuItem = new JMenuItem("Return to Title");
		returnToTitleMenuItem.addActionListener(event -> returnToTitle());

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(event -> exitApplication());

		JMenu mainMenu = new JMenu("Main");
		mainMenu.add(newGameMenuItem);
		mainMenu.add(returnToTitleMenuItem);
		mainMenu.add(exitMenuItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(mainMenu);
		return menuBar;
	}

	private WindowAdapter createWindowCloseListener() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitApplication();
			}
		};
	}

	private void startNewGame() {
		ScreenName currentScreen = appState.getCurrentScreen();
		switch (currentScreen) {
			case TITLE_SCREEN, CREDITS_SCREEN -> transitionTo(ScreenName.SETUP_SCREEN);
			case SETUP_SCREEN -> {
			}
			case SKIRMISH_SCREEN -> transitionAfterConfirmation(ScreenName.SETUP_SCREEN);
		}
	}

	private void returnToTitle() {
		ScreenName currentScreen = appState.getCurrentScreen();
		switch (currentScreen) {
			case TITLE_SCREEN, CREDITS_SCREEN -> {
			}
			case SETUP_SCREEN -> transitionTo(ScreenName.TITLE_SCREEN);
			case SKIRMISH_SCREEN -> transitionAfterConfirmation(ScreenName.TITLE_SCREEN);
		}
	}

	private void transitionAfterConfirmation(ScreenName screenName) {
		if (confirmLeavingSkirmish()) {
			transitionTo(screenName);
		}
	}

	private void transitionTo(ScreenName screenName) {
		screenNavigator.transitionToScreen(screenName);
	}

	private void exitApplication() {
		if (appState.getCurrentScreen() != ScreenName.SKIRMISH_SCREEN || confirmLeavingSkirmish()) {
			System.exit(0);
		}
	}

	private boolean confirmLeavingSkirmish() {
		int selectedOption = JOptionPane.showConfirmDialog(
				this,
				QUIT_CONFIRMATION_MESSAGE,
				QUIT_CONFIRMATION_TITLE,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		return selectedOption == JOptionPane.YES_OPTION;
	}
}
