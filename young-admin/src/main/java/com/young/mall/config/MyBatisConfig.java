package com.young.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: MyBatis配置
 * @Author: yqz
 * @CreateDate: 2020/10/25 17:18
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.young.db.dao","com.young.db.mapper","com.young.mall.mapper"})
public class MyBatisConfig {
}
