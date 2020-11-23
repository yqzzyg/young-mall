package com.young.mall.service;

import com.young.db.entity.YoungCategory;
import com.young.db.pojo.CategoryAndGoodsPojo;

import java.util.List;
import java.util.Map;

/**
 * @Description: 客户端商品分类查询
 * @Author: yqz
 * @CreateDate: 2020/11/22 11:36
 */
public interface ClientCategoryService {

    /**
     * 查询一级分类
     *
     * @return
     */
    List<YoungCategory> queryLevelFirst();

    /**
     * 查询商品及其所属分类
     * 自定义sql，无法针对每个分类进行分页，导致首页无法展示所有的分类
     * 而是把一个分类的所有商品都展示了
     *
     * @return
     */
    List<CategoryAndGoodsPojo> getCategoryAndGoodsPojo(int page, int size);

    /**
     * 通过逐级查询商品分类，然后通过商品分类查询该分类的下属商品
     * 这样可以在首页展示出所有的分类，每个分类的展示商品数量根据分页数据展示
     *
     * @param page
     * @param size
     * @return
     */
    List<Map<String, Object>> getCategoryList(int page, int size);

    /**
     * 查询商品一级分类
     *
     * @param page
     * @param size
     * @return
     */
    List<YoungCategory> getLeveFirst(int page, int size);

    /**
     * 根据一级分类 id 查询该分类下的二级分类
     *
     * @param pid
     * @return
     */
    List<YoungCategory> getLeveSecondByPid(Integer pid);
}
