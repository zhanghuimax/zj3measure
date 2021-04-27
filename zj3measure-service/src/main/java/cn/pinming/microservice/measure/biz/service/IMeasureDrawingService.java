package cn.pinming.microservice.measure.biz.service;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.entity.MeasureDrawing;
import cn.pinming.microservice.measure.biz.form.MeasureDrawingForm;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 实测实量-项目图纸 服务类
 * </p>
 *
 * @author zh
 * @since 2021-04-15
 */
public interface IMeasureDrawingService extends IService<MeasureDrawing> {

    /**
     * 项目图纸分页
     * @param page  分页对象
     * @param user  用户对象
     * @return  分页列表
     */
    Page findMeasureDrawingPageList(Page page, AuthUser user);

    /**
     * 新增项目图纸
     * @param form   提交数据
     * @param user   用户对象
     */
    void addMeasureDrawing(MeasureDrawingForm form,AuthUser user);

    /**
     * 编辑项目图纸
     * @param form  提交数据
     * @param user  用户对象
     */
    void updateMeasureDrawing(MeasureDrawingForm form,AuthUser user);

    /**
     * 删除
     */
    void deleteMeasureDrawing(Integer id);
}
