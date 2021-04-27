package cn.pinming.microservice.measure.biz.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.entity.MeasurePlace;
import cn.pinming.microservice.measure.biz.entity.MeasureTask;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.form.MeasurePlaceForm;
import cn.pinming.microservice.measure.biz.mapper.MeasurePlaceMapper;
import cn.pinming.microservice.measure.biz.mapper.MeasureTaskMapper;
import cn.pinming.microservice.measure.biz.service.IMeasurePlaceService;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import cn.pinming.microservice.measure.common.exception.BOException;
import cn.pinming.microservice.measure.common.exception.BOExceptionEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 实测实量--测量部位表 服务实现类
 * </p>
 *
 * @author zh
 * @since 2021-04-14
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MeasurePlaceServiceImpl extends ServiceImpl<MeasurePlaceMapper, MeasurePlace> implements IMeasurePlaceService {

    @Resource
    private MeasurePlaceMapper measurePlaceMapper;
    @Resource
    private MeasureTaskMapper measureTaskMapper;

    /**
     * 获取测量部位列表
     *
     * @return 测量部位列表数据
     */
    @Override
    public List<MeasurePlaceVO> listMeasurePlace(AuthUser user, String positionName){
        return measurePlaceMapper.listMeasurePlace(user.getCurrentCompanyId(),user.getCurrentProjectId(),positionName);
    }

    /**
     * 保存测量部位列表
     */
    @Override
    public void saveMeasurePlace(AuthUser user,List<MeasurePlaceForm> list){
        if(CollUtil.isEmpty(list)){
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("company_id",user.getCurrentCompanyId());
            wrapper.eq("project_id",user.getCurrentProjectId());
            MeasurePlace place = new MeasurePlace();
            place.setIsDelete(DeleteEnum.DELETE.value());
            this.update(place,wrapper);
            return;
        }

        if(list.size() > 500){
            throw new BOException(BOExceptionEnum.PLACE_IS_OUT);
        }

//       现在MeasurePlaceForm有 主键id 区域 楼栋 单元 楼层 部位 图纸名称 图纸uuid
//        还有place_id,template_id,create_id,gmt_create,modify_id,gmt_modify,company_id,project_id,is_delete
//        所以通过一个本类的方法去添加所缺的数据
//        通过createMeasurePlaceList，company_id,project_id,create_id,gmt_create,is_delete
//        现在还有place_id,template_id，modify_id,gmt_modify没有
//        modify_id,gmt_modify没有也没事，总有没有修改的列表，设为null就行

//          运行后， Unknown column 'drawing_name' in 'field list' 可能是drawing_name它不知道
//        一开始我想的是明明MeasurePlaceForm中有这个字段，应该不是drawing_name没有定义
//        但是现在我想起来我给的是一个 List<MeasurePlace> placeList 再看看
//          有的
//        然后我看了一眼报错信息 发现是'drawing_name' 这个名字只有数据库中才有
//        这时候才发现 我要保存进的数据库中没有这个字段

        List<MeasurePlace> placeList = this.createMeasurePlaceList(user,list);
        List<MeasurePlace> existsPlaceList = this.findExistsMeasurePlace(user);
        this.deletePlaces(placeList,existsPlaceList);

        if(CollUtil.isNotEmpty(placeList)){
            this.saveOrUpdateBatch(placeList);

            for(MeasurePlace resultList : placeList){
                MeasureTask measureTask = new MeasureTask();
                measureTask.setCompanyId(user.getCurrentCompanyId());
                measureTask.setProjectId(user.getCurrentProjectId());
                measureTask.setPlaceName(StrUtil.format("-{}-{}-{}-{}-{}",resultList.getPlaceOne(),resultList.getPlaceTwo(),resultList.getPlaceThree(),resultList.getPlaceFour(),resultList.getPlaceFive()));
//                现在不给measuretask的主键
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("company_id",user.getCurrentCompanyId());
                wrapper.eq("project_id",user.getCurrentProjectId());

//              this.saveOrUpdate是针对MeasurePlace的
//                this.saveOrUpdate()

                measureTaskMapper.update(measureTask,wrapper);
            }
        }
    }

    /**
     * 批量保存测量部位
     */
//    上面的只是MeasurePlaceForm的list 但是要保存到数据库的是measurePlace的list
    List<MeasurePlace> createMeasurePlaceList(AuthUser user,List<MeasurePlaceForm> list){
        List<MeasurePlace> placeList = new ArrayList<>();
        int i = 1;
        LocalDateTime now = LocalDateTimeUtil.now();
        for(MeasurePlaceForm form :list){
            MeasurePlace place = new MeasurePlace();
            BeanUtil.copyProperties(form,place);
            place.setCompanyId(user.getCurrentCompanyId());
            place.setProjectId(user.getCurrentProjectId());
            place.setCreateId(user.getId());
            place.setGmtCreate(LocalDateTimeUtil.offset(now,i *  10, ChronoUnit.MILLIS));
            place.setIsDelete((DeleteEnum.NORMAL.value()));
            i++;
            placeList.add(place);

        }
        return placeList;

    }

    /**
     * 删除测量部位列表
     */
    @Override
    public void deleteMeasurePlace(int id){
        measurePlaceMapper.deleteById(id);
    }

    private List<MeasurePlace> findExistsMeasurePlace(AuthUser user){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("company_id",user.getCurrentCompanyId());
        wrapper.eq("project_id",user.getCurrentProjectId());
        wrapper.eq("is_delete",DeleteEnum.NORMAL.value());
        List<MeasurePlace> existsMeasurePlace = this.list(wrapper);
        return existsMeasurePlace;
    }

    private void deletePlaces(List<MeasurePlace> placeList,List<MeasurePlace> existsPlaceList){
        existsPlaceList.removeAll(placeList);
        for(MeasurePlace list : existsPlaceList){
            list.setIsDelete(DeleteEnum.DELETE.value());
        }
        this.updateBatchById(existsPlaceList);
    }

    @Override
    public MeasurePlaceVO selectMeasurePlaceByName(Integer companyId, Integer projectId, MeasurePlaceForm form) {
        return measurePlaceMapper.getMeasurePlaceByName(companyId,projectId,form);
    }

    @Override
    public List<MeasurePlaceVO> listPlace(AuthUser user){
        List<MeasurePlaceVO> result = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("project_id", user.getCurrentProjectId());
        wrapper.eq("company_id", user.getCurrentCompanyId());
        wrapper.eq("is_delete", DeleteEnum.NORMAL.value());
        wrapper.orderByDesc("gmt_create");
        List<MeasurePlace> list = this.list(wrapper);
        if(CollUtil.isNotEmpty(list)){
            BeanUtils.copyProperties(list,result);
        }
        return result;
    }

}
