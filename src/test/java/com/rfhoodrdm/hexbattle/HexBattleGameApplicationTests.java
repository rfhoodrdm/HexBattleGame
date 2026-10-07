package com.rfhoodrdm.hexbattle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.rfhoodrdm.hexbattle.service.soundmanager.SoundManager;

@SpringBootTest
class HexBattleGameApplicationTests {

	@MockitoBean
	private SoundManager soundManager;

	@Test
	void contextLoads() {
	}

}
