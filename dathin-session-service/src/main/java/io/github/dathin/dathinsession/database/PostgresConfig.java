package io.github.dathin.dathinsession.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Bean
    @ConfigurationProperties(prefix = "user.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public JdbcTemplate postgresJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
