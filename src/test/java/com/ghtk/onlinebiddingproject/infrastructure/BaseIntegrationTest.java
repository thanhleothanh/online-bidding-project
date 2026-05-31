package com.ghtk.onlinebiddingproject.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public abstract class BaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void teardown() throws Exception {
        truncateAllTables();
    }

    protected void truncateAllTables() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Disable foreign key checks
            statement.execute("SET FOREIGN_KEY_CHECKS = 0;");
            
            // Get all tables and truncate them
            List<String> tables = jdbcTemplate.queryForList("SELECT table_name FROM information_schema.tables WHERE table_schema = DATABASE()", String.class);
            
            for (String table : tables) {
                try {
                    statement.execute("TRUNCATE TABLE " + table + " CASCADE;");
                } catch (SQLException e) {
                    log.error("Error truncating table: {}", table);
                }
            }
            // Re-enable foreign key checks
            statement.execute("SET FOREIGN_KEY_CHECKS = 1;");
        }
    }

}
