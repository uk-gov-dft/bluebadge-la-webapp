package uk.gov.service.bluebadge.test.acceptance.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public class DbUtils {
  private final JdbcTemplate jdbc;
  private static final Logger log = LoggerFactory.getLogger(DbUtils.class);

  public DbUtils(Map<String, Object> config) {
    String url = (String) config.get("url");
    String username = (String) config.get("username");
    String  ***REMOVED***);
    String driver = (String) config.get("driverClassName");
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    jdbc = new JdbcTemplate(dataSource);
    log.info("init jdbc template: {}", url);
  }

  public boolean runScript(String script) throws SQLException {
    log.info("Cucumber DB. Running script: {}", script);
    ScriptUtils.executeSqlScript(
        jdbc.getDataSource().getConnection(), new ClassPathResource(script));
    return true;
  }

  public Object readValue(String query) {
    return jdbc.queryForObject(query, Object.class);
  }

  public Map<String, Object> readRow(String query) {
    log.debug("Cucumber DB query: {}", query);
    Map<String, Object> stringObjectMap = jdbc.queryForMap(query);
    log.debug("Cucumber DB result: {}", stringObjectMap);
    return stringObjectMap;
  }

  public List<Map<String, Object>> readRows(String query) {
    return jdbc.queryForList(query);
  }
}
