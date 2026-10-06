package com.rfhoodrdm.hexbattle.service.dataloader.asset;

import java.awt.image.BufferedImage;

public record ImageAsset(BufferedImage image, String name) implements Asset {

}
