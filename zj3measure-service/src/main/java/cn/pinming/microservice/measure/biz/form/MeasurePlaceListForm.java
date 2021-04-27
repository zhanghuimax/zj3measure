package cn.pinming.microservice.measure.biz.form;

import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import lombok.Data;

import java.util.List;

@Data
public class MeasurePlaceListForm {
    public List<MeasurePlaceForm> measurePlace;
}