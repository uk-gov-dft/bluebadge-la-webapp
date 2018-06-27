package uk.gov.service.bluebadge.test.acceptance;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;


/** Entry point required by {@code cucumber-jvm} to discover and run tests. */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"})
public class AcceptanceTest {
  // no-op, config class only
  @AfterClass
  public static void teardown() throws IOException {

      }
}
