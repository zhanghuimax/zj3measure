package cn.pinming.microservice.measure.biz.controller;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.web.response.Response;
import cn.pinming.core.web.response.SuccessResponse;
import cn.pinming.microservice.measure.biz.form.MeasureDrawingForm;
import cn.pinming.microservice.measure.biz.service.IMeasureDrawingService;
import cn.pinming.microservice.measure.biz.vo.MeasureDrawingVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 实测实量-项目图纸 前端控制器
 * </p>
 *
 * @author zh
 * @since 2021-04-15
 */
@RestController
@Api(tags = "项目图纸-controller")
@RequestMapping("/api/drawing")
public class MeasureDrawingController{

    @Resource
    private AuthUserHolder authUserHolder;
    @Resource
    private IMeasureDrawingService measureDrawingService;

    @ApiOperation(value = "项目图纸列表",response = MeasureDrawingVO.class)
    @GetMapping("/list")
    public ResponseEntity<Response> list(Page page){
        AuthUser user = authUserHolder.getCurrentUser();
        Page result = measureDrawingService.findMeasureDrawingPageList(page,user);
        return ResponseEntity.ok(new SuccessResponse(result));
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public ResponseEntity<Response> add(@RequestBody MeasureDrawingForm form){
        AuthUser user = authUserHolder.getCurrentUser();
        measureDrawingService.addMeasureDrawing(form,user);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @ApiOperation(value = "编辑")
    @PostMapping("/update")
    public ResponseEntity<Response> update(@RequestBody MeasureDrawingForm form){
        AuthUser user = authUserHolder.getCurrentUser();
//        如果fileUuid乱填的话会报错
        measureDrawingService.updateMeasureDrawing(form,user);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete")
    public ResponseEntity<Response> delete(Integer id){
        measureDrawingService.deleteMeasureDrawing(id);
        return ResponseEntity.ok(new SuccessResponse());
    }

}