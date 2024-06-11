package com.dekapx.apps.notification.icsd.factory;

import com.dekapx.apps.notification.icsd.handler.EuroclearIcsdHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IcsdHandlerFactoryITest {
    @Autowired
    private IcsdHandlerFactory icsdHandlerFactory;

    @Test
    public void givenBeanNameShouldLookupAndReturnFileWriter() {
        var apiHandler = this.icsdHandlerFactory.getIcsdHandler("euroclearIcsdHandler");
        assertThat(apiHandler).isNotNull()
                .satisfies(o -> {
                    assertThat(o).isInstanceOf(EuroclearIcsdHandler.class);
                });
    }
}
