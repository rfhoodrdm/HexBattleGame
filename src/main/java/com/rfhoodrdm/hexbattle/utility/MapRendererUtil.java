package com.rfhoodrdm.hexbattle.utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.state.map.MapTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MapRendererUtil {
	
	public static final Color BASE_HEX_BORDER_COLOR = Color.WHITE;
	public static final Color BASE_MAP_BACKGROUND_COLOR = Color.BLACK;
	public static final int DEFAULT_MAP_COLUMNS = 12;
	public static final int DEFAULT_MAP_ROWS = 12;
	public static final int PREVIEW_PADDING = 8;
	public static final float HEX_BORDER_WIDTH = 1.25F;

	private static final double HEX_HEIGHT_FACTOR = Math.sqrt(3.0);

	/**
	 * Creates a view of the Map, given the desired size and additional data.
	 * @param width
	 * @param height
	 * @param mapTemplate
	 * @return	Buffered Image rendering of the map.
	 */
	public BufferedImage renderMapPreview(int width, int height, Optional<MapTemplate> mapTemplateMaybe) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Map preview dimensions must be positive");
		}
		if (mapTemplateMaybe == null) {
			throw new IllegalArgumentException("Map template optional must not be null");
		}

		log.debug("Rendering map preview. Width: {}, height: {}, map template present: {}",
				width, height, mapTemplateMaybe.isPresent());
		BufferedImage mapImage = createMapImage(width, height);
		
		if(mapTemplateMaybe.isPresent()) {
			drawTerrain(mapImage, mapTemplateMaybe.get());
		}
		
		drawHexGrid(mapImage, mapTemplateMaybe);
		
		return mapImage;
	}
	
	private void drawTerrain(BufferedImage mapImage, MapTemplate mapTemplate) {
		// Terrain rendering will be added when map tile data is defined.
	}

	private BufferedImage createMapImage(int width, int height) {
		BufferedImage mapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = mapImage.createGraphics();
		try {
			graphics.setColor(BASE_MAP_BACKGROUND_COLOR);
			graphics.fillRect(0, 0, width, height);
		} finally {
			graphics.dispose();
		}
		return mapImage;
	}

	/**
	 * Draws the hex grid cells onto the map for however many tiles
	 * Default map size is 12 columns by 12 rows, if no mapTemplate or dimensions are specified.
	 */
	private void drawHexGrid(BufferedImage mapImage, Optional<MapTemplate> mapTemplateMaybe) {
		int columnCount = DEFAULT_MAP_COLUMNS;
		int rowCount = DEFAULT_MAP_ROWS;
		int availableWidth = Math.max(1, mapImage.getWidth() - (PREVIEW_PADDING * 2));
		int availableHeight = Math.max(1, mapImage.getHeight() - (PREVIEW_PADDING * 2));

		double gridWidthInRadii = 1.5 * columnCount + 0.5;
		double gridHeightInRadii = HEX_HEIGHT_FACTOR * (rowCount + 0.5);
		double radius = Math.min(
				availableWidth / gridWidthInRadii,
				availableHeight / gridHeightInRadii);
		double gridWidth = gridWidthInRadii * radius;
		double gridHeight = gridHeightInRadii * radius;
		double originX = (mapImage.getWidth() - gridWidth) / 2.0;
		double originY = (mapImage.getHeight() - gridHeight) / 2.0;

		Graphics2D graphics = mapImage.createGraphics();
		try {
			graphics.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setColor(BASE_HEX_BORDER_COLOR);
			graphics.setStroke(new BasicStroke(HEX_BORDER_WIDTH));
			for (int column = 0; column < columnCount; column++) {
				for (int row = 0; row < rowCount; row++) {
					double centerX = originX + radius + (column * 1.5 * radius);
					double columnOffset = column % 2 == 0 ? 0.0 : HEX_HEIGHT_FACTOR * radius / 2.0;
					double centerY = originY
							+ (HEX_HEIGHT_FACTOR * radius / 2.0)
							+ columnOffset
							+ (row * HEX_HEIGHT_FACTOR * radius);
					Polygon hexagon = createFlatTopHexagon(centerX, centerY, radius);
					graphics.drawPolygon(hexagon);
				}
			}
		} finally {
			graphics.dispose();
		}
	}

	private Polygon createFlatTopHexagon(double centerX, double centerY, double radius) {
		Polygon hexagon = new Polygon();
		for (int vertex = 0; vertex < 6; vertex++) {
			double angle = Math.toRadians(vertex * 60.0);
			int vertexX = (int) Math.round(centerX + radius * Math.cos(angle));
			int vertexY = (int) Math.round(centerY + radius * Math.sin(angle));
			hexagon.addPoint(vertexX, vertexY);
		}
		return hexagon;
	}
}
