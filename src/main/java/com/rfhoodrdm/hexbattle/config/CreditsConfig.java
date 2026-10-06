package com.rfhoodrdm.hexbattle.config;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Data
@Slf4j
public class CreditsConfig {

	private String creditsText = """
			Lead Developer: 
				Robert H.
			
			Artwork by:
				ChatGPT
				Codex
			
			Music by:
				Google Gemini
			
			""";
	
	@PostConstruct
	public void init() {
		log.info("Credits Config: {}", this.toString());
	}
}
