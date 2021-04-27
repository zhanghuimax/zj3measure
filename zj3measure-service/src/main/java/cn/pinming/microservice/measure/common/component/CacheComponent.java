package cn.pinming.microservice.measure.common.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by jin on 2020-03-10.
 */
@Component
@Slf4j
public class CacheComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;



    public <T> T get(String key,Class<T> clazz) {
        String result;
        try {
            result = stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("获取缓存失败,返回null", e);
            return null;
        }
        if (result == null) {
            if (log.isInfoEnabled()) {
                log.info("没有获取缓存，key={}", key);
            }
            return null;
        } else {
            try {
                return objectMapper.readValue(result, clazz);
            } catch (JsonProcessingException e) {
                if (log.isErrorEnabled()) {
                    log.error("缓存转为对象失败", e);
                }
            }
            return null;
        }
    }

    public <T> T get(Cache<T> cache) {
        String key = cache.key();
        if (log.isInfoEnabled()) {
            log.info("尝试去获取缓存，key={}", key);
        }
        String result = null;
        try {
            result = stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("获取缓存失败,使用真实值", e);
        }
        if (result == null) {
            T value = cache.value();
            if (value == null) {
                if (log.isWarnEnabled()) {
                    log.warn("{}真实数据为空，不设置缓存", key);
                }
                return null;
            } else {
                if (!cache.needCached(value)) {
                    if (log.isInfoEnabled()) {
                        log.info("缓存不存在，得到真实数据={}，但是不符合缓存条件，不缓存", value.toString());
                    }
                    return value;
                }
                if (log.isInfoEnabled()) {
                    log.info("缓存不存在，得到真实数据={}，并设置缓存key={}", value.toString(), key);
                }
                String jsonValue = null;
                try {
                    jsonValue = objectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    if (log.isErrorEnabled()) {
                        log.error("对象转换json进入缓存失败", e);
                    }
                }
                if (jsonValue != null) {
                    Long second = cache.timeSecond();
                    if (second != null) {
                        stringRedisTemplate.opsForValue().set(key, jsonValue, second, TimeUnit.SECONDS);
                    } else {
                        stringRedisTemplate.opsForValue().set(key, jsonValue);
                    }
                }
                return value;
            }

        } else {
            if (log.isInfoEnabled()) {
                log.info("{}得到缓存->{}", key, result);
            }
            try {
                return objectMapper.readValue(result, cache.clazz());
            } catch (JsonProcessingException e) {
                if (log.isErrorEnabled()) {
                    log.error("缓存转为对象失败", e);
                }
            }
            return null;
        }
    }

    public <T> boolean set(Cache<T> cache) {
        T value = cache.value();
        String key = cache.key();
        String jsonValue;
        try {
           jsonValue = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            if (log.isErrorEnabled()) {
                log.error("缓存转为Json失败", e);
            }
            return false;
        }
        Long cacheTime = cache.timeSecond();
        if (cacheTime != null) {
            stringRedisTemplate.opsForValue().set(key, jsonValue, cacheTime, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, jsonValue);
        }
        return true;
    }

    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }
}
