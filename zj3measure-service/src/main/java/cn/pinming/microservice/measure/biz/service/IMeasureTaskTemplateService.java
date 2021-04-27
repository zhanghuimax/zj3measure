package cn.pinming.microservice.measure.biz.service;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.dto.TaskTemplateDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureTaskTemplate;
import cn.pinming.microservice.measure.biz.form.MeasureTaskTemplateForm;
import cn.pinming.microservice.measure.biz.query.MeasureTaskTemplateQuery;
import cn.pinming.microservice.measure.biz.vo.MeasureTaskTemplateVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实测指标--测量任务模板 服务类
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
public interface IMeasureTaskTemplateService extends IService<MeasureTaskTemplate> {

    /**
     * 新增实测指标
     *
     * @param form
     */
    void insertTaskTemplate(MeasureTaskTemplateForm form);

    /**
     * 获取所属单位下的实测指标列表
     *
     * @param query
     * @return
     */
    Page<TaskTemplateDTO> listMeasureTaskTemplate(MeasureTaskTemplateQuery query);

    /**
     * 获取测量模板详情
     * @param template
     * @return
     */
    MeasureTaskTemplateVO getTaskTemplateDetail(String template);

    /**
     * 编辑测量模板详情
     * @param form
     * @return
     */
    void updateMeasureTemplate(MeasureTaskTemplateForm form);

    /**
     * 删除测量模板详情
     * @param template
     * @return
     */
    void deleteTaskTemplate(MeasureTaskTemplate template);

//    /**
//     * 查看移动端模板列表(带缓存)
//     * @return
//     */
//    Map<String, List> findTemplateList(AuthUser user,boolean isMobile);

}
