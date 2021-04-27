package cn.pinming.microservice.measure.biz.mapper;

import cn.pinming.microservice.measure.biz.dto.MeasureTaskDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureTask;
import cn.pinming.microservice.measure.biz.query.MeasureTaskQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
 * <p>
 * 实测实量--任务表 Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2021-04-16
 */
public interface MeasureTaskMapper extends BaseMapper<MeasureTask> {
    Page<MeasureTaskDTO>findMeasureTaskList(MeasureTaskQuery query);

}
