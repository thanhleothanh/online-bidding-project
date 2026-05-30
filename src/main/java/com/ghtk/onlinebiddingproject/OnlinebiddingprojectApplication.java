package com.ghtk.onlinebiddingproject;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlinebiddingprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinebiddingprojectApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "metanoia",
                "api_key", "899938948262375",
                "api_secret", "28mQ7ymH9cjf1lW5o1lN09gEsc4",
                "secure", true));
    }
}
