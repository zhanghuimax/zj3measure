package cn.pinming.microservice.measure.biz.service.Impl;

import cn.pinming.microservice.measure.biz.entity.MeasureItem;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.form.MeasureItemForm;
import cn.pinming.microservice.measure.biz.mapper.MeasureItemMapper;
import cn.pinming.microservice.measure.biz.query.MeasureItemQuery;
import cn.pinming.microservice.measure.biz.service.IMeasureItemService;
import cn.pinming.microservice.measure.biz.vo.MeasureItemVO;
import cn.pinming.microservice.measure.common.ValidCommon;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.validation.Validator;
import java.util.List;

/**
 * <p>
 * 实测实量--测量分项表 服务实现类
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
@Service
public class MeasureItemServiceImpl extends ServiceImpl<MeasureItemMapper, MeasureItem> implements IMeasureItemService {

    @Resource
    private Validator validator;
    @Resource
    private MeasureItemMapper measureItemMapper;

    /**
     * 新增测量分项
     * @param form
     */
    @Override
    public String saveMeasureItem(MeasureItemForm form){
        ValidCommon.validRequestParams(validator.validate(form));
        MeasureItem measureItem = new MeasureItem();
        BeanUtils.copyProperties(form,measureItem);
        measureItem.setIsDelete(DeleteEnum.NORMAL.value());
        this.saveOrUpdate(measureItem);
        return measureItem.getItemId();
    }

    /**
     * 查看测量分项
     * @param query
     */
    @Override
    public List<MeasureItemVO> findMeasureItemList(MeasureItemQuery query){
        List<MeasureItemVO> result = measureItemMapper.selectItemList(query);
        return result;

    }
}
