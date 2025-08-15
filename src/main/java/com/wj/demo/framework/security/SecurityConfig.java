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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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

    /**
     * jwt过滤器
     */
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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

    // 新增 CORS 配置
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 生产环境应指定具体域名
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of(RequestMethod.GET.name(), RequestMethod.POST.name(), RequestMethod.PUT.name(), RequestMethod.DELETE.name(), RequestMethod.OPTIONS.name()));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                // CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 关闭CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 异常处理
                .exceptionHandling(e -> e.authenticationEntryPoint(new MyAuthEntryPoint()))
                // 无状态会话
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 授权配置 - 使用现代API
                .authorizeHttpRequests(authorize -> authorize
                        // 1. 静态资源和API端点
                        .requestMatchers(
                                "/login",
                                "/logout",
                                "/register",
                                "/captchaImage",
                                "/favicon.ico"
                        ).permitAll()

                        // 2. 使用路径模式匹配
                        .requestMatchers("/sso/**").permitAll()
                        .requestMatchers("/wechat/**").permitAll()
                        .requestMatchers("/websocket/**").permitAll()
                        .requestMatchers("/xxlJobAdmin/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/springBootAdmin/**").permitAll()
                        .requestMatchers("/doc.html").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/druid/**").permitAll()

                        // 3. 静态资源匹配
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/profile/**").permitAll()
                        .requestMatchers("/*.html").permitAll()
                        .requestMatchers("/*.css").permitAll()
                        .requestMatchers("/*.js").permitAll()
                        .requestMatchers("/*.ico").permitAll()
                        .requestMatchers("/*.otf").permitAll()
                        .requestMatchers("/*.eot").permitAll()
                        .requestMatchers("/*.svg").permitAll()
                        .requestMatchers("/*.ttf").permitAll()
                        .requestMatchers("/*.woff").permitAll()
                        .requestMatchers("/*.woff2").permitAll()

                        // 4. 动态排除的URL
                        .requestMatchers(
                                systemProperties.getSecurity().getAuth().getExclude().toArray(String[]::new)
                        ).permitAll()
                        // 5. 匿名访问
                        .requestMatchers(
                                anonymousAccessUrlProvider.getIgnoreUrls().toArray(String[]::new)
                        ).permitAll()
                        // 6. 所有其他请求需要认证
                        .anyRequest().authenticated()
                )
                // 禁用X-Frame-Options（允许iframe嵌入）
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // 注销配置
                .logout(l -> l
                        .logoutUrl(systemProperties.getSecurity().getLogoutUrl())
                        .logoutSuccessUrl(systemProperties.getSecurity().getLoginPage())
                        .addLogoutHandler(new MyLogoutHandler())
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
                )

                // 会话管理
                .sessionManagement(session -> session
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
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilter(this.switchUserFilter());

        //配置表单登陆
/*        httpSecurity.formLogin(f -> f
                .loginPage(systemProperties.getSecurity().getLoginPage())
                .loginProcessingUrl(systemProperties.getSecurity().getLoginUrl())
                .successHandler(new MyAuthSuccessHandler())
                .failureHandler(new MyAuthFailureHandler())
        );*/

        return httpSecurity.build();
    }

}
