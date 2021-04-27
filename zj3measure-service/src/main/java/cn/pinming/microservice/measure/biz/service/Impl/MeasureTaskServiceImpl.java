package cn.pinming.microservice.measure.biz.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.pinming.core.common.util.StringUtil;
import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.dto.MeasureTaskDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureTask;
import cn.pinming.microservice.measure.biz.enums.CheckTypeEnum;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.enums.MeasureTaskStatusEnum;
import cn.pinming.microservice.measure.biz.mapper.MeasureTaskMapper;
import cn.pinming.microservice.measure.biz.query.MeasureTaskQuery;
import cn.pinming.microservice.measure.biz.service.IMeasureTaskService;
import cn.pinming.microservice.measure.biz.vo.StatusTaskVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.deploy.util.JVMParameters;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 实测实量--任务表 服务实现类
 * </p>
 *
 * @author zh
 * @since 2021-04-19
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MeasureTaskServiceImpl extends ServiceImpl<MeasureTaskMapper, MeasureTask> implements IMeasureTaskService {

    @Resource
    private MeasureTaskMapper taskMapper;

    /**
     * app-测量任务（指派待测任务、待测复测任务、待整改任务）
     */
    @Override
    public StatusTaskVO findSpreadTaskStatus(AuthUser user){
        StatusTaskVO vo = new StatusTaskVO();
        QueryWrapper wrapper = new QueryWrapper<>()
            .eq("company_id",user.getCurrentCompanyId())
            .eq("project_id",user.getCurrentProjectId())
            .eq("member_id",user.getId())
            .eq("is_delete", DeleteEnum.NORMAL.value());
        List<MeasureTask> taskList = taskMapper.selectList(wrapper);
        if(CollUtil.isNotEmpty(taskList)){
//            以任务状态为key，所以 Map<Byte,List<MeasureTask>> 的第一个参数为Byte，有该字段的实例类的list集合放在map里
            Map<Byte,List<MeasureTask>> statusCollectMap = taskList.parallelStream().collect(Collectors.groupingBy(e -> e.getStatus()));
            statusCollectMap.forEach((k,v) -> {
//                指派待测
                if(k.equals(MeasureTaskStatusEnum.ASSIGNMENT_TO_BE_TESTED.value())){
                    vo.setAssignedTestStatus(v.size() > 0);
                }
//                待复测
                if(k.equals(MeasureTaskStatusEnum.WAITING_FOR_TESTED.value())){
                    vo.setWaitingRetestStatus(v.size() > 0);
                }
//                待整改
                if(k.equals(MeasureTaskStatusEnum.WAITING_FOR_RECTIFICATION.value())){
                    vo.setWaitingRetestStatus(v.size() > 0);
                }
            });
        }
        return vo;
    }

    /**
     * 任务测量记录列表
     *
     * @param query 查询条件
     * @return 任务测量记录
     */
    @Override
    public Page<MeasureTaskDTO> getMeasureTaskList(MeasureTaskQuery query, boolean isWeb){
        if(isWeb){
            query.setMemberId(null);
        }
//        根据查询信息找到对应的数据
        Page<MeasureTaskDTO> page = taskMapper.findMeasureTaskList(query);
        List<MeasureTaskDTO> list = page.getRecords();
        if(CollUtil.isNotEmpty(list)){
            for(MeasureTaskDTO measureTaskDTO : list){
                this.encapsulateEntity(measureTaskDTO);
            }
        }
        return page;
    }

    private MeasureTaskDTO encapsulateEntity(MeasureTaskDTO measureTaskDTO){
//        measureTaskDTO.setCheckTypeName(CheckTypeEnum.getDesc(measureTaskDTO.getCheckType()));
//        if("混凝土强度".equals(measureTaskDTO.getTypeName())){
//            measureTaskDTO.setPassRate(null);
//        }else {
//            //        合格率 合格点数 / 当前测量点总数，这些就在measuretaskdto中
//            measureTaskDTO.setPassRate(this.computerProjectPassRate(measureTaskDTO));
//        }
//        BigDecimal measureProcess = this.computerMeasureProgress(measureTaskDTO);
//        measureTaskDTO.setMeasureProgress(measureProcess);
//
//        MeasureTask task = MeasureTask.builder().progress(measureTaskDTO.getMeasureProgress()).passRate(measureTaskDTO.getPassRate()).build();
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("task_id",measureTaskDTO.getTaskId());
//        this.update(task,wrapper);
//        return measureTaskDTO;


//        检查类型名称,看企业级还是项目级
        measureTaskDTO.setCheckTypeName(CheckTypeEnum.getDesc(measureTaskDTO.getCheckType()));
//        测量类型名称
        if("混凝土强度".equals(measureTaskDTO.getTypeName())){
            measureTaskDTO.setPassRate(null);
        }else{
//            返回合格率  合格点数 除以 当前完成测量点数
            measureTaskDTO.setPassRate(this.computerProjectPassRate(measureTaskDTO));
        }

//        计算测量进度  完成测量点数 / 已经测量的点数
//        BigDecimal measureProgress = this.computerMeasureProgress(measureTaskDTO);
//        measureTaskDTO.setMeasureProgress(measureProgress);
        measureTaskDTO.setMeasureProgress(this.computerMeasureProgress(measureTaskDTO));
//        设置合格率,builder()生成一个measureTask对象
        MeasureTask task = MeasureTask.builder().progress(measureTaskDTO.getMeasureProgress()).passRate(measureTaskDTO.getPassRate()).build();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("task_id",measureTaskDTO.getTaskId());
        taskMapper.update(task,wrapper);
        return measureTaskDTO;
    }

    /**
     * 计算合格率
     * 当前任务 合格点数 除以 当前完成测量点数
     *
     * @return 计算合格率
     */
    private BigDecimal computerProjectPassRate(MeasureTaskDTO measureTaskDTO){
//        当前完成测量点数
        if(StringUtils.isEmpty(measureTaskDTO.getCurrentPoints()) || measureTaskDTO.getCurrentPoints() == 0){
            return BigDecimal.ZERO;
        }
//        合格的测量点数
        if(StringUtils.isEmpty(measureTaskDTO.getQualifiedPoints()) || measureTaskDTO.getQualifiedPoints() == 0){
            return BigDecimal.ZERO;
        }
//        计算合格率
        BigDecimal passRate = BigDecimal.valueOf(NumberUtil.div(100.00 * measureTaskDTO.getQualifiedPoints(),measureTaskDTO.getCurrentPoints().floatValue(),2));
        return passRate;
    }

    /**
     * 计算测量进度
     * 当前任务 完成测量点数 / 已经测量的点数
     *
     * @return 测量进度
     */
    private BigDecimal computerMeasureProgress(MeasureTaskDTO measureTaskDTO){
        if(StringUtils.isEmpty(measureTaskDTO.getCurrentPoints())){
            return BigDecimal.ZERO;
        }
        if(StringUtils.isEmpty(measureTaskDTO.getTotalPoints()) || measureTaskDTO.getTotalPoints() == 0){
            return BigDecimal.ZERO;
        }
        return NumberUtil.div(100.00 * measureTaskDTO.getCurrentPoints(),BigDecimal.valueOf(measureTaskDTO.getTotalPoints()),2);
    }
}
