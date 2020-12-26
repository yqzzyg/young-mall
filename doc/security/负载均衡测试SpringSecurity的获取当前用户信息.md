# 负载均衡测试SpringSecurity是否可以获取当前用户信息

## 一、目前程序中获取用户方式

由于程序中很多地方需要获取当前在线的用户信息，目前的做法是：

```java
    @Override
    public AdminUser getUserInfo() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //判断当前是否用户是否在线
        if (principal instanceof AdminUser) {
            AdminUser user = (AdminUser) principal;
            return user;
        }
        return null;
    }
```

这里是从`SecurityContextHolder.getContext().getAuthentication()`的上下文中获取，但是目前有个疑问？

**该方式如果在集群中是否可用？**

## **二、测试方式**

发现疑问，解决疑问。



在`idea`中启动两个实例，通过Nginx负载均衡，代理两个实例

**默认的 8083**

![admin8083](jpg\admin8083.png)

**添加一个8084端口的实例**

![admin8084](jpg\admin8084.png)

**同时启动两个实例**

## 三、配置Nginx

Nginx官网下载windows版本的Ng

http://nginx.org/en/download.html

![nginx目录](jpg\nginx目录.png)



打开conf目录下的`nginx.conf`进行简单的配置，配置如下

```

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {

	   upstream young-mall{

		server 127.0.0.1:8083  weight=10;	
        server 127.0.0.1:8084  weight=10;
 
    }
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       8080;
        server_name  localhost;
		large_client_header_buffers 4 16k;
        client_max_body_size 30m;
        client_body_buffer_size 128k;
        proxy_connect_timeout 300;
        proxy_read_timeout 300;
        proxy_send_timeout 300;
        proxy_buffer_size 64k;
        proxy_buffers   4 32k;
        proxy_busy_buffers_size 64k;
        proxy_temp_file_write_size 64k;
        #charset koi8-r;

        #access_log  logs/host.access.log  main;
        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        #location / {
        #    root   html;
        #    index  index.html index.htm;
        #}
        #此处的 /mall/ 上下文，在请求url中添加，由于本次测试使用的swagger，所以直接使用  /
		#location /mall/ {
		#	proxy_ignore_client_abort   on; 
		#	proxy_pass http://young-mall/;
        #    proxy_set_header   Host             $host:$server_port;
        #    proxy_set_header   X-Real-IP        $remote_addr;
        #    proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
        #    client_max_body_size 100m;
        #    proxy_buffering off;
        #    proxy_redirect off;
        #    proxy_read_timeout 120;
        #    proxy_connect_timeout 120;
        #}
        
        location / {
			proxy_ignore_client_abort   on; 
			proxy_pass http://young-mall/;
            proxy_set_header   Host             $host:$server_port;
            proxy_set_header   X-Real-IP        $remote_addr;
            proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
            client_max_body_size 100m;
            proxy_buffering off;
            proxy_redirect off;
            proxy_read_timeout 120;
            proxy_connect_timeout 120;
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

    }
}
```

双击Nginx目录的`nginx.exe` 可执行文件，保证程序启动，如果启动失败，可查看log目录下的日志文件

在浏览器中输入：http://127.0.0.1:8080/demo/doc.html

即可打开swagger在线文档

![swagger-home](jpg\swagger-home.png)

调用`login`接口：

![swagger-login](jpg\swagger-login.png)

将获取的token放入Authorize中：

![swagger-authorize](jpg\swagger-authorize.png)



多次执行获取当前用户接口，查看控制台日志输出：

![swagger-info](jpg\swagger-info.png)

第一次：

![swagger-info1](jpg\swagger-info1.png)

第二次：

![swagger-info2](jpg\swagger-info2.png)



由于使用的负载均衡策略的weight值相同，所以两次请求分别落到两个实例中，可见未出现`无权限`的情况。

## 四、更改过滤器配置



```java
package com.young.mall.component;

import com.young.mall.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: JWT登录授权过滤器
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:35
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(this.tokenHeader);
        if (header != null && header.startsWith(this.tokenHead)) {
            String token = header.substring(this.tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(token);
            logger.error("filter中解析的用户名:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.error("已认证的用户：{}", username);
                    //重新把认证添加到上下文中，即使部署集群，由于采用的是jwt，所有不管每次用户请求到哪个应用，都会有认证
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

}

```

注释掉` SecurityContextHolder.getContext().setAuthentication(authentication)`

![swagger-注释](jpg\swagger-注释.png)



## 五、结论

采用`jwt`做无状态token，每次请求都会经过`JwtAuthenticationTokenFilter`，此处每次会根据jwt解析出用户名，然后根据用户名查询该用户信息，查询成功之后 重新把认证添加到上下文中，所以及时是负载均衡请求，也不会影响。
