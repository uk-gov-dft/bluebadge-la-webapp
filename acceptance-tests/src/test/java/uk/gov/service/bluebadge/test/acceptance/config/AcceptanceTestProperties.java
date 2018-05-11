package uk.gov.service.bluebadge.test.acceptance.config;

import java.nio.file.Path;

@SuppressWarnings("WeakerAccess")
public class AcceptanceTestProperties {

    private final boolean headlessMode;
    private final Path downloadDir;
    private final Path tempDir;

    AcceptanceTestProperties(final boolean headlessMode, final Path downloadDir, final Path tempDir) {
        this.headlessMode = headlessMode;
        this.downloadDir = downloadDir;
        this.tempDir = tempDir;
    }

    public Path getDownloadDir() {
        return downloadDir;
    }

    public boolean isHeadlessMode() {
        return headlessMode;
    }

    public Path getTempDir() {
        return tempDir;
    }

    @Override
    public String toString() {
        return "AcceptanceTestProperties{"
            + "headlessMode=" + headlessMode
            + ", downloadDir=" + downloadDir
            + ", tempDir=" + tempDir
            + '}';
    }
}
