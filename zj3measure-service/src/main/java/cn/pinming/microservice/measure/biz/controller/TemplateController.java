package cn.pinming.microservice.measure.biz.controller;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.web.response.Response;
import cn.pinming.core.web.response.SuccessResponse;
import cn.pinming.microservice.measure.biz.dto.TaskTemplateDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureTaskTemplate;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.form.MeasureTaskTemplateForm;
import cn.pinming.microservice.measure.biz.query.MeasureTaskTemplateQuery;
import cn.pinming.microservice.measure.biz.service.IMeasureTaskTemplateService;
import cn.pinming.microservice.measure.biz.vo.MeasureTaskTemplateVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实测指标--测量任务模板
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
@Api(tags = "实测实量指标模板-controller")
@RestController
@Slf4j
@RequestMapping("/api/template")
public class TemplateController {

    @Resource
    private AuthUserHolder authUserHolder;
    @Resource
    private IMeasureTaskTemplateService templateService;


    @ApiOperation(value = "查看实测指标列表", response = TaskTemplateDTO.class)
    @PostMapping("/list")
    public ResponseEntity<Response> list(@RequestBody MeasureTaskTemplateQuery query){
        AuthUser user = authUserHolder.getCurrentUser();
        query.setCompanyId(user.getCurrentCompanyId());
        Page<TaskTemplateDTO> pageList = templateService.listMeasureTaskTemplate(query);
        return ResponseEntity.ok(new SuccessResponse(pageList));

    }

    @ApiOperation(value = "新增测量任务模板")
    @PostMapping("/add")
    public ResponseEntity<Response> add(@RequestBody @Validated MeasureTaskTemplateForm form){
        AuthUser user = authUserHolder.getCurrentUser();
        form.setCompanyId(user.getCurrentCompanyId());
        templateService.insertTaskTemplate(form);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @ApiOperation(value = "测量任务模板详情")
    @GetMapping("/{templateId}")
    public ResponseEntity<Response> info(@PathVariable String templateId){
        MeasureTaskTemplateVO result = templateService.getTaskTemplateDetail(templateId);
        return ResponseEntity.ok(new SuccessResponse(result));

    }

    @ApiOperation(value = "编辑测量任务模板")
    @PostMapping("/update")
    public ResponseEntity<Response> update(@RequestBody @Validated MeasureTaskTemplateForm form){
//        这里只传companyid，我一开始还想传templateId,但这个编辑按钮是每行都有的，你不能每个点了都去找你设定的templateid的信息
        AuthUser user = authUserHolder.getCurrentUser();
        form.setCompanyId(user.getCurrentCompanyId());
        templateService.updateMeasureTemplate(form);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @ApiOperation(value = "删除测量任务模板列表")
    @PostMapping("/del/{templateId}")
    public ResponseEntity<Response> deleteTaskTemplate(@PathVariable String templateId){
        MeasureTaskTemplate template = new MeasureTaskTemplate();
        template.setTemplateId(templateId);
        template.setIsDelete(DeleteEnum.DELETE.value());
        templateService.deleteTaskTemplate(template);
        return ResponseEntity.ok(new SuccessResponse());
    }

//    /**
//     * 移动端模板列表(带缓存)
//     *
//     * @return
//     */
//    @ApiOperation(value = "测量任务模板列表")
//    @GetMapping("/mobile/list")
//    public ResponseEntity<Response> mobileTemplateList(){
//        AuthUser user = authUserHolder.getCurrentUser();
//        Map<String, List> nodeList = templateService.findTemplateList(user,true);
//    }



}
