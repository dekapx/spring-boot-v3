package com.dekapx.apps.karate;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class KarateTestRunner {
    @Test
    public void triggerKarateTests() {
        Results results = Runner
                .builder()
                .relativeTo(getClass())
                .parallel(2);
        Assertions.assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

}
