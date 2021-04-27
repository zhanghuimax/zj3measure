package cn.pinming.microservice.measure.biz.service;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.dto.MeasureTaskDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureTask;
import cn.pinming.microservice.measure.biz.query.MeasureTaskQuery;
import cn.pinming.microservice.measure.biz.vo.StatusTaskVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 实测实量--任务表 服务类
 * </p>
 *
 * @author zh
 * @since 2021-04-19
 */
public interface IMeasureTaskService extends IService<MeasureTask> {

    /**
     * APP-测量任务（指派待测任务、待复测任务、待整改任务）
     * @param user  用户信息
     * @return 结果对象
     */
    StatusTaskVO findSpreadTaskStatus(AuthUser user);


    /**
     * 任务测量记录列表
     * @param query  查询条件
     * @param isWeb  是否来自网页端
     * @return  任务测量记录
     */
    Page<MeasureTaskDTO> getMeasureTaskList(MeasureTaskQuery query,boolean isWeb);
}
