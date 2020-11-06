package com.young.mall.config.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @Description: 对象存储配置读取
 * @Author: yqz
 * @CreateDate: 2020/11/5 15:30
 */
@Data
@Primary //使Spring更好的识别该对象，以便于注入
@Component
@ConfigurationProperties(prefix = "young.storage")
public class StorageProperties {
    private String active;
    private Local local;
    private Aliyun aliyun;
    private Tencent tencent;
    private MinIO minIO;
    private Qiniu qiniu;

    @Data
    public static class Local {
        private String address;
        private String storagePath;
    }

    @Data
    public static class Tencent {
        private String secretId;
        private String secretKey;
        private String region;
        private String bucketName;
    }

    @Data
    public static class Aliyun {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
    }

    @Data
    public static class MinIO {
        private String accessKey;
        private String secretKey;
        private String endpoint;
        private String bucketName;
    }

    @Data
    public static class Qiniu {
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucketName;
    }
}
