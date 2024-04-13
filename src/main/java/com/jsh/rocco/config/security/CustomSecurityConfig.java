package com.jsh.rocco.config.security;

import com.jsh.rocco.services.securities.CustomUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@Log4j2
@Configuration
public class CustomSecurityConfig {

    private DataSource dataSource;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    CustomSecurityConfig(DataSource dataSource, CustomUserDetailsService customUserDetailsService){
        this.dataSource = dataSource;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers("/").permitAll()
                                .requestMatchers("layouts/layout").permitAll()
                                .requestMatchers("user/login").permitAll()
                                .requestMatchers("room/*").permitAll()
                                .requestMatchers("hotel/*").permitAll()
                                .requestMatchers("search").permitAll()
                                .requestMatchers("searchAvailableProperty").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                .loginPage("/user/login").defaultSuccessUrl("/")
                )
                .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                        .userDetailsService(customUserDetailsService)
                        .tokenRepository(persistentTokenRepository())
                        .tokenValiditySeconds(60 * 60))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

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
