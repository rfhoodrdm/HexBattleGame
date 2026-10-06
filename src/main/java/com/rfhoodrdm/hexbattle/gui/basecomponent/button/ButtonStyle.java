package com.rfhoodrdm.hexbattle.gui.basecomponent.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public record ButtonStyle(
		Font font,
        Color foreground,
        Color background,
        Color rolloverBackground,
        Color pressedBackground,
        Color borderColor,
        Insets margin) {
	
}
