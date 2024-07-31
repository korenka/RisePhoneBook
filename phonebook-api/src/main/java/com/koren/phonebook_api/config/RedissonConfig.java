package com.koren.phonebook_api.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress("redis://host.docker.internal:6379"); //If runnig outside of Docker (on your JVM) use 127.0.0.1:6379

        return Redisson.create(config);
    }
}
