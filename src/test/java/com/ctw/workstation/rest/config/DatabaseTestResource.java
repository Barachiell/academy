package com.ctw.workstation.rest.config;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class DatabaseTestResource implements QuarkusTestResourceLifecycleManager {

    PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres");





    @Override
    public Map<String, String> start() {
        postgres.start();
        Log.infov("About to Start", "TestConfig.start");
        /*
        map.of goes over the application properties and can make changes to it for the tests.
        If the property doesn't exist it will add it, and if it doesn't will create it for us.
         */
        return Map.of("quarkus.log.console.json", "false",
                "quarkus.datasource.username", postgres.getUsername(),
                "quarkus.datasource.password", postgres.getPassword(),
                "quarkus.datasource.jdbc.url", postgres.getJdbcUrl(),
        "quarkus.hibernate-orm.database.generation", "drop-and-create");
    }


    //the folowing run in case an error occur
    @Override
    public void stop() {
        postgres.stop();
        Log.infov("Stop here", "TestConfig.stop" );
    }
}
