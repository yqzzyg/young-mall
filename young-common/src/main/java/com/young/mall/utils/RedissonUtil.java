//package com.young.mall.utils;
//
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Description:
// * @Author: yqz
// * @CreateDate: 2020/10/26 14:54
// */
//@Component
//public class RedissonUtil {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    //新增-字符串
//    public void setStr(String key, String msg, long seconds) {
//        if (seconds > 0) {
//            redissonClient.getBucket(key).set(msg, seconds, TimeUnit.SECONDS);
//        } else {
//            redissonClient.getBucket(key).set(msg);
//        }
//    }
//
//    //新增--Hash
//    public void setHash(String key, Map<String, Object> map) {
//        redissonClient.getMap(key).putAll(map);
//    }
//
//    //新增-List
//    public void setList(String key, List<Object> list) {
//        redissonClient.getList(key).addAll(list);
//    }
//
//    //新增Set
//    public void setSet(String key, Set<Object> set) {
//        redissonClient.getSet(key).addAll(set);
//    }
//
//    //新增ZSet
//    public void setZset(String key, Map<Object, Double> map) {
//        redissonClient.getScoredSortedSet(key).addAll(map);
//    }
//
//    //删除
//    public void removeStr(String key) {
//        redissonClient.getKeys().delete(key);
//    }
//
//    //删除-Hash中的某个元素
//    public void removeHash(String key, String field) {
//        redissonClient.getMap(key).remove(field);
//    }
//
//    //查询
//    public String getStr(String key) {
//        return (String) redissonClient.getBucket(key).get();
//    }
//
//    //查询集合
//    public List<Object> getList(String key) {
//        return redissonClient.getList(key).readAll();
//    }
//
//    //其他
//    //设置有效期
//    public void expire(String key, long seconds) {
//        redissonClient.getKeys().expire(key, seconds, TimeUnit.SECONDS);
//    }
//
//    //校验指定的Key存在
//    public boolean checkKey(String key) {
//        return redissonClient.getKeys().countExists(key) > 0;
//    }
//
//    //setnx
//    //加锁,
//    public RLock lock(String key) {
//        RLock rLock = redissonClient.getLock(key);
//        return rLock;
//    }
//
//    //解锁
//    public void unlock(RLock lock) {
//        lock.unlock();
//    }
//
//    /**
//     * 获取锁，如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。
//     *
//     * @param lockKey
//     */
//    public void getLock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock();
//    }
//
//    ;
//
//    /**
//     * 释放锁
//     *
//     * @param lockKey
//     */
//    public void unlock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.unlock();
//    }
//
//    ;
//
//    /**
//     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
//     *
//     * @param lockKey
//     * @param timeout
//     */
//    public void lock(String lockKey, int timeout) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock(timeout, TimeUnit.SECONDS);
//    }
//
//    ;
//
//    /**
//     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
//     *
//     * @param lockKey
//     * @param unit
//     * @param timeout
//     */
//    public void lock(String lockKey, TimeUnit unit, int timeout) {
//        RLock lock = redissonClient.getLock(lockKey);
//        lock.lock(timeout, unit);
//    }
//
//    ;
//
//    /**
//     * 尝试获取锁，获取到立即返回true,未获取到立即返回false
//     *
//     * @param lockKey
//     * @return
//     */
//    public boolean tryLock(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        return lock.tryLock();
//    }
//
//    ;
//
//    /**
//     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,如果获取到锁，则要么执行完后程序释放锁，
//     * 要么在给定的超时时间leaseTime后释放锁
//     *
//     * @param lockKey
//     * @param waitTime
//     * @param leaseTime
//     * @param unit
//     * @return
//     */
//    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit)
//            throws InterruptedException {
//        RLock lock = redissonClient.getLock(lockKey);
//        return lock.tryLock(waitTime, leaseTime, unit);
//    }
//
//    ;
//
//    /**
//     * 锁是否被任意一个线程锁持有
//     *
//     * @param lockKey
//     * @return
//     */
//    public boolean isLocked(String lockKey) {
//        RLock lock = redissonClient.getLock(lockKey);
//        return lock.isLocked();
//    }
//
//    ;
//}
