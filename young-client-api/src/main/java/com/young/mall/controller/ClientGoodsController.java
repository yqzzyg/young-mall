package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungSearchHistory;
import com.young.mall.common.CommonPage;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.service.ClientCategoryService;
import com.young.mall.service.ClientGoodsService;
import com.young.mall.service.ClientSearchHistoryService;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户端商品相关
 * @Author: yqz
 * @CreateDate: 2020/11/24 22:26
 */
@Api(tags = "ClientGoodsController")
@RestController
@RequestMapping("/client/goods")
public class ClientGoodsController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientGoodsService clientGoodsService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientSearchHistoryService clientSearchHistoryService;

    @Autowired
    private ClientCategoryService clientCategoryService;

    @ApiOperation("查询在售商品数量")
    @GetMapping("/count")
    public ResBean<Integer> count() {
        Integer countOnSale = clientGoodsService.getGoodsCountOnSale();
        return ResBean.success(countOnSale);
    }

    @ApiOperation("商品分类类目")
    @GetMapping("/category")
    public ResBean<Map<String, Object>> category(@NotNull Integer id) {

        Map<String, Object> category = clientGoodsService.getCategoryById(id);

        return ResBean.success(category);
    }

    @ApiOperation("根据商品分类、品牌、关键词等，分页查询商品")
    @GetMapping("/list")
    public ResBean list(Integer categoryId, Integer brandId, String keyword, Boolean isNew, Boolean isHot,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer size,
                        @RequestParam(defaultValue = "sort_order") String sort,
                        @RequestParam(defaultValue = "asc") String order) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();
        if (StrUtil.isNotBlank(keyword)) {
            YoungSearchHistory history = new YoungSearchHistory();
            history.setKeyword(keyword);
            history.setUserId(userId);
            history.setFrom("wx");
            Integer count = clientSearchHistoryService.save(history);
            logger.info("{}:新增搜索历史成功：{}", userInfo, count);
        }

        // 查询列表数据
        List<YoungGoods> goodsList = clientGoodsService.querySelective(categoryId, brandId, keyword, isNew, isHot, page, size, sort, order);
        // 查询商品所属类目列表id。
        List<Integer> catIds = clientGoodsService.getCatIds(brandId, keyword, isHot, isNew);
        //根据商品类目ids，查询所属所有类目列表
        List<YoungCategory> categoryList = null;
        if (catIds.size() != 0) {
            categoryList = clientCategoryService.queryL2ByIds(catIds);
        } else {
            categoryList = new ArrayList<>(0);
        }
        Map<String, Object> data = new HashMap<>();

        PageInfo<YoungGoods> pageInfo = PageInfo.of(goodsList);
        data.put("goodsList", goodsList);
        data.put("count", pageInfo.getTotal());
        data.put("filterCategoryList", categoryList);
        data.put("totalPages", pageInfo.getPages());
        return ResBean.success(data);
    }
}
