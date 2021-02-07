package com.young.mall.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.young.db.entity.YoungCategory;
import com.young.db.entity.YoungGoods;
import com.young.db.pojo.SeckillPromotionProduct;
import com.young.db.pojo.SeckillPromotionRelationProduct;
import com.young.mall.common.ResBean;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.vo.SeckillGoodsListVo;
import com.young.mall.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 秒杀抢购（仿京东秒杀页面）
 * 首页---> 进入秒杀列表页---->顶部展示不同类别秒杀table--->不同类别下展示该类别下的不同时间段的秒杀
 * @Author: yqz
 * @CreateDate: 2021/2/1 12:21
 */
@Api(tags = "秒杀抢购ClientSecondsToKillController")
@RestController
@RequestMapping("/client/seckill")
public class ClientSecondsToKillController {


    @Resource
    private ClientSecondsToKillService killService;

    @Autowired
    private ClientGoodsService clientGoodsService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientSearchHistoryService clientSearchHistoryService;

    @Autowired
    private ClientCategoryService clientCategoryService;


    @ApiOperation("秒杀商品类别")
    @GetMapping("/category")
    public ResBean<Map<String, Object>> seckillPromotionCategory(Integer id) {

        Map<String, Object> category = killService.seckillPromotionCategory(id);
        return ResBean.success(category);
    }


    /**
     * 根据category查询秒杀时间段列表
     *
     * @param promotionId 秒杀活动id
     * @param page
     * @param size
     * @return
     */
    @ApiOperation("根据category查询秒杀时间段列表")
    @GetMapping("/list")
    public ResBean list(
            @RequestParam(value = "promotionId") Integer promotionId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        List<SeckillPromotionRelationProduct> list = killService.list(promotionId, page, size);
        Map<String, Object> data = new HashMap<>(2);

        data.put("promotionSession", list);
        return ResBean.success(data);
    }

    /**
     * 分页查询不同场次关联及商品信息
     *
     * @param promotionId        秒杀活动id
     * @param promotionSessionId 秒杀场次id
     * @param pageSize
     * @param pageNum
     * @return
     */
    @ApiOperation("分页查询不同场次关联及商品信息")
    @GetMapping("/listByDate")
    public ResBean<List<SeckillPromotionProduct>> listByDate(@RequestParam(value = "promotionId") Long promotionId,
                                                             @RequestParam(value = "promotionSessionId") Long promotionSessionId,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        List<SeckillPromotionProduct> listByDate = killService.listByDate(promotionId, promotionSessionId, pageSize, pageNum);
        return ResBean.success(listByDate);
    }

    /**
     * @param id 商品id
     * @return
     */
    @ApiOperation("秒杀商品详情")
    @GetMapping("/details")
    public ResBean details(@RequestParam("id") Integer id) {

        return ResBean.success(null);
    }

    @ApiOperation("获取系统时间")
    @GetMapping("/now")
    public ResBean now() {
        return ResBean.success(System.currentTimeMillis());
    }


    @ApiOperation("获取秒杀商品列表")
    @PostMapping("/goodsList")
    public ResBean goodsList(@RequestBody SeckillGoodsListVo vo) {

        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (BeanUtil.isEmpty(userInfo)) {
            return ResBean.unauthorized("请登录！");
        }

        // 查询列表数据
        List<YoungGoods> goodsList = clientGoodsService.querySelective(vo.getGoodsIds(), vo.getCategoryId(), vo.getBrandId(), vo.getKeyword(), null,
                null, vo.getPage(), vo.getSize(), vo.getSort(), vo.getOrder());
        // 查询商品所属类目列表id。
        List<Integer> catIds = clientGoodsService.getCatIds(vo.getBrandId(), vo.getKeyword(), vo.getIsHot(), vo.getIsNew());
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
}
