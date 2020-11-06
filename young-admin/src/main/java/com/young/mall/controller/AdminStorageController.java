package com.young.mall.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungStorage;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.dto.FileDto;
import com.young.mall.service.YoungStorageService;
import com.young.mall.storage.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 存储Controller
 * @Author: yqz
 * @CreateDate: 2020/11/5 16:34
 */
@Api(tags = "存储Controller", description = "存储Controller")
@RestController
@RequestMapping("/admin/storage")
public class AdminStorageController extends BaseController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private YoungStorageService youngStorageService;

    @ApiOperation("分页查询所有对象存储")
    @GetMapping("/list")
    public ResBean list(String key, String name,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "add_time") String sort,
                        @RequestParam(defaultValue = "desc") String order) {
        logger.info("key:{},name:{},page:{},size:{},sort:{},order:{}", key, name, page, size, sort, order);
        Optional<List<YoungStorage>> youngStorages = youngStorageService.querySelective(key, name, page, size, sort, order);
        if (!youngStorages.isPresent()) {
            ResBean.failed("查询失败");
        }

        CommonPage<YoungStorage> storagePage = CommonPage.restPage(youngStorages.get());
        logger.info("分页查询所有对象存储出参：{}", JSONUtil.toJsonStr(storagePage));
        return ResBean.success(storagePage);
    }

    @ApiOperation("存储文件对象")
    @PostMapping("/create")
    public ResBean creat(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String url = storageService.store(file.getInputStream(), file.getSize(),
                file.getContentType(), originalFilename);
        FileDto fileDto = FileDto.builder().url(url).build();
        return ResBean.success(fileDto);
    }

    @ApiOperation("根据id读取该对象")
    @PostMapping("/read")
    public ResBean read(@NotNull Integer id) {

        Optional<YoungStorage> storageOptional = youngStorageService.findById(id);

        if (!storageOptional.isPresent()) {
            return ResBean.failed("读取对象失败");
        }
        return ResBean.success(storageOptional.get());
    }

    @ApiOperation("更新存储对象")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungStorage youngStorage) {
        Optional<Integer> integerOptional = youngStorageService.update(youngStorage);

        if (!integerOptional.isPresent()) {
            return ResBean.failed("更新失败");
        }
        return ResBean.success(youngStorage);
    }

    @ApiOperation("删除存储对象")
    @PostMapping("/delete")
    public ResBean delete(@RequestBody YoungStorage youngStorage) {
        String key = youngStorage.getKey();
        if (StrUtil.isEmpty(key)) {
            return ResBean.validateFailed("删除对象ID不能为空");
        }
        Optional<Integer> integerOptional = youngStorageService.deleteByKey(key);
        if (!integerOptional.isPresent()) {
            return ResBean.failed("删除失败");
        }

        storageService.delete(key);

        return ResBean.success("删除成功");
    }

}
