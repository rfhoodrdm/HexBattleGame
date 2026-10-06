package com.rfhoodrdm.hexbattle.service.screennavigator;

import java.util.Objects;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.rfhoodrdm.hexbattle.common.constants.ScreenName;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScreenNavigatorService implements ScreenNavigator {
	
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public void transitionToScreen(ScreenName screenToShow) {
		Objects.requireNonNull(screenToShow, "Screen to show must not be null");
		ScreenTransitionRequest request = new ScreenTransitionRequest(screenToShow);
		eventPublisher.publishEvent(request);
	}

}
