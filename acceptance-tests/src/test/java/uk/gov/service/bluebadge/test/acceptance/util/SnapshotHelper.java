package uk.gov.service.bluebadge.test.acceptance.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SnapshotHelper {

    public static void takeSnapShot(WebDriver webdriver) throws Exception{

        final LocalDateTime now = LocalDateTime.now();
        String nowFormatter = now.format(DateTimeFormatter.ISO_DATE_TIME);
        final String fileWithPath = "./screenshot/screenshot" + nowFormatter + ".jpg" ;

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

        File srcFile=scrShot.getScreenshotAs(OutputType.FILE);
        System.err.println("SrcFile: " + srcFile.toString());

        //Move image file to new destination

        File destFile=new File(fileWithPath);
        System.err.println("DestFile: " + destFile.toString());

        //Copy file at destination

        FileUtils.copyFile(srcFile, destFile);

    }
}
