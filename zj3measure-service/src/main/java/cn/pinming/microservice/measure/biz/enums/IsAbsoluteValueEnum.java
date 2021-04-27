package cn.pinming.microservice.measure.biz.enums;

public enum IsAbsoluteValueEnum {
    YES((byte)1,"是"),
    NO((byte)2,"否"),
    ;
    private byte value;
    private String description;

    IsAbsoluteValueEnum(byte value ,String description){
        this.value = value ;
        this.description = description;
    }

    private byte value(){
        return value;
    }
    private String description(){
        return description;
    }

    public static String getDesc(byte value){
        IsAbsoluteValueEnum[] typeEnums = values();
        for(IsAbsoluteValueEnum typeEnum : typeEnums){
            if(typeEnum.value() == value){
                return typeEnum.description;
            }
        }
        return null;
    }
}
