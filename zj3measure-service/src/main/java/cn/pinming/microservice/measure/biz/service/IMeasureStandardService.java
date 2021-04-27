package cn.pinming.microservice.measure.biz.service;

import cn.pinming.microservice.measure.biz.entity.MeasureStandard;
import cn.pinming.microservice.measure.biz.form.MeasureStandardForm;
import cn.pinming.microservice.measure.biz.query.MeasureStandardQuery;
import cn.pinming.microservice.measure.biz.vo.MeasureStandardVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.tools.doclets.formats.html.resources.standard;

import java.util.List;

public interface IMeasureStandardService extends IService<MeasureStandard> {
     void updateMeasureStandard(MeasureStandardForm standard);

     List<MeasureStandardVO> findMeasureStandardList(MeasureStandardQuery standardQuery);
}
