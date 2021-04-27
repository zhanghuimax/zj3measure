package cn.pinming.microservice.measure.biz.mapper;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.dto.MeasurePlaceDTO;
import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceForm;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实测实量--测量部位表 Mapper 接口
 * </p>
 *
 * @author zh
 * @since 2021-04-14
 */

public interface MeasurePlaceMapper extends BaseMapper<MeasurePlace> {
    List<MeasurePlaceVO> listMeasurePlace(@Param("companyId") Integer companyId,
                                          @Param("projectId") Integer projectId,
                                          @Param("positionName") String positionName);

    MeasurePlaceVO getMeasurePlaceByName(@Param("companyId")Integer companyId, @Param("projectId")Integer projectId,@Param("form") MeasurePlaceForm form);
}


