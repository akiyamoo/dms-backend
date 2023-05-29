package kg.eldar.dms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "POST", "GET", "PUT", "OPTIONS", "DELETE", "PATCH")
                        .allowedOrigins("*")
                        .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
                        .maxAge(3600);
            }
        };
    }
}
