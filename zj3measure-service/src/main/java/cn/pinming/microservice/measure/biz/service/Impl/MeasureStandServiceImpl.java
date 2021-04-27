package cn.pinming.microservice.measure.biz.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.pinming.microservice.measure.biz.entity.MeasureStandard;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.enums.IsAbsoluteValueEnum;
import cn.pinming.microservice.measure.biz.enums.IsCustomEnum;
import cn.pinming.microservice.measure.biz.enums.IsRangeArithmeticEnum;
import cn.pinming.microservice.measure.biz.form.MeasureStandardForm;
import cn.pinming.microservice.measure.biz.mapper.MeasureStandardMapper;
import cn.pinming.microservice.measure.biz.query.MeasureStandardQuery;
import cn.pinming.microservice.measure.biz.service.IMeasureStandardService;
import cn.pinming.microservice.measure.biz.vo.MeasureStandardVO;
import cn.pinming.microservice.measure.common.ValidCommon;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.Query;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 实测实量--测量分项评判标准表 服务实现类
 * </p>
 *
 * @author zh
 * @since 2021-04-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MeasureStandServiceImpl extends ServiceImpl<MeasureStandardMapper, MeasureStandard> implements IMeasureStandardService {
    @Resource
    private Validator validator;

    @Override
    public void updateMeasureStandard(MeasureStandardForm standard)
    {
        ValidCommon.validRequestParams(validator.validate(standard));
        MeasureStandard measureStandard = new MeasureStandard();
        BeanUtils.copyProperties(standard, measureStandard);
        this.saveOrUpdate(measureStandard);
    }

    @Override
    public  List<MeasureStandardVO> findMeasureStandardList(MeasureStandardQuery standardQuery){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("company_id",standardQuery.getCompanyId());
        wrapper.eq("item_id",standardQuery.getItemId());
        wrapper.eq("is_delete", DeleteEnum.NORMAL.value());
        wrapper.orderByAsc("gmt_create");
        List<MeasureStandard> list = this.list(wrapper);
        List<MeasureStandardVO> result = new ArrayList<>();
        if(CollUtil.isNotEmpty(list)){
            result = list.stream().map(e -> {
                MeasureStandardVO vo = new MeasureStandardVO();
                BeanUtils.copyProperties(e,vo);
                String itemDescribe = StrUtil.format("合格区间【{},{}】mm,基准值：{}mm,绝对值：{},极差算法：{},自定义基准值:{}",vo.getJudgeStartValue(),vo.getJudgeEndValue(),vo.getBaseValue(), IsAbsoluteValueEnum.getDesc(vo.getIsAbsoluteValue()), IsRangeArithmeticEnum.getDesc(vo.getIsRangeArithmetic()), IsCustomEnum.getDesc(vo.getIsCustom()));
                vo.setItemDescribe(itemDescribe);
                return vo;
            }).collect(Collectors.toList());
        }
        return result;

    }
}
