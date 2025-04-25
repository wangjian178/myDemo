package com.wj.demo.test.security;

import com.wj.demo.core.system.config.property.SysConfigProperty;
import com.wj.demo.core.system.service.ILoginService;
import com.wj.demo.framework.baseContext.HandlerAdapter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @ClassName SecurityConfig
 * @Description: 鉴权配置
 * @Author: W.Jian
 * @CreateDate: 2025/4/14 17:50
 * @Version:
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Resource
    private SysConfigProperty sysConfigProperty;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private CorsFilter corsFilter;

    /**
     * 认证管理。     jwt的用户验证
     *
     * @param authConfig 鉴权配置
     * @throws Exception 抛出异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 密码加码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 也可用有参构造，取值范围是 4 到 31，默认值为 10。数值越大，加密计算越复杂
        return new BCryptPasswordEncoder(10);
    }

    /**
     * 认证提供者
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.getUserDetailsService());
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    /**
     * 获取用户详情服务
     */
    private UserDetailsService getUserDetailsService() {
        return HandlerAdapter.finderHandler(ILoginService.class, sysConfigProperty.getLoginHandler());
    }

    /**
     * 配置过滤器
     *
     * @param httpSecurity httpSecurity
     * @param introspector introspector
     * @throws Exception 抛出异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {

        // 添加JWT过滤器
        httpSecurity.addFilterBefore(this.jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(this.corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(this.corsFilter, LogoutFilter.class);

        return httpSecurity.build();
    }
}
