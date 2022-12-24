package com.ghtk.onlinebiddingproject.configurations;


import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("https://auctiontime.netlify.app", "http://localhost:3000", "http://127.0.0.1:3000", "https://onlinebiddingproject.netlify.app", "http://20.172.145.151:80", "http://20.172.145.151:3000", "http://20.172.145.151:443", "https://20.172.145.151:80", "https://20.172.145.151:3000", "https://20.172.145.151:443").allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true).exposedHeaders("Set-Cookie");
    }
}
