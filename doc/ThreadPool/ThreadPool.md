







# ThreadPool相关杂记

# **一、为什么要使用线程池？**

> “ **池化技术相比大家已经屡见不鲜了，线程池、数据库连接池、Http 连接池等等都是对这个思想的应用。池化技术的思想主要是为了减少每次获取资源的消耗，提高对资源的利用率。**

**线程池**提供了一种限制和管理资源（包括执行一个任务）。 每个**线程池**还维护一些基本统计信息，例如已完成任务的数量。

这里借用《Java 并发编程的艺术》提到的来说一下**使用线程池的好处**：

- **降低资源消耗**。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
- **提高响应速度**。当任务到达时，任务可以不需要的等到线程创建就能立即执行。
- **提高线程的可管理性**。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

## **1.1 线程池在实际项目的使用场景**

**线程池一般用于执行多个不相关联的耗时任务，没有多线程的情况下，任务顺序执行，使用了线程池的话可让多个不相关联的任务同时执行。**

# 二、线程池是否定义为公共的？

## **2.1 建议不同类别的业务用不同的线程池**

很多人在实际项目中都会有类似这样的问题：**我的项目中多个业务需要用到线程池，是为每个线程池都定义一个还是说定义一个公共的线程池呢？**

一般建议是不同的业务使用不同的线程池，配置线程池的时候根据当前业务的情况对当前线程池进行配置，因为不同的业务的并发以及对资源的使用情况都不同，重心优化系统性能瓶颈相关的业务。

# 三、构造位置

## 3.1 成员变量

当设置成成员变量时，需要手动：shutdown()

基本上很少使用局部变量声明的线程池，每次处理时都生成一个线程池再销毁，这个代价实在太大了，而且如果请求多的时候，可能同一时间创建大量的线程池，导致应用直接不可用。成员变量声明的线程池，若不是常用的服务模块，那感觉基本没有使用线程池的必要。

```java
 @Override
    public Map<String, Object> getIndexData(Integer userId) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //轮播图
        Callable<List<YoungAd>> bannerListCallable = () -> clientAdService.queryIndex();

        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);
        executorService.submit(bannerTask);

        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());

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

## 3.2 局部变量

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
   /**
     * corePoolSize 核心线程池大小；
     * maximumPoolSize 线程池最大容量大小；
     * keepAliveTime 线程池空闲时，线程存活的时间；
     * TimeUnit 时间单位；
     * ThreadFactory 线程工厂；
     * BlockingQueue任务队列；
     * RejectedExecutionHandler 线程拒绝策略；
     */
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

        FutureTask<List<YoungAd>> bannerTask = new FutureTask<>(bannerListCallable);

        executorService.submit(bannerTask);


        Map<String, Object> entity = new HashMap<>();
        try {
            entity.put("banner", bannerTask.get());


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

# **四、正确配置线程池参数**

## **4.1 常规操作**

很多人甚至可能都会觉得把线程池配置过大一点比较好！我觉得这明显是有问题的。就拿我们生活中非常常见的一例子来说：**并不是人多就能把事情做好，增加了沟通交流成本。你本来一件事情只需要 3 个人做，你硬是拉来了 6 个人，会提升做事效率嘛？我想并不会。** 线程数量过多的影响也是和我们分配多少人做事情一样，对于多线程这个场景来说主要是增加了**上下文切换**成本。不清楚什么是上下文切换的话，可以看我下面的介绍。

> “ 上下文切换：
> 多线程编程中一般线程的个数都大于 CPU 核心的个数，而一个 CPU 核心在任意时刻只能被一个线程使用，为了让这些线程都能得到有效执行，CPU 采取的策略是为每个线程分配时间片并轮转的形式。当一个线程的时间片用完的时候就会重新处于就绪状态让给其他线程使用，这个过程就属于一次上下文切换。概括来说就是：当前任务在执行完 CPU 时间片切换到另一个任务之前会先保存自己的状态，以便下次再切换回这个任务时，可以再加载这个任务的状态。**任务从保存到再加载的过程就是一次上下文切换**。
> 上下文切换通常是计算密集型的。也就是说，它需要相当可观的处理器时间，在每秒几十上百次的切换中，每次切换都需要纳秒量级的时间。所以，上下文切换对系统来说意味着消耗大量的 CPU 时间，事实上，可能是操作系统中时间消耗最大的操作。
> Linux 相比与其他操作系统（包括其他类 Unix 系统）有很多的优点，其中有一项就是，其上下文切换和模式切换的时间消耗非常少。

**类比于实现世界中的人类通过合作做某件事情，我们可以肯定的一点是线程池大小设置过大或者过小都会有问题，合适的才是最好。**

**如果我们设置的线程池数量太小的话，如果同一时间有大量任务/请求需要处理，可能会导致大量的请求/任务在任务队列中排队等待执行，甚至会出现任务队列满了之后任务/请求无法处理的情况，或者大量任务堆积在任务队列导致 OOM。这样很明显是有问题的！ CPU 根本没有得到充分利用。**

**但是，如果我们设置线程数量太大，大量线程可能会同时在争取 CPU 资源，这样会导致大量的上下文切换，从而增加线程的执行时间，影响了整体执行效率。**

有一个简单并且适用面比较广的公式：

- **CPU 密集型任务(N+1)：** 这种任务消耗的主要是 CPU 资源，可以将线程数设置为 N（CPU 核心数）+1，比 CPU 核心数多出来的一个线程是为了防止线程偶发的缺页中断，或者其它原因导致的任务暂停而带来的影响。一旦任务暂停，CPU 就会处于空闲状态，而在这种情况下多出来的一个线程就可以充分利用 CPU 的空闲时间。
- **I/O 密集型任务(2N)：** 这种任务应用起来，系统会用大部分的时间来处理 I/O 交互，而线程在处理 I/O 的时间段内不会占用 CPU 来处理，这时就可以将 CPU 交出给其它线程使用。因此在 I/O 密集型任务的应用中，我们可以多配置一些线程，具体的计算方法是 2N。

**如何判断是 CPU 密集任务还是 IO 密集任务？**

CPU 密集型简单理解就是利用 CPU 计算能力的任务比如你在内存中对大量数据进行排序。单凡涉及到网络读取，文件读取这类都是 IO 密集型，这类任务的特点是 CPU 计算耗费时间相比于等待 IO 操作完成的时间来说很少，大部分时间都花在了等待 IO 操作完成上。