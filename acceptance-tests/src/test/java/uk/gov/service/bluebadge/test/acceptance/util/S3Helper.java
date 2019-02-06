package uk.gov.service.bluebadge.test.acceptance.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.io.File;
import java.net.URISyntaxException;

public class S3Helper {

  private final AmazonS3 s3;

  private final String applicationBucket =
      null == System.getenv("bb_env")
          ? "uk-gov-dft-dev-applications"
          : "uk-gov-dft-" + System.getenv("bb_env") + "-applications";

  public S3Helper() {
    this.s3 = AmazonS3ClientBuilder.defaultClient();
  }

  public String putApplicationObject(String fileName, String s3key) throws URISyntaxException {
    File f = new File(this.getClass().getResource(fileName).toURI());
    if (!s3.doesObjectExist(applicationBucket, s3key)) {
      s3.putObject(applicationBucket, s3key, f);
    }
    return s3key;
  }
}
