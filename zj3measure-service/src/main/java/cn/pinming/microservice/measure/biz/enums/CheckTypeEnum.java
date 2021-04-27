package cn.pinming.microservice.measure.biz.enums;

/**
 * 检查类型 1 企业级 2 项目级
 */
public enum  CheckTypeEnum {
    COMPANY((byte)1, "企业级"),
    PROJECT((byte)2, "项目级");

    private byte value;

    private String description;

    CheckTypeEnum(byte value, String description) {
        this.value = value;
        this.description = description;
    }

    public byte value() {
        return value;
    }

    public String description() {
        return description;
    }

    public static String getDesc(Byte value){
        CheckTypeEnum[] typeEnums = values();
        for(CheckTypeEnum typeEnum : typeEnums){
            if(typeEnum.value() == value){
                return typeEnum.description();
            }
        }
        return null;
    }
}
