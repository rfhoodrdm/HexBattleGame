package com.rfhoodrdm.hexbattle.gui.basecomponent.panel;

import java.awt.Color;
import java.awt.Font;

public final class PanelStyles {

	public static final int TRANSLUCENT_PRECENTAGE = 70;
	public static final int ALPHA_CHANNEL_TRANSLUCENT = 255 * TRANSLUCENT_PRECENTAGE / 100;

	public static final Color BACKGROUND_TRANSLUCENT_PANEL_COLOR = new Color(0, 0, 0, ALPHA_CHANNEL_TRANSLUCENT);
	public static final Color TITLED_BORDER_COLOR = new Color(190, 198, 207);
	public static final Color TITLE_FOREGROUND_COLOR = new Color(240, 235, 214);
	public static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 18);
	public static final int PANEL_PADDING = 16;
	public static final int SECTION_GAP = 16;

	private PanelStyles() {
	}
}
