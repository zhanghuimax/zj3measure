package cn.pinming.microservice.measure.biz.mapper;

import cn.pinming.microservice.measure.biz.dto.TaskTemplateDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureTaskTemplate;
import cn.pinming.microservice.measure.biz.query.MeasureTaskTemplateQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 实测指标--测量任务模板 Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
public interface MeasureTaskTemplateMapper extends BaseMapper<MeasureTaskTemplate> {

//    你没有用@Param的话，你的query虽然传进来了，但是数据库不知道啊，所以要用个注释来告诉他
    Page<TaskTemplateDTO> selectTemplateList(@Param("query")MeasureTaskTemplateQuery query);


}
