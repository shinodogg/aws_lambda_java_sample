package aws_lambda;

import java.net.URL;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

public class S3PreSignedURLGetter {
	public String handler(S3ObjectInfo s3, Context context) {
		AmazonS3 s3client = new AmazonS3Client();

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				s3.getBucket(), s3.getKey());
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);

		java.util.Date expiration = new java.util.Date();
		expiration.setTime(expiration.getTime() + s3.getExpiration());
		generatePresignedUrlRequest.setExpiration(expiration);

		URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}
}
