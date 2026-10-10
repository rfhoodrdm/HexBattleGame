package com.rfhoodrdm.hexbattle.gui.basecomponent.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public final class ButtonStyles {

    public static final int MENU_BUTTON_WIDTH = 220;
    public static final int MENU_BUTTON_HEIGHT = 48;

    private ButtonStyles() {}

    public static final ButtonStyle DEFAULT =
        new ButtonStyle(
            new Font("SansSerif", Font.PLAIN, 16),
            Color.WHITE,
            new Color(70, 70, 70),
            new Color(85, 85, 85),
            new Color(50, 50, 50),
            new Color(120, 120, 120),
            new Insets(6, 12, 6, 12)
        );
    
    public static final ButtonStyle IMPERIAL_ACTION =
        new ButtonStyle(
                new Font("Serif", Font.BOLD, 18),
                new Color(240, 238, 228),
                new Color(72, 79, 89),
                new Color(92, 101, 113),
                new Color(50, 57, 66),
                new Color(190, 198, 207),
                new Insets(10, 24, 10, 24)
            );

    public static final ButtonStyle PRIMARY =
        new ButtonStyle(
            new Font("SansSerif", Font.BOLD, 16),
            Color.WHITE,
            new Color(120, 80, 30),
            new Color(145, 100, 40),
            new Color(90, 58, 22),
            new Color(180, 130, 60),
            new Insets(6, 12, 6, 12)
        );

    public static final ButtonStyle IMPERIAL_ACTIVATION =
        new ButtonStyle(
            new Font("Serif", Font.BOLD, 18),
            new Color(244, 241, 224),
            new Color(35, 78, 55),
            new Color(48, 100, 70),
            new Color(24, 57, 39),
            new Color(194, 177, 116),
            new Insets(10, 24, 10, 24)
        );

    public static final ButtonStyle IMPERIAL_CANCELLATION =
        new ButtonStyle(
            new Font("Serif", Font.BOLD, 18),
            new Color(244, 236, 228),
            new Color(100, 38, 42),
            new Color(126, 49, 54),
            new Color(72, 27, 30),
            new Color(205, 174, 164),
            new Insets(10, 24, 10, 24)
        );
}
