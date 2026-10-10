package com.rfhoodrdm.hexbattle.gui.basecomponent.panel;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class TitledGamePanel extends GameTranslucentPanel {

	private static final long serialVersionUID = 1L;

	public TitledGamePanel(String title) {
		this(title, true);
	}

	public TitledGamePanel(String title, boolean paintTranslucentBackground) {
		super(paintTranslucentBackground);
		Border lineBorder = BorderFactory.createLineBorder(PanelStyles.TITLED_BORDER_COLOR);
		TitledBorder titledBorder = BorderFactory.createTitledBorder(
				lineBorder,
				title,
				TitledBorder.LEADING,
				TitledBorder.TOP,
				PanelStyles.TITLE_FONT,
				PanelStyles.TITLE_FOREGROUND_COLOR);
		setBorder(titledBorder);
	}
}
