package cn.pinming.microservice.measure.biz.service;

import cn.pinming.microservice.measure.biz.entity.MeasureItem;
import cn.pinming.microservice.measure.biz.form.MeasureItemForm;
import cn.pinming.microservice.measure.biz.mapper.MeasureItemMapper;
import cn.pinming.microservice.measure.biz.query.MeasureItemQuery;
import cn.pinming.microservice.measure.biz.vo.MeasureItemVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IMeasureItemService extends IService<MeasureItem> {
    /**
     * 保存分项
     * @param form  分项数据
     * @return 分项id
     */
    String saveMeasureItem(MeasureItemForm form);

    /**
     * 查看分项
     * @param query
     */
    List<MeasureItemVO> findMeasureItemList(MeasureItemQuery query);
}
