package cn.pinming.microservice.measure.biz.mapper;

import cn.pinming.microservice.measure.biz.entity.MeasureStandard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeasureStandardMapper extends BaseMapper<MeasureStandard> {
    void deleteMeasureStandardList(@Param("itemId") List<String> itemId);
}
