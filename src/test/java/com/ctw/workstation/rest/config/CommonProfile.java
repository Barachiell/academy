package com.ctw.workstation.rest.config;

import io.quarkus.test.junit.QuarkusTestProfile;
import org.osgi.resource.Wire;

import java.util.List;

public class CommonProfile implements QuarkusTestProfile {

    @Override
    public List<TestResourceEntry> testResources(){
        return List.of(
                new TestResourceEntry(DatabaseTestResource.class),
                new TestResourceEntry(WireMockResource.class)
        );
    }

}
