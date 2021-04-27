package cn.pinming.microservice.measure.biz.enums;

import lombok.Data;

/**
 * 测量任务状态 1指派待测 2指派进行中 3本地进行中 4待整改 5待复测 6已完成
 *
 * @author zh
 */
public enum  MeasureTaskStatusEnum {
    ASSIGNMENT_TO_BE_TESTED((byte) 1, "指派待测"),
    ASSIGNMENT_IN_PROGRESS((byte) 2, "指派进行中"),
    LOCAL_IN_PROGRESS((byte) 3, "本地进行中"),
    WAITING_FOR_RECTIFICATION((byte) 4, "待整改"),
    WAITING_FOR_TESTED((byte) 5, "待复测"),
    FINISH((byte) 6, "已完成");;

    private Byte value;
    private String description;

    MeasureTaskStatusEnum(Byte value,String description){
        this.value = value;
        this.description = description;
    }

    public Byte value(){
        return value;
    }
    public String description(){
        return description;
    }
}
