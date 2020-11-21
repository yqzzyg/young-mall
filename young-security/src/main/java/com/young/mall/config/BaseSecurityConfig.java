package com.young.mall.config;

import com.young.mall.component.JwtAuthenticationTokenFilter;
import com.young.mall.component.RestAuthenticationEntryPoint;
import com.young.mall.component.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * @Description:
 * @Author: yqz
 * @CreateDate: 2020/10/25 16:30
 */
public class BaseSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 允许基于选择匹配在资源级配置基于网络的安全性。
     * 以下示例将以/ admin /开头的网址限制为具有ADMIN角色的用户，并声明任何其他网址需要成功验证。
     * 也就是对角色的权限——所能访问的路径做出限制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问，功能只有对应权限的人才可以访问
        //请求授权的规则
        http.authorizeRequests()
                .antMatchers(
                        "/admin/auth/login",
                        "/doc.html",
                        "/swagger-resources/**",
                        "/swagger/**",
                        "/**/v2/api-docs",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.png",
                        "/**/*.ico",
                        "/webjars/springfox-swagger-ui/**")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS) //跨域请求会先进行一次options请求
                .permitAll()
                // 除了以上路径，其他都需要验证
                .anyRequest()
                .authenticated();
        // 禁用缓存
        http.headers().cacheControl();
        //防止网站攻击：get post
        http.csrf().disable();//关闭csrf跨站攻击，登出失败可能的原因

        //基于token所以不需要session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        http.authorizeRequests()
                .and()
                .formLogin()
                .and()
                .httpBasic();

    }

    /**
     * 用于通过允许AuthenticationProvider容易地添加来建立认证机制。
     * 也就是说用来记录账号，密码，角色信息。
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //此处把用户信息交给security校验
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * SpringSecurity5.0以后，密码需要加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
