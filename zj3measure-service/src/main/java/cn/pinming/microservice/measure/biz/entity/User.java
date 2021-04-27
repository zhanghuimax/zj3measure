package cn.pinming.microservice.measure.biz.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String passWord;
    private String email;

    public User(String name,String passWord,String email){
        super();
        this.name = name;
        this.passWord = passWord;
        this.email = email;
    }
}
