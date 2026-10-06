package com.rfhoodrdm.hexbattle.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.rfhoodrdm.hexbattle.config.GameConfig;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = -3283069073884263018L;

	public GameFrame(GameConfig gameConfig, JPanel screenContainer) {
		String windowTitle = gameConfig.getGameName() + " " + gameConfig.getVersionNumber();
		setTitle(windowTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(createMenuBar());
		setContentPane(screenContainer);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private JMenuBar createMenuBar() {
		JMenuItem newGameMenuItem = new JMenuItem("New Game");

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(event -> System.exit(0));

		JMenu mainMenu = new JMenu("Main");
		mainMenu.add(newGameMenuItem);
		mainMenu.add(exitMenuItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(mainMenu);
		return menuBar;
	}
}
