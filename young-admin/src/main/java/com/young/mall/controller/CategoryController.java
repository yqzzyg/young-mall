package com.young.mall.controller;

import cn.hutool.json.JSONUtil;
import com.young.db.entity.YoungCategory;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.service.MallCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Description: 商品分类
 * @Author: yqz
 * @CreateDate: 2020/11/1 14:56
 */
@Api(tags = "CategoryController", description = "商品分类")
@RestController
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    @Autowired
    private MallCategoryService categoryService;

    @ApiOperation("商品分类list")
    @PreAuthorize("@pms.hasPermission('admin:category:list')")
    @GetMapping("/list")
    public ResBean queryCategoryList(String id, String name,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "add_time") String sort,
                                     @RequestParam(defaultValue = "desc") String order) {

        logger.error("id:{},name:{},page:{},size:{},sort:{},order:{}", id, name, page, size, sort, order);

        Optional<List<YoungCategory>> listOptional = categoryService.queryCateGoryList(id, name, page, size, sort, order);

        if (!listOptional.isPresent()) {
            return ResBean.failed("品牌分类查询失败");
        }

        CommonPage<YoungCategory> restPage = CommonPage.restPage(listOptional.get());
        return ResBean.success(restPage);
    }

    /**
     * 用户新增或者修改商品分类时，如果选择二级类目，则从此接口获取父类目
     *
     * @return
     */
    @ApiOperation("一级分类目录")
    @GetMapping("/l1")
    public ResBean getLevelFirst() {

        Optional<List<YoungCategory>> optionalList = categoryService.queryLevelFirst();
        List<YoungCategory> l1CatList = optionalList.get();
        List<Map<String, Object>> data = new ArrayList<>(l1CatList.size());
        for (YoungCategory category : l1CatList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", category.getId());
            d.put("label", category.getName());
            data.add(d);
        }
        return ResBean.success(data);
    }


    @ApiOperation("删除商品分类")
    @PreAuthorize("@pms.hasPermission('admin:category:delete')")
    @DeleteMapping("/{infoIds}")
    public ResBean delete(@PathVariable("infoIds") Integer infoIds) {

        logger.error("删除商品分类，入参：{}", JSONUtil.toJsonStr(infoIds));
        if (infoIds == null) {
            return ResBean.validateFailed();
        }
        Optional<Integer> integerOptional = categoryService.delete(infoIds);
        if (!integerOptional.isPresent()) {
            return ResBean.failed("删除失败");
        }
        return ResBean.success(integerOptional.get());
    }

    @ApiOperation("创建商品分类")
    @PreAuthorize("@pms.hasPermission('admin:category:create')")
    @PostMapping("/create")
    public ResBean creat(@RequestBody YoungCategory category) {

        logger.error("创建商品分类,入参：{}", JSONUtil.toJsonStr(category));
        Optional<Integer> integerOptional = categoryService.creat(category);
        if (!integerOptional.isPresent()) {

            return ResBean.failed("添加失败");
        }
        return ResBean.success(integerOptional.get());
    }

    @ApiOperation("更新分类")
    @PreAuthorize("@pms.hasPermission('admin:category:update')")
    @PostMapping("/update")
    public ResBean update(@RequestBody YoungCategory category) {

        Optional<Integer> optionalInteger = categoryService.update(category);
        if (!optionalInteger.isPresent()) {

            return ResBean.failed("更新失败");
        }
        return ResBean.success(optionalInteger.get());
    }

}
