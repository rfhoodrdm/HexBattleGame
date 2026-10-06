package com.rfhoodrdm.hexbattle.service.screennavigator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;

class ScreenNavigatorServiceTests {

	@Test
	void transitionToScreenPublishesRequest() {
		List<Object> publishedEvents = new ArrayList<>();
		ApplicationEventPublisher eventPublisher = publishedEvents::add;
		ScreenNavigator screenNavigator = new ScreenNavigatorService(eventPublisher);

		screenNavigator.transitionToScreen(ScreenName.CREDITS_SCREEN);

		ScreenTransitionRequest expectedRequest = new ScreenTransitionRequest(ScreenName.CREDITS_SCREEN);
		assertEquals(List.of(expectedRequest), publishedEvents);
	}
}
