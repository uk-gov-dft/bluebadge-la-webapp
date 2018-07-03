package uk.gov.service.bluebadge.test.acceptance.util;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class SnapshotHelper {

  public static void takeSnapShot(WebDriver webdriver) throws Exception {

    final LocalDateTime now = LocalDateTime.now();
    String nowFormatter = now.format(DateTimeFormatter.ISO_DATE_TIME);
    final String fileWithPath = "./screenshot/screenshot" + nowFormatter + ".jpg";

    //Convert web driver object to TakeScreenshot

    TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

    //Call getScreenshotAs method to create image file

    File srcFile = scrShot.getScreenshotAs(OutputType.FILE);

    //Move image file to new destination

    File destFile = new File(fileWithPath);

    //Copy file at destination

    FileUtils.copyFile(srcFile, destFile);
  }
}
