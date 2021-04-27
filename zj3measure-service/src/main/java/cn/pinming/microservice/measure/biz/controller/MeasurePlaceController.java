package cn.pinming.microservice.measure.biz.controller;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.web.response.Response;
import cn.pinming.core.web.response.SuccessResponse;
import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceForm;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceListForm;
import cn.pinming.microservice.measure.biz.listener.MeasureDataListener;
import cn.pinming.microservice.measure.biz.service.IMeasurePlaceService;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import cn.pinming.microservice.measure.common.util.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * <p>
 * 实测实量--测量部位表 前端控制器
 * </p>
 *
 * @author zh
 * @since 2021-04-14
 */
@Api(tags = "测量部位-Controller")
@RestController
@RequestMapping("/api/place")
@Slf4j
public class MeasurePlaceController {

    @Resource
    private AuthUserHolder authUserHolder;
    @Resource
    private IMeasurePlaceService measurePlaceService;

    @ApiOperation(value = "测量部位列表", response = MeasurePlaceVO.class)
    @GetMapping("/list")
    public ResponseEntity<Response> getMeasurePlaceList(@RequestParam(required = false) String positionName){
        AuthUser user = authUserHolder.getCurrentUser();
        List<MeasurePlaceVO> list = measurePlaceService.listMeasurePlace(user,positionName);
        return ResponseEntity.ok(new SuccessResponse(list));
    }

    @ApiOperation(value = "保存/修改测量部位")
    @PostMapping("/save")
    public ResponseEntity<Response> saveMeasurePlace(@RequestBody MeasurePlaceListForm form){
        AuthUser user = authUserHolder.getCurrentUser();
        measurePlaceService.saveMeasurePlace(user,form.getMeasurePlace());
        return ResponseEntity.ok(new SuccessResponse());
    }

    @ApiOperation(value = "删除测量部位")
    @GetMapping("/delete")
    public ResponseEntity<Response> deleteMeasurePlace(int id){
        measurePlaceService.deleteMeasurePlace(id);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @ApiOperation(value = "导入测量部位")
    @PostMapping("/import")
    public ResponseEntity<Response> importMeasurePlace(MultipartFile file) throws IOException {
        List<MeasurePlaceForm> list = EasyExcel.read(file.getInputStream(),MeasurePlaceForm.class,new MeasureDataListener(authUserHolder.getCurrentUser(),measurePlaceService)).sheet().doReadSync();
        return ResponseEntity.ok(new SuccessResponse(list));
    }

    @ApiOperation(value = "导出测量部位")
    @GetMapping("/export")
    public void exportMeasurePlace(HttpServletResponse response){
        List<MeasurePlaceVO> list = measurePlaceService.listPlace(authUserHolder.getCurrentUser());
        ExcelUtils.export(response,list,MeasurePlaceVO.class,"测量部位");
    }
}
