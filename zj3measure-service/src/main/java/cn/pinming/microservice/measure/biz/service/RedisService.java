package cn.pinming.microservice.measure.biz.service;

import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import cn.pinming.microservice.measure.biz.entity.MeasureTask;
import cn.pinming.microservice.measure.biz.entity.User;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    public boolean setUser(User user){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(user.getName(),user);
        log.info("{}",user.toString());
        return true;
    }
    public User getUser(String name){
        ValueOperations ops = redisTemplate.opsForValue();
        return (User) ops.get(name);
    }

    public boolean setPlace(MeasurePlaceVO measurePlaceVO){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(measurePlaceVO.getPlaceId(),measurePlaceVO);
        log.info("{}",measurePlaceVO.toString());
        return true;
    }

    public MeasurePlaceVO getPlace(String placeId){
        ValueOperations ops = redisTemplate.opsForValue();
        return (MeasurePlaceVO) ops.get(placeId);
    }
}
