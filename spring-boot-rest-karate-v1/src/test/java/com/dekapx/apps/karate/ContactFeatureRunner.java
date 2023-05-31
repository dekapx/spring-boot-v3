package com.dekapx.apps.karate;

import com.intuit.karate.junit5.Karate;

public class ContactFeatureRunner {
    @Karate.Test
    Karate verifyContactCrud() {
        return Karate.run("").relativeTo(getClass());
    }
}
