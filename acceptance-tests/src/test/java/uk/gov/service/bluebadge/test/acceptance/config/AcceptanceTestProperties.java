package uk.gov.service.bluebadge.test.acceptance.config;

import java.nio.file.Path;

@SuppressWarnings("WeakerAccess")
public class AcceptanceTestProperties {

  private final boolean headlessMode;
  private final Path downloadDir;
  private final Path tempDir;
  private final boolean zapMode;
  private final boolean bStackMode;
  private final String browserName;
  private final String browserVersion;
  private final String browserStackUser;
  private final String browserStackKey;

  AcceptanceTestProperties(
          final boolean headlessMode, final Path downloadDir, final Path tempDir, boolean zapMode, boolean bStackMode, String browserName, String browserVersion, String browserStackUser,String browserStackKey) {
    this.headlessMode = headlessMode;
    this.downloadDir = downloadDir;
    this.tempDir = tempDir;
    this.zapMode = zapMode;
    this.bStackMode = bStackMode;
    this.browserName = browserName;
    this.browserVersion = browserVersion;
    this.browserStackUser = browserStackUser;
    this.browserStackKey = browserStackKey;
  }

  public Path getDownloadDir() {
    return downloadDir;
  }

  public boolean isHeadlessMode() {
    return headlessMode;
  }

  public boolean isZapMode() {
    return zapMode;
  }

  public boolean isbStackMode() {
    return bStackMode;
  }

  public String getBrowserVersion() {
    return browserVersion;
  }

  public String getBrowserName() {
    return browserName;
  }

  public String getBrowserStackUser() {
    return browserStackUser;
  }

  public String getBrowserStackKey() {
    return browserStackKey;
  }

  public Path getTempDir() {
    return tempDir;
  }

  @Override
  public String toString() {
    return "AcceptanceTestProperties{"
            + "headlessMode="
            + headlessMode
            + ", downloadDir="
            + downloadDir
            + ", tempDir="
            + tempDir
            + ", zapMode="
            + zapMode
            + ", bStackMode="
            + bStackMode
            + ", browserName="
            + browserName
            + ", browserVersion="
            + browserVersion
            + ", browserStackUser="
            + browserStackUser
            + ", browserStackKey="
            + browserStackKey
            + '}';
  }



}
