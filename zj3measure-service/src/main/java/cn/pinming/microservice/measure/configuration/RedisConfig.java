package cn.pinming.microservice.measure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate setRedisTemplate(RedisTemplate redisTemplate){
//        redisTemplate.setKeySerializer(RedisSerializer.string());
        //redisTemplate.setValueSerializer(RedisSerializer.string());
//        redisTemplate.setHashKeySerializer(RedisSerializer.string());
//        redisTemplate.setHashValueSerializer(RedisSerializer.string());
        return redisTemplate;
    }
}
