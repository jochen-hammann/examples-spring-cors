package com.example.spring.cors.cors.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class CorsConfig
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Value("${cors.enabled:false}")
    private boolean enabled;

    @Value("${cors.allowed-origins:#{null}}")
    private String[] allowedOrigins;

    @Value("${cors.allowed-methods:#{null}}")
    private String[] allowedMethods;

    @Value("${cors.allowed-headers:#{null}}")
    private String[] allowedHeaders;

    @Value("${cors.exposed-headers:#{null}}")
    private String[] exposedHeaders;

    @Value("${cors.allow-credentials:#{null}}")
    private Boolean allowCredentials;

    @Value("${cors.max-age:#{null}}")
    private Integer maxAge;

    // ============================== [Construction / Destruction] ==============================

    // -------------------- [Public Construction / Destruction] --------------------

    // ============================== [Spring Beans] ==============================

    // -------------------- [Public Spring Beans] --------------------

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        this.dumpSettings();

        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                if (CorsConfig.this.enabled)
                {
                    CorsRegistration corsRegistration = registry.addMapping("/**");

                    if (CorsConfig.this.allowedOrigins != null)
                        corsRegistration.allowedOrigins(CorsConfig.this.allowedOrigins);

                    if (CorsConfig.this.allowedMethods != null)
                        corsRegistration.allowedMethods(CorsConfig.this.allowedMethods);

                    if (CorsConfig.this.allowedHeaders != null)
                        corsRegistration.allowedHeaders(CorsConfig.this.allowedHeaders);

                    if (CorsConfig.this.exposedHeaders != null)
                        corsRegistration.exposedHeaders(CorsConfig.this.exposedHeaders);

                    if (CorsConfig.this.allowedOrigins != null)
                        corsRegistration.allowedOrigins(CorsConfig.this.allowedOrigins);

                    if (CorsConfig.this.maxAge != null)
                        corsRegistration.maxAge(CorsConfig.this.maxAge);
                }
            }
        };
    }

    // ============================== [Getter/Setter] ==============================

    // -------------------- [Private Getter/Setter] --------------------

    // -------------------- [Public Getter/Setter] --------------------

    // ============================== [Methods] ==============================

    // -------------------- [Private Methods] --------------------

    private String toString(String[] array)
    {
        if (array == null)
            return "null";

        return Arrays.asList(array).toString();
    }

    private void dumpSettings()
    {
        StringBuilder corsConfig = new StringBuilder();
        corsConfig.append("cors.enabled = ").append(this.enabled).append("\n");
        corsConfig.append("cors.allowed-origins = ").append(toString(this.allowedOrigins)).append("\n");
        corsConfig.append("cors.allowed-methods = ").append(toString(this.allowedMethods)).append("\n");
        corsConfig.append("cors.allowed-headers = ").append(toString(this.allowedHeaders)).append("\n");
        corsConfig.append("cors.exposed-headers = ").append(toString(this.exposedHeaders)).append("\n");
        corsConfig.append("cors.allow-credentials = ").append(this.allowCredentials).append("\n");
        corsConfig.append("cors.max-age = ").append(maxAge).append("\n");

        logger.info("CORS Config:\n{}", corsConfig);
    }

    // -------------------- [Public Methods] --------------------

}
