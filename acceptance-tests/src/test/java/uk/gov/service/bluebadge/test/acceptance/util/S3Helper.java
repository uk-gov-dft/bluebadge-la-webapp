package uk.gov.service.bluebadge.test.acceptance.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;

public class S3Helper {
Logger log = LoggerFactory.getLogger(S3Helper.class);
  private final AmazonS3 s3;

  private final String applicationBucket =
      null == System.getenv("bb_env")
          ? "uk-gov-dft-ci-applications"
          : "uk-gov-dft-" + System.getenv("bb_env") + "-applications";

  public S3Helper() {
    this.s3 = AmazonS3ClientBuilder.defaultClient();
  }

  public String putApplicationObject(String fileName, String s3key) throws URISyntaxException {
    File f = new File(this.getClass().getResource(fileName).toURI());
    if (!s3.doesObjectExist(applicationBucket, s3key)) {
      s3.putObject(applicationBucket, s3key, f);
    }
    if (!s3.doesObjectExist(applicationBucket, s3key)) {
      log.error("Could not put file in bucket {}.  Has bb_env environment variable been set?", applicationBucket);
    }
    return s3key;
  }
}
