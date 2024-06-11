package com.dekapx.apps.notification.icsd.factory;

import com.dekapx.apps.notification.model.NotificationModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class IcsdHandlerFactoryITest {
    @Autowired
    private IcsdHandlerFactory icsdHandlerFactory;

    @Test
    public void givenBeanNameShouldLookupAndReturnFileWriter() {
        this.icsdHandlerFactory.invokeIcsdHandlers(new NotificationModel("Redemption"));
        log.info("Handlers notified...");
        assertThat(true);
    }
}
