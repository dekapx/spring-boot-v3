package com.dekapx.apps.controller;

import com.dekapx.apps.event.UserOnboardingEvent;
import com.dekapx.apps.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import static com.dekapx.apps.common.ResourceUrls.BASE_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class UserOnboardController {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/onboard")
    public String onboardUser() {
        UserOnboardingEvent<UserModel> userOnboardingEvent = prepareEvent(userModelSupplier.get());
        log.info("Publishing UserOnboardingEvent...");
        eventPublisher.publishEvent(userOnboardingEvent);
        log.info("UserOnboardingEvent published...");
        return "UserOnboarding completed...";
    }

    private UserOnboardingEvent<UserModel> prepareEvent(UserModel userModel) {
        return new UserOnboardingEvent(this, userModel);
    }

    private Supplier<UserModel> userModelSupplier = () ->
            UserModel.builder()
                    .firstName("Test")
                    .lastName("User")
                    .joiningDate(LocalDateTime.now())
                    .build();
}
