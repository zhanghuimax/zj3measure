package cn.pinming.microservice.measure.biz.listener;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceForm;
import cn.pinming.microservice.measure.biz.service.IMeasurePlaceService;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import cn.pinming.microservice.measure.common.exception.BOException;
import cn.pinming.microservice.measure.common.exception.BOExceptionEnum;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MeasureDataListener extends AnalysisEventListener<MeasurePlaceForm> {

    private AuthUser user;

    private IMeasurePlaceService measurePlaceService;

    private List<MeasurePlaceForm> list = new ArrayList<>();

    public MeasureDataListener(AuthUser user, IMeasurePlaceService measurePlaceService) {
        this.user = user;
        this.measurePlaceService = measurePlaceService;
    }

    /**
     * 表头标题
     */
    private static final String[] HEADERS = {"区域", "楼栋", "单元", "楼层", "部位"};

//    逐个判断表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        for (String head : HEADERS) {
            boolean contains = headMap.containsValue(head);
            if (!contains) {
                throw new BOException(BOExceptionEnum.PLACE_IS_ERROR);
            }
        }
    }

//    添加数据
    @Override
    public void invoke(MeasurePlaceForm data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        processData();
    }

    private void processData() {
        for (MeasurePlaceForm form : list) {
            MeasurePlaceVO measurePlaceVO = measurePlaceService.selectMeasurePlaceByName(user.getCurrentCompanyId(), user.getCurrentProjectId(), form);
            if (Objects.nonNull(measurePlaceVO)) {
                BeanUtils.copyProperties(measurePlaceVO, form);
            }
        }
    }

}

