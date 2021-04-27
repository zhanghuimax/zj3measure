package cn.pinming.microservice.measure.biz.strategy.enums;

import cn.pinming.core.web.interfaces.InfParam;

/**
 * 
 * @Description : 接口参数输入枚举类
 * @author sam    
 * @version 1.0  
 * @created Mar 29, 2013 6:37:02 PM
 * @fileName com.weqia.interfaces.constant.InfParamEnum.java
 *
 */
public enum InfParamEnum implements InfParam {

    version 					("version", "版本号", Float.class),
    mid		  ("mid","会员id",String.class),
    mCoId	  ("mCoId","当前企业Id",Integer.class),
    pjId				("pjId","项目ID",Integer.class),
    projectId				("projectId","项目ID",Integer.class),

    ;
	private InfParamEnum(String paramValue, String paramMsg, Class<?> clazz){
		this.paramValue = paramValue;
		this.paramMsg = paramMsg;
		this.clazz = clazz;
	}


	private String paramValue;		//参数值
	private String paramMsg; 		//参数含义
	private Class<?> clazz;			//参数类型
	
	public String paramMsg(){
		return paramMsg;
	}
	
	public String paramValue(){
		return paramValue;
	}
	
	public Class<?> clazz(){
		return clazz;
	}
	
}
