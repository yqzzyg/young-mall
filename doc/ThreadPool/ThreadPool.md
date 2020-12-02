# ThreadPool是设置为成员变量还是局部变量

## 一、成员变量

当设置成成员变量时，需要手动：shutdown()

基本上很少使用局部变量声明的线程池，每次处理时都生成一个线程池再销毁，这个代价实在太大了，而且如果请求多的时候，可能同一时间创建大量的线程池，导致应用直接不可用。成员变量声明的线程池，若不是常用的服务模块，那感觉基本没有使用线程池的必要。

```java
 @Override
    public Map<String, Object> getIndexData(Integer userId) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //轮播图
        Callable<List<YoungAd>> bannerListCallable = () -> clientAdService.queryIndex();
        //首页九宫格
        Callable<List<YoungCategory>> channelListCallable = () -> clientCategoryService.queryLevelFirst();
        //优惠券
        Callable<List<YoungCoupon>> couponListCallable;
        if (userId == null) {
            couponListCallable = () -> clientCouponService.queryList(0, 3);
        } else {
            couponListCallable = () -> clientCouponService.queryAvailableList(userId, 0, 3);
        }
        Callable<List<YoungArticle>> articleListCallable = () -> clientArticleService.queryList(0, 5, "add_time", "desc");
        //首页新品首发模块
        Callable<List<YoungGoods>> newGoodsListCallable = () -> clientGoodsService.queryByNew(0, SystemConfig.getNewLimit());
        //首页人气推荐
        Callable<List<YoungGoods>> hotGoodsListCallable = () -> clientGoodsService.queryByHot(0, SystemConfig.getHotLimit());
        //首页品牌厂商直供
        Callable<List<YoungBrand>> brandListCallable = () -> clientBrandService.queryBrand(0, SystemConfig.getBrandLimit());
        //团购专区
        Callable<List<Map<String, Object>>> grouponListCallable = () -> grouponRulesService.queryList(0, 6);
        //活动专场
        Callable<List<YoungTopic>> topicListCallable = () -> topicService.queryList(0, SystemConfig.getTopicLimit());
        //首页底部商品及其所属分类
        // Callable<List<CategoryAndGoodsPojo>> floorGoodsListCallable = () -> clientCategoryService.getCategoryAndGoodsPojo(0, SystemConfig.getCatlogListLimit());
        //此处分页为首页下半部分展示商品的商品分类，此时数据库只要8类商品，分10为展示所有，PageHelper起始页默认值 1。
        Callable<List<Map<String, Object>>> floorGoodsListCallable = () -> clientCategoryService.getCategoryList(1, SystemConfig.getCatlogListLimit());


        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List<YoungCategory>> channelTask = new FutureTask<>(channelListCallable);
        FutureTask<List<YoungCoupon>> couponListTask = new FutureTask<>(couponListCallable);
        FutureTask<List<YoungArticle>> articleTask = new FutureTask<>(articleListCallable);
        FutureTask<List<YoungGoods>> newGoodsListTask = new FutureTask<>(newGoodsListCallable);
        FutureTask<List<YoungGoods>> hotGoodsListTask = new FutureTask<>(hotGoodsListCallable);
        FutureTask<List<YoungBrand>> brandListTask = new FutureTask<>(brandListCallable);

        FutureTask<List<Map<String, Object>>> grouponListTask = new FutureTask<>(grouponListCallable);
        FutureTask<List<YoungTopic>> topicListTask = new FutureTask<>(topicListCallable);
        FutureTask<List<Map<String, Object>>> floorGoodsListTask = new FutureTask<>(floorGoodsListCallable);

        executorService.submit(bannerTask);
        executorService.submit(channelTask);
        executorService.submit(couponListTask);
        executorService.submit(articleTask);
        executorService.submit(newGoodsListTask);
        executorService.submit(hotGoodsListTask);
        executorService.submit(brandListTask);
        executorService.submit(grouponListTask);
        executorService.submit(topicListTask);
        executorService.submit(floorGoodsListTask);


        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            entity.put("couponList", couponListTask.get());
            entity.put("articles", articleTask.get());
            entity.put("newGoodsList", newGoodsListTask.get());
            entity.put("hotGoodsList", hotGoodsListTask.get());
            entity.put("brandList", brandListTask.get());
            entity.put("grouponList", grouponListTask.get());
            entity.put("topicList", topicListTask.get());
            entity.put("floorGoodsList", floorGoodsListTask.get());

            //后续可以添加缓存数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果线程池使用成员变量，则不用每次shutdown
            executorService.shutdown();
        }
        return entity;
    }

```

## 二、局部变量

成员变量的线程池,如何保证在应用停止时,正常的线程任务都执行完, 我想到的时若是spring 声明的 bean,可以在bean destroy时调用 pool.shutdown, 但若应用是异常停止(比如kill),如何保证

解决：

```java
      Runtime.getRuntime().addShutdownHook(new Thread() {
          @Override
          public void run() {
             // do something when jvm shutdown
          }
      });
```

项目中样例：

```java
private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();
    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(16, 16, 1000, TimeUnit.MILLISECONDS,
            WORK_QUEUE, HANDLER);

    @Override
    public Map<String, Object> getIndexData(Integer userId) {

        /**
         * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明：Executors返回的线程池对象的弊端如下：
         * 1）FixedThreadPool和SingleThreadPool:
         * 允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
         * 2）CachedThreadPool:
         * 允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。
         */
        //ExecutorService executorService = Executors.newFixedThreadPool(10);
        //轮播图
        Callable<List<YoungAd>> bannerListCallable = () -> clientAdService.queryIndex();
        //首页九宫格
        Callable<List<YoungCategory>> channelListCallable = () -> clientCategoryService.queryLevelFirst();
        //优惠券
        Callable<List<YoungCoupon>> couponListCallable;
        if (userId == null) {
            couponListCallable = () -> clientCouponService.queryList(0, 3);
        } else {
            couponListCallable = () -> clientCouponService.queryAvailableList(userId, 0, 3);
        }
        Callable<List<YoungArticle>> articleListCallable = () -> clientArticleService.queryList(0, 5, "add_time", "desc");
        //首页新品首发模块
        Callable<List<YoungGoods>> newGoodsListCallable = () -> clientGoodsService.queryByNew(0, SystemConfig.getNewLimit());
        //首页人气推荐
        Callable<List<YoungGoods>> hotGoodsListCallable = () -> clientGoodsService.queryByHot(0, SystemConfig.getHotLimit());
        //首页品牌厂商直供
        Callable<List<YoungBrand>> brandListCallable = () -> clientBrandService.queryBrand(0, SystemConfig.getBrandLimit());
        //团购专区
        Callable<List<Map<String, Object>>> grouponListCallable = () -> grouponRulesService.queryList(0, 6);
        //活动专场
        Callable<List<YoungTopic>> topicListCallable = () -> topicService.queryList(0, SystemConfig.getTopicLimit());
        //首页底部商品及其所属分类
        // Callable<List<CategoryAndGoodsPojo>> floorGoodsListCallable = () -> clientCategoryService.getCategoryAndGoodsPojo(0, SystemConfig.getCatlogListLimit());
        //此处分页为首页下半部分展示商品的商品分类，此时数据库只要8类商品，分10为展示所有，PageHelper起始页默认值 1。
        Callable<List<Map<String, Object>>> floorGoodsListCallable = () -> clientCategoryService.getCategoryList(1, SystemConfig.getCatlogListLimit());


        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);
        FutureTask<List<YoungCategory>> channelTask = new FutureTask<>(channelListCallable);
        FutureTask<List<YoungCoupon>> couponListTask = new FutureTask<>(couponListCallable);
        FutureTask<List<YoungArticle>> articleTask = new FutureTask<>(articleListCallable);
        FutureTask<List<YoungGoods>> newGoodsListTask = new FutureTask<>(newGoodsListCallable);
        FutureTask<List<YoungGoods>> hotGoodsListTask = new FutureTask<>(hotGoodsListCallable);
        FutureTask<List<YoungBrand>> brandListTask = new FutureTask<>(brandListCallable);

        FutureTask<List<Map<String, Object>>> grouponListTask = new FutureTask<>(grouponListCallable);
        FutureTask<List<YoungTopic>> topicListTask = new FutureTask<>(topicListCallable);
        FutureTask<List<Map<String, Object>>> floorGoodsListTask = new FutureTask<>(floorGoodsListCallable);

        executorService.submit(bannerTask);
        executorService.submit(channelTask);
        executorService.submit(couponListTask);
        executorService.submit(articleTask);
        executorService.submit(newGoodsListTask);
        executorService.submit(hotGoodsListTask);
        executorService.submit(brandListTask);
        executorService.submit(grouponListTask);
        executorService.submit(topicListTask);
        executorService.submit(floorGoodsListTask);


        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());
            entity.put("channel", channelTask.get());
            entity.put("couponList", couponListTask.get());
            entity.put("articles", articleTask.get());
            entity.put("newGoodsList", newGoodsListTask.get());
            entity.put("hotGoodsList", hotGoodsListTask.get());
            entity.put("brandList", brandListTask.get());
            entity.put("grouponList", grouponListTask.get());
            entity.put("topicList", topicListTask.get());
            entity.put("floorGoodsList", floorGoodsListTask.get());

            //后续可以添加缓存数据
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果线程池使用成员变量，则不用每次shutdown
            //executorService.shutdown();
        }
        return entity;
    }
```

