package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.young.db.entity.YoungCategory;
import com.young.mall.common.ResBean;
import com.young.mall.service.ClientCategoryService;
import com.young.mall.service.MallCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 类目服务
 * @Author: yqz
 * @CreateDate: 2020/11/28 15:16
 */
@Api(tags = "ClientCatalogController")
@RestController
@RequestMapping("/client/catalog")
public class ClientCatalogController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientCategoryService clientCategoryService;

    @Autowired
    private MallCategoryService mallCategoryService;

    /**
     * 分类类目ID。 如果分类类目ID是空，则选择第一个分类类目。
     * 需要注意，这里分类类目是一级类目
     *
     * @return
     */
    @ApiOperation("分类详情")
    @GetMapping("/index")
    public ResBean<Map<String, Object>> index(@RequestParam("id") Integer id) {

        //查询所有一级分类
        List<YoungCategory> categoryList = clientCategoryService.queryLevelFirst();

        YoungCategory category = null;
        //当前一级分类目录
        if (id != null) {
            Optional<YoungCategory> optional = mallCategoryService.findById(id);
            if (optional.isPresent()) {
                category = optional.get();
            }
        } else {
            category = categoryList.get(0);
        }

        // 当前一级分类目录对应的二级分类目录
        List<YoungCategory> second = null;
        if (!BeanUtil.isEmpty(category)) {
            second = clientCategoryService.getLeveSecondByPid(category.getId());
        }
        Map<String, Object> data = new HashMap<>(4);

        data.put("categoryList", categoryList);
        data.put("currentCategory", category);
        data.put("currentSubCategory", second);
        return ResBean.success(data);
    }

    @ApiOperation("当前分类栏目")
    @GetMapping("/current")
    public ResBean<Map<String, Object>> current(@RequestParam("id") Integer id) {

        Optional<YoungCategory> optional = mallCategoryService.findById(id);
        if (!optional.isPresent()) {
            return ResBean.failed("查询失败");
        }
        YoungCategory currentCategory = optional.get();
        Optional<List<YoungCategory>> queryByPid = mallCategoryService.queryByPid(currentCategory.getId());
        if (!queryByPid.isPresent()) {
            return ResBean.failed("查询失败");
        }
        List<YoungCategory> currentSubCategory = queryByPid.get();

        Map<String, Object> data = new HashMap<>(2);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResBean.success(data);
    }
}
