package com.young.mall.config.storage;

import com.young.mall.exception.Asserts;
import com.young.mall.storage.StorageService;
import com.young.mall.storage.impl.AliyunStorage;
import com.young.mall.storage.impl.LocalStorage;
import com.young.mall.storage.impl.QiniuStorage;
import com.young.mall.storage.impl.TencentStorage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/11/5 15:33
 */
@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageAutoConfiguration {

    private final StorageProperties properties;

    public StorageAutoConfiguration(StorageProperties properties) {
        this.properties = properties;
    }

    @Bean
    public StorageService storageService() {
        StorageService storageService = new StorageService();
        String active = this.properties.getActive();

        storageService.setActive(active);
        if (active.equals("local")) {
            storageService.setStorage(localStorage());
        } else if (active.equals("aliyun")) {
            storageService.setStorage(aliyunStorage());
        } else if (active.equals("tencent")) {
            storageService.setStorage(tencentStorage());
        } else if (active.equals("qiniu")) {
            storageService.setStorage(qiniuStorage());
        } else {
            Asserts.fail("当前存储模式 " + active + " 不支持");
        }
        return storageService;
    }

    @Bean
    public LocalStorage localStorage() {
        LocalStorage localStorage = new LocalStorage();
        StorageProperties.Local local = properties.getLocal();
        localStorage.setAddress(local.getAddress());
        localStorage.setStoragePath(local.getStoragePath());
        return localStorage;
    }

    @Bean
    public AliyunStorage aliyunStorage() {
        AliyunStorage aliyunStorage = new AliyunStorage();

        StorageProperties.Aliyun aliyun = properties.getAliyun();
        aliyunStorage.setAccessKeyId(aliyun.getAccessKeyId());
        aliyunStorage.setAccessKeySecret(aliyun.getAccessKeySecret());
        aliyunStorage.setBucketName(aliyun.getBucketName());
        aliyunStorage.setEndpoint(aliyun.getEndpoint());
        return aliyunStorage;
    }

    @Bean
    public TencentStorage tencentStorage() {
        TencentStorage tencentStorage = new TencentStorage();

        StorageProperties.Tencent tencent = properties.getTencent();

        tencentStorage.setSecretId(tencent.getSecretId());
        tencentStorage.setSecretKey(tencent.getSecretKey());
        tencentStorage.setBucketName(tencent.getBucketName());
        tencentStorage.setRegion(tencent.getRegion());
        return tencentStorage;
    }

    @Bean
    public QiniuStorage qiniuStorage() {
        QiniuStorage qiniuStorage = new QiniuStorage();
        StorageProperties.Qiniu qiniu = properties.getQiniu();

        qiniuStorage.setAccessKey(qiniu.getAccessKey());
        qiniuStorage.setSecretKey(qiniu.getSecretKey());
        qiniuStorage.setBucketName(qiniu.getBucketName());
        qiniuStorage.setEndpoint(qiniu.getEndpoint());
        return qiniuStorage;
    }
}
