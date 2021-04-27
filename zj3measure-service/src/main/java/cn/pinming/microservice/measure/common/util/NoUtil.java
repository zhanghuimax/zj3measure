package cn.pinming.microservice.measure.common.util;

import cn.hutool.core.date.DateUtil;
import cn.pinming.microservice.measure.common.exception.BOException;
import cn.pinming.microservice.measure.common.exception.BOExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class NoUtil {

    private static RedisTemplate redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        NoUtil.redisTemplate = redisTemplate;
    }

    private static final String NO_KEY = "zj3_measure:no:%s";

    private static final String DAY_KEY = "zj3_measure:day";

    private static ReentrantLock lock = new ReentrantLock();

    public static String getNo(String prefix, @NotNull Integer projectId) {
        lock.lock();
        try {
            Date time = (Date) redisTemplate.opsForValue().get(DAY_KEY);
            Date now = new Date();
            if (time == null || !DateUtil.isSameDay(time, now)) {
                redisTemplate.opsForValue().set(DAY_KEY, now, 1L, TimeUnit.DAYS);
                redisTemplate.delete(redisTemplate.keys(String.format(NO_KEY, "*")));
            }
        }catch (Exception e){
            log.info("【获取no.出错】 {}", e.getMessage());
        }finally {
            lock.unlock();
        }
        Long increment = redisTemplate.opsForHash().increment(String.format(NO_KEY, prefix), projectId, 1);
        return prefix + dateNumber() + completion(increment);
    }

    public static String completion(Long num){
        return String.format("%04d", num);
    }

    public static String dateNumber(){
        return DateUtil.format(new Date(), "yyyyMMdd");
    }
}
