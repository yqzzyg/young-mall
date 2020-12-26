package com.young.mall.storage.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.young.mall.dto.BucketPolicyConfigDto;
import com.young.mall.exception.Asserts;
import com.young.mall.storage.Storage;
import io.minio.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @Description: MinIO对象存储
 * @Author: yqz
 * @CreateDate: 2020/11/6 21:11
 */
@Data
public class MinioStorage implements Storage {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucketName;

    private MinioClient minioClient;

    public MinioClient getMinio() {
        try {
            if (BeanUtil.isEmpty(minioClient)) {
                minioClient = MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey, secretKey)
                        .build();
            }
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (isExist) {
                logger.error("存储桶已经存在！");
            } else {
                //创建存储桶并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                BucketPolicyConfigDto bucketPolicyConfigDto = createBucketPolicyConfigDto(bucketName);
                SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
            }
        } catch (Exception e) {
            logger.error("创建MinIO客户端失败");
        }
        return minioClient;
    }

    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement = BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::" + bucketName + "/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2012-10-17")
                .Statement(CollUtil.toList(statement))
                .build();
    }

    private String getBaseUrl() {
        return endpoint + "/" + bucketName + "/";
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(keyName)
                .contentType(contentType)
                .stream(inputStream, contentLength, ObjectWriteArgs.MIN_MULTIPART_SIZE)
                .build();
        try {
            ObjectWriteResponse response = getMinio().putObject(putObjectArgs);
            String jsonStr = JSONUtil.toJsonStr(response);
            logger.error("MinIO上传结果：{}", jsonStr);
        } catch (Exception e) {
            Asserts.fail("上传发生错误");
            e.printStackTrace();
            logger.error("上传发生错误：{}", e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String keyName) {
        return null;
    }

    @Override
    public Resource loadAsResource(String keyName) {
        try {
            URL url = new URL(getBaseUrl() + keyName);
            Resource resource = new UrlResource(url);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            logger.error("loadAsResource失败：{}", e.getMessage());
            e.printStackTrace();
            Asserts.fail("加载发生错误");

        }
        return null;
    }

    @Override
    public void delete(String keyName) {
        RemoveObjectArgs build = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(keyName)
                .build();

        try {
            getMinio().removeObject(build);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("MinIO删除失败：{}", e.getMessage());
            Asserts.fail("删除发生错误");
        }
    }

    @Override
    public String generateUrl(String keyName) {
        return getBaseUrl() + keyName;
    }
}
