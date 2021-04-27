package cn.pinming.microservice.measure.biz.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseForm implements Serializable {
    /**
     * 企业ID（web）
     */
    private Integer companyId;
    /**
     * 项目ID(web)
     */
    private Integer projectId;
    /**
     * 会员id
     */
    private String mid;
    /**
     * 企业ID(移动端)
     */
    private Integer mCoId;
    /**
     * 项目ID(移动端)
     */
    private Integer pjId;

}
