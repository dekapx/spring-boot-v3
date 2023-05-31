package com.dekapx.apps.karate;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactFeatureRunner {
    @Karate.Test
    @Order(1)
    Karate verifyApplicationHealth() {
        return Karate.run("actuator-health").relativeTo(getClass());
    }

    @Karate.Test
    @Order(2)
    Karate verifyContactGetFeature() {
        return Karate.run("contacts-get").relativeTo(getClass());
    }

    @Karate.Test
    @Order(3)
    Karate verifyContactPostFeature() {
        return Karate.run("contacts-post").relativeTo(getClass());
    }

    @Karate.Test
    @Order(4)
    Karate verifyContactPutFeature() {
        return Karate.run("contacts-put").relativeTo(getClass());
    }
}
