package uk.gov.service.bluebadge.test.acceptance.steps;

import static org.slf4j.LoggerFactory.getLogger;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import uk.gov.service.bluebadge.test.acceptance.util.DbUtils;

public class DatabaseSteps {

  private static final Logger log = getLogger(DatabaseSteps.class);

  private final DbUtils dbUtils;

  public DatabaseSteps() {

    String dbHost = System.getProperty("dbHost");
    if (StringUtils.isBlank(dbHost)) {
      dbHost = "localhost";
    }
    Map<String, Object> settings = new HashMap<>();
    settings.put("username", "developer");
    settings.put(" ***REMOVED***);
    settings.put(
        "url", "jdbc:postgresql://" + dbHost + ":5432/bb_dev?currentSchema=applicationmanagement");
    settings.put("driverClassName", "org.postgresql.Driver");

    dbUtils = new DbUtils(settings);
  }

  @Before("@NewApplicationScripts")
  public void executeInsertApplicationsDBScript() throws SQLException {
    dbUtils.runScript("scripts/create_applications.sql");
  }

  @After("@NewApplicationScripts")
  public void executeDeleteApplicationsDBScript() throws SQLException {
    dbUtils.runScript("scripts/delete_applications.sql");
  }

  @Before("@NewApplicationPaginationScripts")
  public void executeInsertApplicationsPaginationDBScript() throws SQLException {
    dbUtils.runScript("scripts/create_applications_pagination.sql");
  }

  @After("@NewApplicationPaginationScripts")
  public void executeDeleteApplicationsPaginationDBScript() throws SQLException {
    dbUtils.runScript("scripts/delete_applications_pagination.sql");
  }

  @Before("@UsersRolesAndPermissionsScripts")
  public void executeInsertUsersDBScript() throws SQLException {
    dbUtils.runScript("scripts/create_users.sql");
  }

  @After("@UsersRolesAndPermissionsScripts")
  public void executeDeleteUsersDBScript() throws SQLException {
    dbUtils.runScript("scripts/delete_users.sql");
  }

  @Before("@ReplaceBadgeScripts")
  public void executeCreateBadgesDBScript() throws SQLException {
    dbUtils.runScript("scripts/badges/create-badges.sql");
  }

  @After("@ReplaceBadgeScripts")
  public void executeDeleteBadgesDBScript() throws SQLException {
    dbUtils.runScript("scripts/badges/delete-badges.sql");
  }

  public void runScript(String scriptPath) throws SQLException {
    dbUtils.runScript(scriptPath);
  }

  public String queryForString(String query) {
    return dbUtils.readValue(query, String.class);
  }
}
