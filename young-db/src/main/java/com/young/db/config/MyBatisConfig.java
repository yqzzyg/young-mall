package com.young.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: MyBatis配置，注意持久层接口所在包，该配置放到young-admin无效
 * @Author: yqz
 * @CreateDate: 2020/11/4 10:18
 */
@Configuration
@MapperScan(basePackages = {"com.young.db.mapper","com.young.db.dao"})
@EnableTransactionManagement
public class MyBatisConfig {
}
