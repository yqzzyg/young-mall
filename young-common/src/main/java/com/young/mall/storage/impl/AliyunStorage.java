package com.young.mall.storage.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.young.mall.storage.Storage;
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
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/5 10:22
 */
@Data
public class AliyunStorage implements Storage {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    private OSS getOSSClient() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    private String getBaseUrl() {
        return "https://" + bucketName + "." + endpoint + "/";
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {

        logger.info("阿里云存储OSS对象 内容长度：{},文件类型：{},KeyName:{}", contentLength, contentType, keyName);
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contentLength);
        objectMetadata.setContentType(contentType);
        // 对象键（Key）是对象在存储桶中的唯一标识。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata);
        PutObjectResult result = getOSSClient().putObject(putObjectRequest);
        ResponseMessage response = result.getResponse();
        if (!BeanUtil.isEmpty(result) && !BeanUtil.isEmpty(response)) {
            logger.info("阿里云OSS存储成功，URI:{}", response.getUri());
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
            UrlResource resource = new UrlResource(url);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(String keyName) {
        getOSSClient().deleteObject(bucketName, keyName);
    }

    @Override
    public String generateUrl(String keyName) {
        return getBaseUrl() + keyName;
    }
}
