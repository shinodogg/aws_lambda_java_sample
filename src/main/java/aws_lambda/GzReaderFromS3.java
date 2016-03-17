package aws_lambda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class GzReaderFromS3 
{
	public void handler(S3ObjectInfo s3, Context context) throws IOException
    {
		LambdaLogger logger = context.getLogger();

		AmazonS3 s3Client = new AmazonS3Client();
		GetObjectRequest getObjectRequest = new GetObjectRequest(s3.getBucket(), s3.getKey());
		S3Object s3Object = s3Client.getObject(getObjectRequest);
    	GZIPInputStream gzis = new GZIPInputStream(s3Object.getObjectContent());
        InputStreamReader reader = new InputStreamReader(gzis);
        BufferedReader in = new BufferedReader(reader);
        String str;
        while ((str = in.readLine()) != null) {
    		logger.log(str);
    		logger.log("\n");
        }
    }
}
