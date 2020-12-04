package com.young.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.young.db.dao.YoungGoodsMapper;
import com.young.db.entity.*;
import com.young.db.entity.YoungGoods.Column;
import com.young.db.mapper.GoodsMapper;
import com.young.mall.domain.ClientGoodsSpecificationVO;
import com.young.mall.exception.Asserts;
import com.young.mall.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Description: 客户端商品业务
 * @Author: yqz
 * @CreateDate: 2020/11/22 16:57
 */
@Service
public class ClientGoodsServiceImpl implements ClientGoodsService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YoungGoodsMapper youngGoodsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ClientCategoryService clientCategoryService;

    @Autowired
    private MallGoodsService mallGoodsService;

    @Autowired
    private MallGoodsProductService mallGoodsProductService;

    @Autowired
    private MallIssueService mallIssueService;

    @Autowired
    private MallBrandService mallBrandService;

    @Autowired
    private MallCommentService mallCommentService;

    @Autowired
    private ClientGoodsAttributeService clientGoodsAttributeService;

    @Autowired
    private ClientGoodsSpecificationService clientGoodsSpecificationService;

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private ClientGrouponRulesService clientGrouponRulesService;

    @Autowired
    private ClientCollectService clientCollectService;

    @Autowired
    private ClientFootPrintService clientFootPrintService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS,
            WORK_QUEUE, HANDLER);


    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot, Column.isNew,
            Column.counterPrice, Column.retailPrice};

    @Override
    public List<YoungGoods> queryByNew(int page, int size) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsNewEqualTo(true).andIsOnSaleEqualTo(true)
                .andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(page, size);
        List<YoungGoods> newGoodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return newGoodsList;
    }

    @Override
    public List<YoungGoods> queryByHot(int page, int size) {

        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("browse desc");

        PageHelper.startPage(page, size);
        List<YoungGoods> newGoodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return newGoodsList;
    }

    @Override
    public List<YoungGoods> getGoodByCategoryId(List<Integer> cid, int page, int size) {
        YoungGoodsExample example = new YoungGoodsExample();
        example.or().andCategoryIdIn(cid).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("sort_order  asc");
        PageHelper.startPage(page, size);
        List<YoungGoods> goodsList = youngGoodsMapper.selectByExampleSelective(example, columns);
        return goodsList;
    }

    @Override
    public Integer getGoodsCountOnSale() {
        YoungGoodsExample example = new YoungGoodsExample();
        example.createCriteria().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) youngGoodsMapper.countByExample(example);
    }

    @Override
    public Map<String, Object> getCategoryById(Integer id) {
        YoungCategory category = clientCategoryService.findById(id);

        YoungCategory parent = null;
        List<YoungCategory> children = null;
        //判断是否为一级分类
        if (category.getPid() == 0) {
            parent = category;
            children = clientCategoryService.getLeveSecondByPid(category.getId());
            //如果该一级分类对应的二级分类大于 0，当前分类则取第一个，否则，当前分类还是一级分类
            category = children.size() > 0 ? children.get(0) : category;
        } else {
            //非一级分类
            //先查出一级分类
            parent = clientCategoryService.findById(category.getPid());
            //再查出二级分类
            children = clientCategoryService.getLeveSecondByPid(category.getPid());
        }
        Map<String, Object> data = new HashMap<>();

        data.put("currentCategory", category);
        //用户显示页面title
        data.put("parentCategory", parent);
        data.put("brotherCategory", children);
        return data;
    }

    @Override
    public List<YoungGoods> querySelective(Integer catId, Integer brandId,
                                           String keywords, Boolean isHot,
                                           Boolean isNew, Integer page,
                                           Integer limit, String sort,
                                           String order) {
        YoungGoodsExample example = new YoungGoodsExample();
        //sql:select id , `name` , brief , pic_url , is_hot , is_new , counter_price , retail_price from young_goods WHERE ( category_id = ? and is_on_sale = ? and deleted = ? ) or( category_id = ? and is_on_sale = ? and deleted = ? ) order by sort_order asc LIMIT ?
        //参数：100100601(Integer), true(Boolean), false(Boolean), 100100601(Integer), true(Boolean), false(Boolean), 10(Integer)
        YoungGoodsExample.Criteria criteria1 = example.or();
        YoungGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }
        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return youngGoodsMapper.selectByExampleSelective(example, columns);
    }

    @Override
    public List<Integer> getCatIds(Integer brandId, String keywords, Boolean isHot, Boolean isNew) {
        YoungGoodsExample example = new YoungGoodsExample();
        YoungGoodsExample.Criteria criteria1 = example.or();
        YoungGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<YoungGoods> goodsList = youngGoodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (YoungGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    @Override
    public int addBrowse(Integer id, Short num) {
        int count = goodsMapper.addBrowse(id, num);
        return count;
    }

    @Override
    public Map<String, Object> details(Integer userId, Integer id) {

        Optional<YoungGoods> optional = mallGoodsService.findById(id);
        if (!optional.isPresent()) {
            Asserts.fail("查询失败");
        }
        // 商品信息
        YoungGoods goods = optional.get();

        // 商品属性
        Callable<List> goodsAttributeListCallable = () -> clientGoodsAttributeService.queryByGid(id);

        // 商品规格 返回的是定制的ClientGoodsSpecificationVO
        Callable<List> objectCallable = () -> clientGoodsSpecificationService.getSpecificationVoList(id);

        // 商品规格对应的数量和价格
        Callable<List> productListCallable = () -> mallGoodsProductService.queryByGoodsId(id).get();

        //商品问题，这里是一些通用问题
        Callable<List> issueCallable = () -> mallIssueService.query();

        // 商品品牌商
        Callable<YoungBrand> brandCallable = () -> {
            Integer brandId = goods.getBrandId();
            YoungBrand brand;
            if (brandId == 0) {
                brand = new YoungBrand();
            } else {
                brand = mallBrandService.findById(brandId).get();
            }
            return brand;
        };

        // 评论
        Callable<Map> commentsCallable = () -> {
            List<YoungComment> comments = mallCommentService.queryGoodsByGid(id, 0, 2);
            List<Map<String, Object>> commentsVo = new ArrayList<>(comments.size());

            for (YoungComment comment : comments) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", comment.getId());
                map.put("addTime", comment.getAddTime());
                map.put("content", comment.getContent());
                YoungUser user = clientUserService.findById(comment.getUserId());
                map.put("nickname", user.getNickname());
                map.put("avatar", user.getAvatar());
                map.put("picList", comment.getPicUrls());
                commentsVo.add(map);
            }
            Map<String, Object> commentList = new HashMap<>();
            commentList.put("count", PageInfo.of(comments).getTotal());
            commentList.put("data", commentsVo);
            return commentList;
        };

        // 团购信息
        String goodsSn = goods.getGoodsSn();
        Callable<List> grouponRulesCallable = () -> clientGrouponRulesService.queryByGoodsSn(goodsSn);
        // 用户收藏
        int userHasCollect = clientCollectService.count(userId, id);
        // 记录用户的足迹 异步处理
        executorService.execute(() -> {
            YoungFootprint footprint = new YoungFootprint();
            footprint.setUserId(userId);
            footprint.setGoodsId(id);
            clientFootPrintService.add(footprint);
            // 新增商品点击量
            short num = 1;
            this.addBrowse(id, num);//
        });


        FutureTask<List> goodsAttributeListTask = new FutureTask<>(goodsAttributeListCallable);
        FutureTask<List> objectCallableTask = new FutureTask<>(objectCallable);
        FutureTask<List> productListCallableTask = new FutureTask<>(productListCallable);
        FutureTask<List> issueCallableTask = new FutureTask<>(issueCallable);
        FutureTask<Map> commentsCallableTsk = new FutureTask<>(commentsCallable);
        FutureTask<YoungBrand> brandCallableTask = new FutureTask<>(brandCallable);
        FutureTask<List> grouponRulesCallableTask = new FutureTask<>(grouponRulesCallable);

        executorService.submit(goodsAttributeListTask);
        executorService.submit(objectCallableTask);
        executorService.submit(productListCallableTask);
        executorService.submit(issueCallableTask);
        executorService.submit(commentsCallableTsk);
        executorService.submit(brandCallableTask);
        executorService.submit(grouponRulesCallableTask);

        Map<String, Object> data = new HashMap<>(14);
        try {

            data.put("info", goods);
            data.put("userHasCollect", userHasCollect);
            data.put("issue", issueCallableTask.get());
            data.put("comment", commentsCallableTsk.get());
            data.put("specificationList", objectCallableTask.get());
            data.put("productList", productListCallableTask.get());
            data.put("attribute", goodsAttributeListTask.get());
            data.put("brand", brandCallableTask.get());
            data.put("groupon", grouponRulesCallableTask.get());
        } catch (Exception e) {
            logger.error("获取商品详情出错:{}", e.getMessage());
        }
        // 商品分享图片地址
        data.put("shareImage", goods.getShareUrl());

        return data;
    }
}
