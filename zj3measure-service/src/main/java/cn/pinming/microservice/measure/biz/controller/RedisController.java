package cn.pinming.microservice.measure.biz.controller;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.web.response.Response;
import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import cn.pinming.microservice.measure.biz.entity.User;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceListForm;
import cn.pinming.microservice.measure.biz.service.IMeasurePlaceService;
import cn.pinming.microservice.measure.biz.service.RedisService;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import cn.pinming.microservice.measure.common.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "缓存")
@RestController
@Slf4j
@RequestMapping("/api/redis")
public class RedisController {
    @Resource
    private RedisService redisService;
    @Resource
    private IMeasurePlaceService measurePlaceService;
    @Resource
    private AuthUserHolder authUserHolder;

    @ApiOperation(value = "从缓存中得到测量部位",response = MeasurePlaceVO.class)
    @GetMapping("/getPlace")
    public ResponseEntity<Response> GetPlace(@RequestParam(required = false) String positionName){
        AuthUser user = authUserHolder.getCurrentUser();
        List<MeasurePlaceVO> list1 = new ArrayList<>();
        List<MeasurePlaceVO> list = measurePlaceService.listMeasurePlace(user,positionName);
        for(MeasurePlaceVO result : list){
            redisService.setPlace(result);
            redisService.getPlace(result.getPlaceId());
            list1.add(result);
        }
        return ResponseEntity.ok(new SuccessResponse(list1));
    }


    @GetMapping("/setUser")
    public String setUser(){
        User user = new User("zh","123","1781981502@qq.com");
        redisService.setUser(user);
        return "添加成功";

    }

    @GetMapping("/getUser")
    public User getUser(){
        String name = "zh";
        return redisService.getUser(name);
    }






}
