package io.tenants.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlywayConfig {

    private static final String DEFAULT_LOCATION = "db/migration";
    private static final String DEFAULT_SCHEMA = "saas";

    private final DataSource dataSource;

    @Bean
    public Flyway flyway() {
        log.info("Migrating default schema: {} with location: {}", DEFAULT_SCHEMA, DEFAULT_LOCATION);
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(DEFAULT_SCHEMA)
                .locations(DEFAULT_LOCATION)
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();
        return flyway;
    }

}
