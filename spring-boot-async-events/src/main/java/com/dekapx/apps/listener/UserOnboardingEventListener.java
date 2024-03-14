package com.dekapx.apps.listener;

import com.dekapx.apps.event.UserOnboardingEvent;
import com.dekapx.apps.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserOnboardingEventListener {
    @Async
    @EventListener
    public void onApplicationEvent(UserOnboardingEvent<UserModel> event) {
        UserModel userModel = event.getModel();
        log.info("UserOnboardingEvent received... {}", userModel.getFirstName());
    }
}
