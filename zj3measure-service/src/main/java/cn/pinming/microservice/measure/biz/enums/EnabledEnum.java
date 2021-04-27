package cn.pinming.microservice.measure.biz.enums;

/**
 * 启用禁用枚举
 */
public enum EnabledEnum {
    ENABLE((byte) 1, "启用"),
    DISABLE((byte) 2, "禁用");

    private byte value;

    private String description;

    EnabledEnum(byte value, String description) {
        this.value = value;
        this.description = description;
    }

    public byte value() {
        return value;
    }

    public String description() {
        return description;
    }
}
