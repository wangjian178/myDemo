package com.wj.demo.framework.security;

import com.wj.demo.framework.common.constant.BaseConstant;
import com.wj.demo.framework.common.property.SystemProperties;
import com.wj.demo.framework.security.handler.MyAuthSuccessHandler;
import com.wj.demo.framework.security.handler.MyLogoutHandler;
import com.wj.demo.framework.security.handler.MyLogoutSuccessHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * @ClassName SecurityConfig
 * @Description: Security配置
 * @Author: W.Jian
 * @CreateDate: 2025/4/15 15:07
 * @Version:
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private SystemProperties systemProperties;

    @Resource
    private AnonymousAccessUrlProvider anonymousAccessUrlProvider;

    @Resource(name = "ILoginService" + BaseConstant.UNDERLINE + BaseConstant.DEFAULT)
    private UserDetailsService userDetailsService;

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
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(this.passwordEncoder());
        return authProvider;
    }

    /**
     * 切换用户过滤器
     */
    @Bean
    public SwitchUserFilter switchUserFilter() {
        SwitchUserFilter switchUserFilter = new SwitchUserFilter();
        switchUserFilter.setUserDetailsService(this.userDetailsService);
        switchUserFilter.setUsernameParameter("switch_username_parameter_demo");
        switchUserFilter.setSwitchUserUrl("/switch_user_demo");
        switchUserFilter.setSuccessHandler(new MyAuthSuccessHandler());
        return switchUserFilter;
    }

    /**
     * jwt过滤器
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //关闭CSRF（跨站请求伪造）是一种网络攻击，攻击者通过欺骗已登录用户，诱使他们在不知情的情况下向受信任的网站发送请求
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(new MyAuthEntryPoint()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //开启授权保护，配置请求授权规则
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(new HandlerMappingIntrospector());
        httpSecurity.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(
                                new RequestMatcher[]{
                                        mvcMatcherBuilder.pattern("/login"),
                                        mvcMatcherBuilder.pattern("/logout"),
                                        mvcMatcherBuilder.pattern("/sso/**"),
                                        mvcMatcherBuilder.pattern("/register"),
                                        mvcMatcherBuilder.pattern("/wechat/**"),
                                        mvcMatcherBuilder.pattern("/captchaImage"),
                                        mvcMatcherBuilder.pattern("/websocket/**"),
                                        mvcMatcherBuilder.pattern("/xxlJobAdmin/**"),
                                        mvcMatcherBuilder.pattern("/actuator/**"),
                                        mvcMatcherBuilder.pattern("/springBootAdmin/**"),
                                        mvcMatcherBuilder.pattern("favicon.ico"),
                                        mvcMatcherBuilder.pattern("/doc.html"),
                                        mvcMatcherBuilder.pattern("/webjars/**"),
                                        mvcMatcherBuilder.pattern("/swagger-resources/**"),
                                        mvcMatcherBuilder.pattern("/swagger-ui/**"),
                                        mvcMatcherBuilder.pattern("/v3/api-docs/**"),
                                        AntPathRequestMatcher.antMatcher("/druid/*")
                                }
                        ).permitAll()
                        .requestMatchers(
                                new RequestMatcher[]{
                                        AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS),
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/static/**"),
                                        AntPathRequestMatcher.antMatcher("/*.html"),
                                        AntPathRequestMatcher.antMatcher("/*.ico"),
                                        AntPathRequestMatcher.antMatcher("/**/*.html"),
                                        AntPathRequestMatcher.antMatcher("/**/*.css"),
                                        AntPathRequestMatcher.antMatcher("/**/*.js"),
                                        AntPathRequestMatcher.antMatcher("/profile/**"),
                                        AntPathRequestMatcher.antMatcher("/**/*.otf"),
                                        AntPathRequestMatcher.antMatcher("/**/*.eot"),
                                        AntPathRequestMatcher.antMatcher("/**/*.svg"),
                                        AntPathRequestMatcher.antMatcher("/**/*.ttf"),
                                        AntPathRequestMatcher.antMatcher("/**/*.woff"),
                                        AntPathRequestMatcher.antMatcher("/**/*.woff2")
                                }
                        ).permitAll()
                        .requestMatchers(
                                systemProperties.getSecurity().getAuth().getExclude().toArray(String[]::new)
                        ).permitAll()
                        .requestMatchers(
                                anonymousAccessUrlProvider.getIgnoreUrls().toArray(String[]::new)
                        ).permitAll()
                        .anyRequest()
                        .access(new AuthenticatedAuthorizationManager<>())
        ).headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        //配置表单登陆
/*        httpSecurity.formLogin(f -> f
                .loginPage(systemProperties.getSecurity().getLoginPage())
                .loginProcessingUrl(systemProperties.getSecurity().getLoginUrl())
                .successHandler(new MyAuthSuccessHandler())
                .failureHandler(new MyAuthFailureHandler())
        );*/

        // 配置注销功能
        httpSecurity.logout(
                l -> l
                        .logoutUrl(systemProperties.getSecurity().getLogoutUrl())
                        .logoutSuccessUrl(systemProperties.getSecurity().getLoginPage())
                        .addLogoutHandler(new MyLogoutHandler())
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
        );

        // 配置会话管理器
        httpSecurity.sessionManagement(session -> session
                // 防止会话固定攻击
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::changeSessionId)
                // 限制每个用户只能有一个活跃会话
                .maximumSessions(1)
                // 如果为 true，禁止新登录；为 false，允许新登录并终止旧会话
                .maxSessionsPreventsLogin(Boolean.FALSE)
                // 当会话过期时跳转到的页面
                .expiredUrl("/login?session=expired")
        );

        // 添加JWT过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.addFilter(this.switchUserFilter());
        return httpSecurity.build();
    }

}
