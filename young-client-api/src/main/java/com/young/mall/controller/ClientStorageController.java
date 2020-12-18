package com.young.mall.controller;

import com.young.mall.common.ResBean;
import com.young.mall.storage.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 文件上传
 * @Author: yqz
 * @CreateDate: 2020/12/18 15:22
 */
@Api(tags = "ClientStorageController")
@RestController
@RequestMapping("/client/storage")
public class ClientStorageController {

    @Autowired
    private StorageService storageService;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResBean upload(@RequestParam("file") MultipartFile file) throws IOException {

        String filename = file.getOriginalFilename();

        String url = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), filename);
        Map<String, Object> data = new HashMap<>(2);
        data.put("url", url);
        return ResBean.success(data);
    }
}
