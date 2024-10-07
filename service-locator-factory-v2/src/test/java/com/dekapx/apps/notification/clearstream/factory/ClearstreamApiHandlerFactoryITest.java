package com.dekapx.apps.notification.clearstream.factory;

import com.dekapx.apps.notification.clearstream.handler.RedemptionClearstreamApiHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.dekapx.apps.notification.util.BeanUtils.generateBeanName;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ClearstreamApiHandlerFactoryITest {
    @Autowired
    private ClearstreamApiHandlerFactory clearstreamApiHandlerFactory;

    @Test
    public void givenBeanNameShouldLookupAndReturnFileWriter() {
        var apiHandler = this.clearstreamApiHandlerFactory
                .getApiHandler(generateBeanName(RedemptionClearstreamApiHandler.class));
        assertThat(apiHandler).isNotNull()
                .satisfies(o -> {
                    assertThat(o).isInstanceOf(RedemptionClearstreamApiHandler.class);
                });
    }
}
