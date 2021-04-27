package cn.pinming.microservice.measure.biz.controller;

import cn.hutool.core.util.StrUtil;
import cn.pinming.core.cookie.AuthUser;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.web.response.Response;
import cn.pinming.microservice.measure.biz.dto.MeasureTaskDTO;
import cn.pinming.microservice.measure.biz.query.MeasureTaskQuery;
import cn.pinming.microservice.measure.biz.service.IMeasureTaskService;
import cn.pinming.microservice.measure.biz.vo.StatusTaskVO;
import cn.pinming.microservice.measure.common.SuccessResponse;
import cn.pinming.microservice.measure.common.util.CamelUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 实测实量-数据统计-controller
 * </p>
 *
 * @author zh
 * @since 2021-04-19
 */
@RestController
@Api(tags = "实测实量-数据统计-controller")
@Slf4j
@RequestMapping("/api/mobile")
public class MeasureCalculateController {

    @Resource
    private AuthUserHolder authUserHolder;
    @Resource
    private IMeasureTaskService measureTaskService;

    @ApiOperation(value = "测量任务（指派待测，待复测，待整改）",response = StatusTaskVO.class)
    @GetMapping("/taskStatus")
    public ResponseEntity<Response> findSpreadTaskStatus(){
        AuthUser user = authUserHolder.getCurrentUser();
        StatusTaskVO result = measureTaskService.findSpreadTaskStatus(user);
        return ResponseEntity.ok(new SuccessResponse(result));
    }

    @ApiOperation(value = "查询当前操作人的任务列表",response = MeasureTaskDTO.class)
    @PostMapping("/list")
    public ResponseEntity<Response> getTaskList(@RequestBody(required = false) MeasureTaskQuery query) {
//        这里为什么不直接用user，因为还有很多user中没有的字段需要判断，所以另起一个
//        前端查询的条件可能不止cid，pid，id
       AuthUser user = authUserHolder.getCurrentUser();
       query.setCompanyId(user.getCurrentCompanyId());
       query.setProjectId(user.getCurrentProjectId());
       query.setMemberId(user.getId());
        if(StrUtil.isNotBlank(query.getOrderName())){
            query.setOrderName("A." + CamelUtils.camel2Underline(query.getOrderName()));
        }
        Page<MeasureTaskDTO> pageList = measureTaskService.getMeasureTaskList(query,false);
        return ResponseEntity.ok(new SuccessResponse(pageList));
    }

}
