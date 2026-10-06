package com.rfhoodrdm.hexbattle.gui.basecomponent.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

public final class ButtonStyles {

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
    
    public static final ButtonStyle IMPERIAL_OPTION =
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
}
