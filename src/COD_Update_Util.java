import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

// Aly Wright 2015
/*
 * This utility is intended to simplify updating 
 * web site content hosted on AWS S3. This version
 * provides a basic UI prompting the user for new info
 * which is to be uploaded to a specific S3 bucket.
 * */
 
public class COD_Update_Util {
    public static void main(String[] args) throws IOException {

	AmazonS3 s3 = new AmazonS3Client();
    Region usEast1 = Region.getRegion(Regions.US_EAST_1);
    s3.setRegion(usEast1);

    String bucketName = "baristastest";
    String key = "test_index.html";

    System.out.println("===========================================");
    System.out.println("Getting Started with Amazon S3");
    System.out.println("===========================================\n");
    try {
        /*
         * List the buckets in your account
         */
//        System.out.println("Listing buckets");
//        for (Bucket bucket : s3.listBuckets()) {
//            System.out.println(" - " + bucket.getName());
//        }
        System.out.println();

        /*
         * List objects in your bucket by prefix - There are many options for
         * listing the objects in your bucket.  Keep in mind that buckets with
         * many objects might truncate their results when listing their objects,
         * so be sure to check if the returned object listing is truncated, and
         * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
         * additional results.
         */
        System.out.println("Listing objects");
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(""));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();

        /*
         * Download an object - When you download an object, you get all of
         * the object's metadata and a stream from which to read the contents.
         * It's important to read the contents of the stream as quickly as
         * possibly since the data is streamed directly from Amazon S3 and your
         * network connection will remain open until you read all the data or
         * close the input stream.
         *
         * GetObjectRequest also supports several other options, including
         * conditional downloading of objects based on modification times,
         * ETags, and selectively downloading a range of an object.
         */
	        System.out.println("Downloading an object");
	        S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
	        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
	        displayTextInputStream(object.getObjectContent());
	        editHTML(object.getObjectContent());

        /*
         * Upload an object to your bucket - You can easily upload a file to
         * S3, or upload directly an InputStream if you know the length of
         * the data in the stream. You can also specify your own metadata
         * when uploading to S3, which allows you set a variety of options
         * like content-type and content-encoding, plus additional metadata
         * specific to your applications.
         */
//        System.out.println("Uploading a new object to S3 from a file\n");
//        s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));


    } catch (AmazonServiceException ase) {
        System.out.println("Caught an AmazonServiceException, which means your request made it "
                + "to Amazon S3, but was rejected with an error response for some reason.");
        System.out.println("Error Message:    " + ase.getMessage());
        System.out.println("HTTP Status Code: " + ase.getStatusCode());
        System.out.println("AWS Error Code:   " + ase.getErrorCode());
        System.out.println("Error Type:       " + ase.getErrorType());
        System.out.println("Request ID:       " + ase.getRequestId());
    } 
}
    /**
     * Creates a temporary file with html data to upload to the bucket
     * 
     * @param input
     * @return
     * @throws IOException
     */
    private static File editHTML(InputStream input) throws IOException {
        File file = File.createTempFile("test_index", ".html");
        file.deleteOnExit();
        
        String startMonth = null, endMonth = null;
        String startDay = null, endDay = null;
        String endYear = null;
        
        System.out.println("For the new week:");
        System.out.println("Enter the start month: ");
        Scanner scan = new Scanner(System.in);
        startMonth = scan.nextLine();
        System.out.println("Enter the start day: ");
        startDay = scan.nextLine();
        System.out.println("Enter the end month: ");
        endMonth = scan.nextLine();
        System.out.println("Enter the end day: ");
        endDay = scan.nextLine();
        System.out.println("Enter the end year: ");
        endYear = scan.nextLine();
        
        System.out.println("Coffees of the day for the week of " + startMonth + " " 
        + startDay + " - " + endMonth + " " + endDay + ", " + endYear);
        
        String monTitle, monDesc;
        String tuesTitle, tuesDesc;
        String wedTitle, wedDesc;
        String thurTitle, thurDesc;
        String friTitle, friDesc;
        String satTitle, satDesc;
        
        System.out.println("Enter the name of Monday's coffee: ");
        monTitle = scan.nextLine();
        System.out.println("Enter the description of Monday's coffee: ");
        monDesc = scan.nextLine();
        System.out.println("Enter the name of Tuesday's coffee: ");
        tuesTitle = scan.nextLine();
        System.out.println("Enter the description of Tuesday's coffee: ");
        tuesDesc = scan.nextLine();
        System.out.println("Enter the name of Wednesday's coffee: ");
        wedTitle = scan.nextLine();
        System.out.println("Enter the description of Wednesday's coffee: ");
        wedDesc = scan.nextLine();
        System.out.println("Enter the name of Thursday's coffee: ");
        thurTitle = scan.nextLine();
        System.out.println("Enter the description of Thursday's coffee: ");
        thurDesc = scan.nextLine();
        System.out.println("Enter the name of Friday's coffee: ");
        friTitle = scan.nextLine();
        System.out.println("Enter the description of Friday's coffee: ");
        friDesc = scan.nextLine();
        System.out.println("Enter the name of Saturday's coffee: ");
        satTitle = scan.nextLine();
        System.out.println("Enter the description of Saturday's coffee: ");
        satDesc = scan.nextLine();
        
        System.out.println("Monday: " + monTitle + " - " + monDesc);
        System.out.println("Tuesday: " + tuesTitle + " - " + tuesDesc);
        System.out.println("Wednesday: " + wedTitle + " - " + wedDesc);
        System.out.println("Thursday: " + thurTitle + " - " + thurDesc);
        System.out.println("Friday: " + friTitle + " - " + friDesc);
        System.out.println("Saturday: " + satTitle + " - " + satDesc);

        
        
        
        
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        
        writer.close();
    	return file;
	}
    
    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input
     *            The input stream to display as text.
     *
     * @throws IOException
     */
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
}