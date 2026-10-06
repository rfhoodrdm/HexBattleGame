package com.rfhoodrdm.hexbattle.config;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class GameConfig {

	private String gameName = "HexBattle";
	private String versionNumber = "1.0.0";
}
