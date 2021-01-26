package com.young.mall.config.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.young.mall.domain.ClientUserDetails;
import com.young.mall.domain.MallLog;
import com.young.mall.rabbitMQ.LogMsgSender;
import com.young.mall.service.ClientUserService;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户端日志输出
 * @Author: yqz
 * @CreateDate: 2020/11/23 14:41
 */
@Aspect
@Component
public class ClientLogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClientUserService clientUserService;

    @Autowired
    private LogMsgSender logMsgSender;

    @Pointcut("execution(public * com.young.mall.controller.*.*(..)) || execution(public * com.young.mall.*.controller.*.*(..))")
    public void clientLog() {

    }

    @Before("clientLog()")
    public void doBefore() {

    }

    @AfterReturning(value = "clientLog()", returning = "ret")
    public void doAfterReturning(Object ret) {

    }

    @Around("clientLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求的对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //记录请求信息，可用固化到数据库或者ELK
        MallLog mallLog = new MallLog();
        Object result = joinPoint.proceed();


        //以下为环绕返回阶段
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            mallLog.setDescription(log.value());
        }

        Object parameter = getParameter(method, joinPoint.getArgs());
        ClientUserDetails userInfo = clientUserService.getUserInfo();
        if (!BeanUtil.isEmpty(userInfo)) {
            mallLog.setUsername(userInfo.getUsername());
            mallLog.setUserId(userInfo.getYoungUser().getId());
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        mallLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.getPath(urlStr)));
        mallLog.setIp(request.getRemoteUser());
        mallLog.setMethod(request.getMethod());
        mallLog.setParameter(parameter);
        mallLog.setResult(result);
        mallLog.setSpendTime(((int) (endTime - startTime)));
        mallLog.setStartTime(startTime);
        mallLog.setUri(request.getRequestURI());
        mallLog.setUrl(request.getRequestURL().toString());

        logMsgSender.sendLogMessage(mallLog);

        Map<String, Object> logMap = new HashMap<>(8);
        logMap.put("url", mallLog.getUrl());
        logMap.put("method", mallLog.getMethod());
        logMap.put("parameter", mallLog.getParameter());
        logMap.put("spendTime", mallLog.getSpendTime());
        logMap.put("description", mallLog.getDescription());
        logger.error("客户端接口切面日志：{}", JSONUtil.toJsonStr(mallLog));
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
