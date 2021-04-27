package cn.pinming.microservice.measure.common.exception;

/**
 * @author sam
 * @version 1.0
 * @Description :BoException的错误码和错误信息枚举
 * @created Nov 28, 2012 9:11:18 PM
 * @fileName com.weqia.common.exception.BOExceptionEnum.java
 */
public enum BOExceptionEnum {

    //--------------------------------------------------  基础 -----------------------------------------------------//
    NO_PERMISSION("-1", "无权限"),
    NO_SET_PERMISSION_LEVEL("-10", "没有指定权限级别"),
    PERMISSION_NOT_CONFIGURATION("-12", "权限未配置"),
    NO_EMPLOYEE_INFO("-2", "未查到该员工信息"),
    SYS_ERROR("-10", "系统异常，请稍候再试"),
    INF_ERROR("-11", "接口异常或私有云服务端版本过低"),
    PARAM_MISS("-12", "缺少参数"),
    PARAM_ITYPE_MISS("-13", "缺少接口编号"),


    MEASURE_TYPE_IS_NOT_EXISTS("-31", "测量类型不存在"),
    STANDARD_IS_OUT("-32", "评判标准上限为10条！"),
    STANDARD_NAME_REPEAT("-33", "当前测量分项下标准名称重复！"),
    STANDARD_AREA_ERROR("-34", "测量标准区间异常！"),
    ITEM__IS_NULL("-35", "测量分项不能为空！"),
    ITEM_IS_OUT("-36", "测量分项上限为50条！"),
    TWO_NAME_EXIST("-37", "当前实测指标二级名称已存在！"),
    TASK_TEMPLATE_EXITS("-38", "测量任务模板不存在！"),
    COMMON_SETTING_NOT_EXITS("-39", "请设置通用设置！"),
    TEMPLATE_ID_IS_NULL("-40", "测量模板id不能为空！"),
    PLACE_IS_NULL("-41", "测量部位不能为空！"),
    PLACE_IS_OUT("-42", "测量部位上限为500条！"),
    PLACE_IS_ERROR("-43", "导入测量部位异常，请选择正确的模板！"),
    TASK_IS_NOT_EXISTS("-44", "任务不存在！"),
    TYPE_IS_NULL("-44", "请先选择测量类型！"),
    ITEM_IS_ERROR("-45", "导入测量分项异常，请选择正确的模板！"),
    MEMBER_IS_NOT_EXISTS("-46", "人员不存在"),
    DICT_NOT_EXISTS("-47", "字典不存在"),
    DEPARTMENT_IS_NULL("-48", "未查询到当前登陆人所在部门信息"),
    DEPARTMENT_ID_IS_NULL("-49", "当前登录人所在的部门id为空"),
    COMPANY_OR_FLAG_IS_NULL("-50", "企业层或项目层标识不能为空"),
    DICT_VALUE_EXISTS("-51", "字典值已存在"),
    PROJECT_DEPARTMENT_IS_NULL("-52", "当前项目所属部门信息为空"),
    RULE_VALUE_EXISTS("-53", "规则已存在"),
    DEPARTMENT_NOT_EXISTS("-54", "部门不存在"),
    PROJECT_NOT_EXISTS("-56", "项目不存在"),
    COMPANY_DEPARTMENT_IS_NULL("-55", "企业级所属部门信息为空"),
    CONFIG_VALUE_EXISTS("-57", "配置项已存在"),
    NUMBER_NOT_RULE("-58", "数值不能小于上级设定的数值"),
    RULE_NOT_EXISTS("-59", "规则不存在"),
    RULE_IS_NULL("-60", "您的上级机构未设置规则，请联系上级机构管理员"),
    PROJECT_PASSRATE_LESS_THEN_SETTING("-61", "项目合格率不能低于所属机构最下值"),
    PROJECT_ID_IS_NULL("-62", "当前操作人不在项目级，请切换到项目进行查看"),
    PROJECT_SETTING_NOT_EXIST("-63", "当前项目合格率设置不存在"),
    DRAWING_IS_EXIST("-64", "图纸名称已存在"),
    NUMBER_IS_NULL("-65", "数值不能为空"),
    UNIT_IS_NULL("-66", "单位不能为空"),
    CONFIG_IS_NULL("-67", "配置项不能为空"),
    MARK_IS_NOT_COMPLETE("-68", "标记未填完,请完成填写"),
    PLACE_IS_NOT_EXIST("-69", "测量部位不存在！"),
    MARK_IS_NOT_EXIST("-70", "标记不存在！"),
    ;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    private BOExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String errorCode() {
        return errorCode;
    }

    public String errorMsg() {
        return errorMsg;
    }
}
