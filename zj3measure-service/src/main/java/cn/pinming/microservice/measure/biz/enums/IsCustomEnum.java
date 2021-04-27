package cn.pinming.microservice.measure.biz.enums;

public enum IsCustomEnum {
    YES((byte) 1, "是"),
    NO((byte) 2, "否"),
    ;

    private byte value;

    private String description;

    IsCustomEnum(byte value, String description) {
        this.value = value;
        this.description = description;
    }

    public byte value() {
        return value;
    }

    public String description() {
        return description;
    }

    public static String getDesc(byte value) {
        IsCustomEnum[] typeEnums = values();
        for (IsCustomEnum typeEnum : typeEnums) {
            if (typeEnum.value() == value) {
                return typeEnum.description();
            }
        }
        return null;
    }

}
