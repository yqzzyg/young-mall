package com.young.mall.storage;

import com.young.db.entity.YoungStorage;
import com.young.mall.service.MallStorageService;
import com.young.mall.utils.CharUtil;
import com.young.mall.utils.DateTimeUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description: 提供存储服务类，所有存储服务均由该类对外提供
 * @Author: yqz
 * @CreateDate: 2020/11/5 15:57
 */
@Data
public class StorageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String active;
    private Storage storage;

    @Autowired
    private MallStorageService youngStorageService;

    /**
     * 存储文件
     *
     * @param inputStream   文件输入流
     * @param contentLength 文件长度
     * @param contentType   文件类型
     * @param fileName      文件名
     * @return
     */
    public String store(InputStream inputStream, long contentLength,
                        String contentType, String fileName) {

        String key = generateKey(fileName);
        key =  DateTimeUtils.datePath() + "/" + key;
        storage.store(inputStream, contentLength, contentType, key);

        String url = generateUrl(key);

        YoungStorage youngStorage = new YoungStorage();
        youngStorage.setName(fileName);
        youngStorage.setSize(((int) contentLength));
        youngStorage.setType(contentType);
        youngStorage.setKey(key);
        youngStorage.setUrl(url);

        Optional<Integer> add = youngStorageService.add(youngStorage);
        logger.info("存储数据插入数据库：{}", add.get());
        return url;
    }

    public Stream<Path> loadAll() {
        return storage.loadAll();
    }

    public Path load(String keyName) {
        return storage.load(keyName);
    }

    public Resource loadAsResource(String keyName) {
        return storage.loadAsResource(keyName);
    }

    public void delete(String keyName) {
        storage.delete(keyName);
    }

    /**
     * 生成索引key，数据库
     *
     * @param fileName
     * @return
     */
    private String generateKey(String fileName) {
        int index = fileName.lastIndexOf('.');
        String suffix = fileName.substring(index);
        String key = null;
        YoungStorage youngStorage = null;
        do {
            key = CharUtil.getRandomString(20) + suffix;
            youngStorage = youngStorageService.findByKey(key);
        } while (youngStorage != null);
        return key;
    }

    /**
     * 获取 url
     *
     * @param keyName
     * @return
     */
    private String generateUrl(String keyName) {
        return storage.generateUrl(keyName);
    }
}
