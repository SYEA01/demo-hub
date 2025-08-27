package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Taoao_
 * @create: 2025-08-26 16:15
 * @desc:
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor executor() {
        return new ThreadPoolExecutor(5,
                10,
                1000,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
