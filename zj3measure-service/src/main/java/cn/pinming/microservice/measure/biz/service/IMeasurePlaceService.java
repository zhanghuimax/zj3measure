package cn.pinming.microservice.measure.biz.service;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceForm;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 实测实量--测量部位表 服务类
 * </p>
 *
 * @author zh
 * @since 2021-04-14
 */
public interface IMeasurePlaceService extends IService<MeasurePlace> {

    /**
     * 获取测量部位列表
     * @param user      用户信息
     * @param position  测量部位名称
     * @return 测量部位列表
     */
    List<MeasurePlaceVO> listMeasurePlace(AuthUser user,String position);

    /**
     * 保存测量部位列表
     * @param user  用户信息
     * @param list  测量部位列表
     */
    void saveMeasurePlace(AuthUser user,List<MeasurePlaceForm> list);

    /**
     * 删除测量部位列表
     */
    void deleteMeasurePlace(int id);

    /**
     *
     * @param companyId
     * @param projectId
     * @param form
     * @return
     */
    MeasurePlaceVO selectMeasurePlaceByName(Integer companyId,Integer projectId,MeasurePlaceForm form);

    /**
     * 获取测量部位列表
     * @param user 用户信息
     * @return 测量部位列表
     */
    List<MeasurePlaceVO> listPlace(AuthUser user);


}
