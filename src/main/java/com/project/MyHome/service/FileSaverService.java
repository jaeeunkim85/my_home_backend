package com.project.MyHome.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.project.MyHome.domain.model.FileUpload;
import com.project.MyHome.response.Response;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileSaverService {

    @Value("${blog.bucket.accesess-key}")
    private String accessKey;

    @Value("${blog.bucket.secret-key}")
    private String secretKey;

    @Value("${blog.server-url}")
    private String endPoint;

    final String regionName = "kr-standard";



    public Response uploadFiles(MultipartFile[] files)  throws Exception{
        Response result = Response.from();
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
        log.error("accessKey  " + accessKey);
        log.error("secretKey  " + secretKey);
        String bucketName = "project-blog";
        FileUpload fileUpload = new FileUpload();
        // create folder
//        String folderName = "image/";

//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(0L);
//        objectMetadata.setContentType("application/x-directory");
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName, new ByteArrayInputStream(new byte[0]), objectMetadata);
//
//        try {
//            s3.putObject(putObjectRequest);
//        } catch (AmazonS3Exception e) {
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            e.printStackTrace();
//        }


        for (MultipartFile multipartFile : files) {
            String filename = multipartFile.getOriginalFilename();
            log.error("file name : " + filename);
            ObjectMetadata objectMetadata1 = new ObjectMetadata();
            objectMetadata1.setContentLength(multipartFile.getSize());
            objectMetadata1.setContentType(multipartFile.getContentType());
             File tempFile = convertMultipartFileToFile(multipartFile);

            try {
                s3.putObject(bucketName ,  filename, tempFile);
                setACL(bucketName, filename);
                log.debug(filename);
                fileUpload.setFileName(filename);
                //?????? ????????? ????????? ?????? ?????????.
                fileUpload.setFilePullPath("https://kr.object.ncloudstorage.com/project-blog/" + filename);
                result.setData(fileUpload);
            } catch (AmazonS3Exception e) {
                result.setData(false);
                e.printStackTrace();
            } catch (SdkClientException e) {
                result.setData(false);
                e.printStackTrace();
            }
        }
        return result;
    }

    private void setACL(String bucketName, String ObjectName) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();


        // set bucket ACL
        // ????????? ?????? ??????...???????????? ?????? ????????? ????????? ???????????? ?????? ?????? ?????? ??????.
        try {
            // get the current ACL
            AccessControlList accessControlList = s3.getBucketAcl(bucketName);
            // add read permission to anonymous
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            s3.setBucketAcl(bucketName, accessControlList);

        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        // set object ACL
        // ????????????  object ??? ?????? ??????.
        try {
            // get the current ACL
            AccessControlList accessControlList = s3.getObjectAcl(bucketName, ObjectName);
            // add read permission to user by ID
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
            //????????? ?????? ?????? ????????? ?????? ?????? Allusers??? ?????? ??????   user??? ????????? ?????? ??????.

            s3.setObjectAcl(bucketName, ObjectName, accessControlList);
            log.error("ACL Change....");
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
    }

    //multipart ????????? file ???????????? ?????????.
    private File convertMultipartFileToFile(MultipartFile mfile) throws Exception {
        File file = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());
        fos.close();
        return file;
    }
}
