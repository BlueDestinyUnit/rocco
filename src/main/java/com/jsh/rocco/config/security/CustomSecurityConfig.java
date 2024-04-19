package com.jsh.rocco.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.rocco.config.security.test.*;
//import com.jsh.rocco.services.securities.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final CustomAuthenticationSuccessHandler2 customAuthenticationSuccessHandler2;
    private final CustomAuthenticationFailureHandler2 customAuthenticationFailureHandler2;
    private final CustomLoginAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler accessDeniedHandler;








//    private final ObjectMapper objectMapper;
//    private final DataSource dataSource;
//    private final CustomUserDetailsService customUserDetailsService;
//    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//    private final AuthenticationConfiguration authenticationConfiguration;
//
//
//    @Autowired
//    public CustomSecurityConfig(ObjectMapper objectMapper, DataSource dataSource, CustomUserDetailsService customUserDetailsService,
//                                CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
//                                CustomAuthenticationFailureHandler customAuthenticationFailureHandler
//                                ,AuthenticationConfiguration authenticationConfiguration) {
//        this.objectMapper = objectMapper;
//        this.dataSource = dataSource;
//        this.customUserDetailsService = customUserDetailsService;
//        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
//        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
//        this.authenticationConfiguration = authenticationConfiguration;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomLoginAuthenticationEntryPoint customLoginAuthenticationEntryPoint) throws Exception {
//        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
//        AuthenticationManager authenticationManager = sharedObject.build();
//
//        return  http.csrf(AbstractHttpConfigurer::disable)
//                .authenticationManager(authenticationManager)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        authorizationManagerRequestMatcherRegistry.requestMatchers("/").permitAll()
//                                .requestMatchers("layouts/layout").permitAll()
//                                .requestMatchers("register").permitAll()
//                                .requestMatchers("user/login").permitAll()
//                                .requestMatchers("user/login/*").permitAll()
//                                .requestMatchers("user/login/test").permitAll()
//                                .requestMatchers("user/login/test2").permitAll()
//                                .requestMatchers("room/*").permitAll()
//                                .requestMatchers("hotel/*").permitAll()
//                                .requestMatchers("search").permitAll()
//                                .requestMatchers("searchAvailableProperty").permitAll()
//                                .requestMatchers("admin/room").permitAll()
//                                .requestMatchers("admin/room/").permitAll()
//                                .requestMatchers("favicon.ico").permitAll()
//                                .anyRequest().authenticated()
//                )
//
//                .formLogin(
//                        httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
//                                .loginPage("/user/login").defaultSuccessUrl("/")
//                                .loginProcessingUrl("/user/login/")
//                                .usernameParameter("email").passwordParameter("password")
//                                .successHandler(customAuthenticationSuccessHandler)
//                                .failureHandler(customAuthenticationFailureHandler)
//                                .permitAll()
//                                .disable()
//                )
//                .addFilterAt(
//                        this.abstractAuthenticationProcessingFilter(authenticationManager),
//                        UsernamePasswordAuthenticationFilter.class
//                )
//                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
//                        .userDetailsService(customUserDetailsService)
//                        .tokenRepository(persistentTokenRepository())
//                        .tokenValiditySeconds(60 * 60))
//                .build();
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/user/login/").authenticated()
                        .anyRequest().permitAll()
                )

                .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        return http.build();


    }

//    public AbstractAuthenticationProcessingFilter abstractAuthenticationProcessingFilter(final AuthenticationManager authenticationManager) {
//        return new LoginAuthenticationFilter(
//                "/user/login/",
//                authenticationManager
//        );
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(authenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler2);
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler2);

        // **
        customAuthenticationFilter.setSecurityContextRepository(
                new DelegatingSecurityContextRepository(
                        new RequestAttributeSecurityContextRepository(),
                        new HttpSessionSecurityContextRepository()
                ));

        return customAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }





//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//
//        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//
//        return daoAuthenticationProvider;
//    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {//AuthenticationManager 등록
//        DaoAuthenticationProvider provider = daoAuthenticationProvider();//DaoAuthenticationProvider 사용
//        provider.setPasswordEncoder(passwordEncoder());//PasswordEncoder로는 PasswordEncoderFactories.createDelegatingPasswordEncoder() 사용
//        return new ProviderManager(provider);
//    }
//
//    @Bean
//    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter() throws Exception {
//        JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordLoginFilter = new JsonUsernamePasswordAuthenticationFilter(objectMapper);
//        jsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
//        return jsonUsernamePasswordLoginFilter;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
//        repository.setDataSource(dataSource);
//        return repository;
//    }

    //정적 자원들을 스프링 시큐리티 적용에서 제외시키겠다
    //예) /css/style.css 호출시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (webSecurity) -> webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    // https://curiousjinan.tistory.com/entry/spring-boot-3-1-security-6-security-config-class-detail-2 참조
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "X-XSRF-token"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
