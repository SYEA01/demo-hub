package org.example.tree.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * @author: Rambo
 * @create: 2025-08-24 19:37
 * @desc:
 */
@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 为LocalDateTime配置序列化器
            builder.serializers(new LocalDateTimeSerializer(formatter));
            // 注册JavaTimeModule以支持Java 8日期时间类型
            builder.modules(new JavaTimeModule());
        };
    }
}
