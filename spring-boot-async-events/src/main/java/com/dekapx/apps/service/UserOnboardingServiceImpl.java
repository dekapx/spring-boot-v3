package com.dekapx.apps.service;

import com.dekapx.apps.event.UserOnboardingEvent;
import com.dekapx.apps.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserOnboardingServiceImpl implements UserOnboardingService{
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void onboardUser(UserModel userModel) {
        UserOnboardingEvent<UserModel> userOnboardingEvent = prepareUserOnboardingEvent(userModel);
    }

    private UserOnboardingEvent<UserModel> prepareUserOnboardingEvent(UserModel userModel) {
        return new UserOnboardingEvent(this, userModel);
    }
}
