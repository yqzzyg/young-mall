package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungGoods;
import com.young.db.entity.YoungSearchHistory;
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
    public ResBean<Map<String, Object>> category(@NotNull @RequestParam("id") Integer id) {

        Map<String, Object> category = clientGoodsService.getCategoryById(id);

        return ResBean.success(category);
    }

    @ApiOperation("根据商品分类、品牌、关键词等，分页查询商品")
    @GetMapping("/list")
    public ResBean list(@RequestParam(required = false) List<Integer> goodsIds, Integer categoryId, Integer brandId, String keyword, Boolean isNew, Boolean isHot,
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
            logger.error("{}:新增搜索历史成功：{}", userInfo, count);
        }

        // 查询列表数据
        List<YoungGoods> goodsList = clientGoodsService.querySelective(goodsIds,categoryId, brandId, keyword, isNew, isHot, page, size, sort, order);
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
        //用于展示”筛选--分类“
        data.put("filterCategoryList", categoryList);
        data.put("totalPages", pageInfo.getPages());
        return ResBean.success(data);
    }

    @ApiOperation("商品详情")
    @GetMapping("/detail")
    public ResBean details(@NotNull @RequestParam("id") Integer id) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }
        Integer userId = userInfo.getYoungUser().getId();
        Map<String, Object> details = clientGoodsService.details(userId, id);
        return ResBean.success(details);
    }

    @ApiOperation("商品详情页面“大家都在看”推荐商品")
    @GetMapping("/related")
    public ResBean related(@NotNull @RequestParam("id") Integer id) {

        YoungGoods goods = clientGoodsService.findById(id);
        if (BeanUtil.isEmpty(goods)) {
            return ResBean.failed("查询失败");
        }

        //目前的商品推荐算法仅仅是推荐同类目的其他商品
        int cid = goods.getCategoryId().intValue();
        int brandId = goods.getBrandId().intValue();

        //查找六个相关商品,同店铺，同类优先
        int limitBid = 10;
        List<YoungGoods> goodsList = clientGoodsService.queryByBrandId(brandId, cid, 1, limitBid);
        List<YoungGoods> relatedGoods = goodsList == null ? new ArrayList<>() : goodsList;
        // 同店铺，同类商品小于6件，则获取其他店铺同类商品
        if (goodsList == null || goodsList.size() < 6) {
            int limitCid = 6;
            List<YoungGoods> youngGoodsList = clientGoodsService.queryByCategoryAndNotSameBrandId(brandId, cid, 1, limitCid);
            relatedGoods.addAll(youngGoodsList);
        }


        Map<String, Object> data = new HashMap<>(2);
        data.put("goodsList", relatedGoods);

        return ResBean.success(data);
    }
}
