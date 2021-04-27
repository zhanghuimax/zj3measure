package cn.pinming.microservice.measure.biz.enums;

import lombok.Getter;

@Getter
public enum PermissionEnum {
    COMPANY((byte)1),
    PROJECT((byte)2),
    COMPANY_PROJECT((byte)3);

    private byte value;

    PermissionEnum(byte value){
        this.value = value;
    }
}
