package com.vnpay.isp.be.config;

import com.vnpay.isp.be.common.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/8/2020
 **/
@Configuration
public class AppConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.database}")
    private int redisDatabase;

    @Value("${app.api.timeout}")
    private int appTimeout;

    @Value("${isp.header.cookie.auth}")
    private String headerCookieAuth;

    @Value("${isp.query.access_token}")
    private String queryAccessToken;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration rsConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        if (!Utils.isNullOrEmpty(redisPassword)) {
            rsConfig.setPassword(redisPassword);
        }
        rsConfig.setDatabase(redisDatabase);
        return new LettuceConnectionFactory(rsConfig);
    }

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(appTimeout);
        simpleClientHttpRequestFactory.setReadTimeout(appTimeout);
        RestTemplate rest = new RestTemplate(simpleClientHttpRequestFactory);
        rest.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return rest;
    }

    @Bean
    public String headerCookieAuth() {
        return this.headerCookieAuth;
    }

    @Bean
    public String queryAccessToken() {
        return this.queryAccessToken;
    }
}
