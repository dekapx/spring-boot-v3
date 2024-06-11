package com.dekapx.apps.notification.euroclear.factory;

import com.dekapx.apps.notification.euroclear.factory.EuroclearApiHandlerFactory;
import com.dekapx.apps.notification.euroclear.handler.RedemptionEuroclearApiHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class EuroclearApiHandlerFactoryITest {
    @Autowired
    private EuroclearApiHandlerFactory euroclearApiHandlerFactory;

    @Test
    public void givenBeanNameShouldLookupAndReturnFileWriter() {
        var apiHandler = this.euroclearApiHandlerFactory.getApiHandler("redemptionEuroclearApiHandler");
        assertThat(apiHandler).isNotNull()
                .satisfies(o -> {
                    assertThat(o).isInstanceOf(RedemptionEuroclearApiHandler.class);
                });
    }
}
