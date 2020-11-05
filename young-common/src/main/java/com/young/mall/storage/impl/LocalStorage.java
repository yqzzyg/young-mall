package com.young.mall.storage.impl;

import com.young.mall.exception.WebApiException;
import com.young.mall.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * @Description: 服务器本地对象存储服务
 * @Author: yqz
 * @CreateDate: 2020/11/5 11:02
 */
public class LocalStorage implements Storage {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String storagePath;
    private String address;

    private Path rootLocation;


    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
        this.rootLocation = Paths.get(storagePath);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    public void setRootLocation(Path rootLocation) {
        this.rootLocation = rootLocation;
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {

        try {
            Files.copy(inputStream, rootLocation.resolve(keyName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.info("本地存储文件失败：{}", e.getMessage());
            throw new WebApiException("本地存储文件失败" + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation))
                    .map(path -> rootLocation.relativize(path));
        } catch (IOException e) {
            throw new WebApiException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String keyName) {
        return rootLocation.resolve(keyName);
    }

    @Override
    public Resource loadAsResource(String keyName) {
        try {
            Path file = load(keyName);
            Resource resource = new UrlResource(file.toUri());
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
        Path file = load(keyName);
        try {
            Files.delete(file);
        } catch (IOException e) {
            logger.info("本地存储删除失败：{}", e.getMessage());
            throw new WebApiException("本地存储删除失败:" + e.getMessage());
        }
    }

    @Override
    public String generateUrl(String keyName) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        String uri = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
        String realUrl = uri + "/" + storagePath + "/" + keyName;
        return realUrl;
    }
}
