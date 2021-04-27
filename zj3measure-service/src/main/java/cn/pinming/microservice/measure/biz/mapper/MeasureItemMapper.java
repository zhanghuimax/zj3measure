package cn.pinming.microservice.measure.biz.mapper;

import cn.pinming.microservice.measure.biz.entity.MeasureItem;
import cn.pinming.microservice.measure.biz.query.MeasureItemQuery;
import cn.pinming.microservice.measure.biz.vo.MeasureItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实测实量--测量分项表 Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
public interface MeasureItemMapper extends BaseMapper<MeasureItem> {
    List<MeasureItemVO> selectItemList(@Param("query") MeasureItemQuery query);


}
