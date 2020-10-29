package com.young.mall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 基础类
 * @Author: yqz
 * @CreateDate: 2020/10/29 15:38
 */
@RestController
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
