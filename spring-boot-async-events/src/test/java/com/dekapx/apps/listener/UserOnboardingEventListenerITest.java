package com.dekapx.apps.listener;

import com.dekapx.apps.event.UserOnboardingEvent;
import com.dekapx.apps.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@Slf4j
@SpringBootTest
public class UserOnboardingEventListenerITest {
    @Autowired
    private TestApplicationContextAware applicationContextAware;

    @Test
    public void publishUserOnboardingEvent() {
        UserOnboardingEvent<UserModel> userOnboardingEvent = prepareUserOnboardingEvent();
        this.applicationContextAware.getApplicationContext().publishEvent(userOnboardingEvent);
        log.info("Publish UserOnboardingEvent...");
    }

    private UserOnboardingEvent<UserModel> prepareUserOnboardingEvent() {
        return new UserOnboardingEvent(this, userModelSupplier.get());
    }

    private Supplier<UserModel> userModelSupplier = () ->
            UserModel.builder()
                    .firstName("Test")
                    .lastName("User")
                    .joiningDate(LocalDateTime.now())
                    .build();
}
