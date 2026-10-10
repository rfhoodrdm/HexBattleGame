package com.rfhoodrdm.hexbattle.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class MapRendererUtilTests {

	private final MapRendererUtil mapRendererUtil = new MapRendererUtil();

	@Test
	void rendersBoundedHexGridOnBlackBackground() {
		int width = 480;
		int height = 360;
		BufferedImage preview = mapRendererUtil.renderMapPreview(
				width, height, Optional.empty());

		assertEquals(width, preview.getWidth());
		assertEquals(height, preview.getHeight());
		assertEquals(Color.BLACK.getRGB(), preview.getRGB(0, 0));
		assertEquals(Color.BLACK.getRGB(), preview.getRGB(width - 1, height - 1));
		assertTrue(countNonBlackPixels(preview) > 0);
	}

	@Test
	void rejectsNonPositiveDimensions() {
		assertThrows(
				IllegalArgumentException.class,
				() -> mapRendererUtil.renderMapPreview(0, 100, Optional.empty()));
		assertThrows(
				IllegalArgumentException.class,
				() -> mapRendererUtil.renderMapPreview(100, -1, Optional.empty()));
	}

	private int countNonBlackPixels(BufferedImage image) {
		int nonBlackPixelCount = 0;
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				if (image.getRGB(x, y) != Color.BLACK.getRGB()) {
					nonBlackPixelCount++;
				}
			}
		}
		return nonBlackPixelCount;
	}
}
