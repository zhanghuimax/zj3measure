package cn.pinming.microservice.measure.biz.enums;

public enum DeleteEnum {

    NORMAL((byte)1,"正常"),
    DELETE((byte)2,"删除");

    private byte value;
    private String description;

    DeleteEnum(byte value,String description){
        this.value = value;
        this.description = description;
    }

    public byte value(){
        return value;
    }
    public String description(){
        return description;
    }
}
