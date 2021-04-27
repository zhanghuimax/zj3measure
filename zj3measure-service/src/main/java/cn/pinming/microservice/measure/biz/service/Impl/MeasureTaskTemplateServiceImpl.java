package cn.pinming.microservice.measure.biz.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.dto.TaskTemplateDTO;
import cn.pinming.microservice.measure.biz.entity.MeasureItem;
import cn.pinming.microservice.measure.biz.entity.MeasureStandard;
import cn.pinming.microservice.measure.biz.entity.MeasureTaskTemplate;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.enums.EnabledEnum;
import cn.pinming.microservice.measure.biz.form.MeasureItemForm;
import cn.pinming.microservice.measure.biz.form.MeasureStandardForm;
import cn.pinming.microservice.measure.biz.form.MeasureTaskTemplateForm;
import cn.pinming.microservice.measure.biz.mapper.MeasureItemMapper;
import cn.pinming.microservice.measure.biz.mapper.MeasureStandardMapper;
import cn.pinming.microservice.measure.biz.mapper.MeasureTaskTemplateMapper;
import cn.pinming.microservice.measure.biz.query.MeasureItemQuery;
import cn.pinming.microservice.measure.biz.query.MeasureStandardQuery;
import cn.pinming.microservice.measure.biz.query.MeasureTaskTemplateQuery;
import cn.pinming.microservice.measure.biz.service.IMeasureItemService;
import cn.pinming.microservice.measure.biz.service.IMeasureStandardService;
import cn.pinming.microservice.measure.biz.service.IMeasureTaskTemplateService;
import cn.pinming.microservice.measure.biz.vo.MeasureItemVO;
import cn.pinming.microservice.measure.biz.vo.MeasureStandardVO;
import cn.pinming.microservice.measure.biz.vo.MeasureTaskTemplateVO;
import cn.pinming.microservice.measure.common.exception.BOException;
import cn.pinming.microservice.measure.common.exception.BOExceptionEnum;
import cn.pinming.microservice.measure.proxy.MemberServiceProxy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MeasureTaskTemplateServiceImpl extends ServiceImpl<MeasureTaskTemplateMapper,MeasureTaskTemplate> implements IMeasureTaskTemplateService {

    @Resource
    private IMeasureItemService measureItemService;
    @Resource
    private MeasureTaskTemplateMapper taskTemplateMapper;
    @Resource
    private MemberServiceProxy memberServiceProxy;
    @Resource
    private IMeasureStandardService measureStandardService;
    @Qualifier("caffeineCache")
    @Resource
    private Cache<String,Object> caffeineCache;
    @Resource
    private MeasureItemMapper measureItemMapper;
    @Resource
    private MeasureStandardMapper measureStandardMapper;

    /**
     * 根据搜索 查看实测指标模板
     */
    @Override
    public Page<TaskTemplateDTO> listMeasureTaskTemplate(MeasureTaskTemplateQuery query){
        Page<TaskTemplateDTO> page = taskTemplateMapper.selectTemplateList(query);
        List<TaskTemplateDTO> list = page.getRecords();
        if(CollUtil.isNotEmpty(list)){
            list.forEach(e ->{
                if(StrUtil.isNotBlank(e.getCreateId())){
                    String createName = memberServiceProxy.findMemberName(e.getCreateId(),query.getCompanyId()).orElse("");
                    e.setCreateName(createName);
                }
                if(StrUtil.isNotBlank(e.getModifyId())){
                    String modifyName = memberServiceProxy.findMemberName(e.getModifyId(),query.getCompanyId()).orElse("");
                    e.setModifyName(modifyName);
                }
            });

        }
        return page;
    }

    /**
     * 新增实测指标模板
     */
    @Override
    public void insertTaskTemplate(MeasureTaskTemplateForm form){
//        保存测量模板
        MeasureTaskTemplate template = new MeasureTaskTemplate();
        BeanUtils.copyProperties(form,template,"setting");

        template.setTemplateId(IdUtil.simpleUUID());
        template.setSetting(JSONUtil.toJsonStr(form.getSetting()));
        template.setCheckItemCount(CollUtil.isEmpty(form.getMeasures()) ? 0 : form.getMeasures().size());
        template.setMeasurePositionCount(this.countTotalPoint(form.getMeasures()));

        taskTemplateMapper.insert(template);

//        保存测量分项
        this.saveMeasureItem(form.getMeasures(),template.getTemplateId(),template.getCompanyId());
    }

    /**
     * 计算测量点总数
     * @param itemList  分项列表
     */
    private int countTotalPoint(List<MeasureItemForm> itemList){
        return CollUtil.isNotEmpty(itemList) ? 0 : this.computePositionCount(itemList);
    }

    /**
     * 测量点数量 = 各分项的测量区数量 * 每区测量点数
     * @return 测量点数量
     */
    private int computePositionCount(List<MeasureItemForm> measureItemList){
        return measureItemList.stream().mapToInt(form ->(form.getAreaPointCount() * form.getMeasureAreaCount())).sum();
//        我一开始写的是
//  private Integer countTotalPoint(List<MeasureItemForm> list){
//      int count = 0;
//      for(MeasureItemForm result : list){
//          count = result.getMeasureAreaCount() * result.getAreaPointCount() + count;
//      }
//      return count;
//    }
    }

    /**
     * 保存测量分项
     *
     * @param itemList   分项列表
     * @param templateId 模板id
     */
    private void saveMeasureItem(List<MeasureItemForm> itemList,String templateId,Integer companyId){
        if(CollUtil.isNotEmpty(itemList)){
            for(MeasureItemForm list : itemList){
                list.setTemplateId(templateId);
                list.setCompanyId(companyId);
                String itemId = measureItemService.saveMeasureItem(list);
                if(CollUtil.isNotEmpty(list.getStandards())){
//                    this.saveMeasureStandard(list.getStandards(),list.getItemId(),list.getCompanyId());
                    this.saveMeasureStandard(list.getStandards(),itemId,list.getCompanyId());
                }
            }
        }
    }
    /**
     * 测量分项评判标准
     *
     * @param measureStandard
     * @param itemId
     */
    private void saveMeasureStandard(List<MeasureStandardForm> measureStandard,String itemId,Integer companyId){
//        加判断重名
        for(MeasureStandardForm standard : measureStandard){
            standard.setItemId(itemId);
            standard.setCompanyId(companyId);
            measureStandardService.updateMeasureStandard(standard);
        }
    }

    /**
     * 测量模板详情
     *
     * @param template
     */
    @Override
    public MeasureTaskTemplateVO getTaskTemplateDetail(String template){
        caffeineCache.asMap().remove(template);
        if(caffeineCache.getIfPresent(template) != null){
            return (MeasureTaskTemplateVO) caffeineCache.asMap().get(template);
        }

        MeasureTaskTemplateVO detail = new MeasureTaskTemplateVO();
//        对应的实体类接收，copy到vo
//          只用template就能找到唯一的数据
        MeasureTaskTemplate result = taskTemplateMapper.selectById(template);

        Optional.ofNullable(template).orElseThrow(() -> new BOException(BOExceptionEnum. TASK_TEMPLATE_EXITS));

        BeanUtils.copyProperties(result,detail,"setting");

//        测量分项
        MeasureItemQuery query = new MeasureItemQuery();
        query.setTemplateId(result.getTemplateId());
//        每个测量分项都有测量区数量和每区测量点数量，说明这个标准运用到不同的公司，针对不同公司有对应的数据列表，所以需要个companyId
//        不过这个templateId难道不是唯一的吗
//        可能是为了提高查找效率把
        query.setCompanyId(result.getCompanyId());
        List<MeasureItemVO> itemList = measureItemService.findMeasureItemList(query);
        detail.setTemplateItem(itemList);

//        测量分项标准
        for(MeasureItemVO item : itemList){
            MeasureStandardQuery standardQuery = new MeasureStandardQuery();
            standardQuery.setCompanyId(result.getCompanyId());
            standardQuery.setItemId(item.getItemId());
            List<MeasureStandardVO> list = measureStandardService.findMeasureStandardList(standardQuery);
            item.setStandards(list);
        }
        caffeineCache.put(template,detail);
        return detail;

    }

    /**
     * 编辑测量模板
     *
     * @param form
     */
    @Override
    public void updateMeasureTemplate(MeasureTaskTemplateForm form){
        MeasureTaskTemplate template = new MeasureTaskTemplate();
        BeanUtils.copyProperties(form,template);
        template.setCheckItemCount(CollUtil.isEmpty(form.getMeasures()) ? 0 : form.getMeasures().size());
        template.setMeasurePositionCount(this.countTotalPoint(form.getMeasures()));

//      测量分项
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("company_id",form.getCompanyId());
        wrapper.eq("template_id",form.getTemplateId());
        wrapper.eq("is_delete", DeleteEnum.NORMAL.value());
        wrapper.select("item_id");
//        这个列表就是根据template_id找出来的
        List<MeasureItem> itemList = measureItemMapper.selectList(wrapper);
        if(CollUtil.isNotEmpty(itemList)){
//            批量删除测量分项下的标准
            List<String> itemId = itemList.stream().map(e -> e.getItemId()).collect(Collectors.toList());
//            measureStandardMapper.deleteBatchIds(itemId);
            measureStandardMapper.deleteMeasureStandardList(itemId);
        }
        measureItemMapper.deleteBatchIds(itemList.stream().map(e -> e.getItemId()).collect(Collectors.toList()));
        this.saveMeasureItem(form.getMeasures(),form.getTemplateId(),form.getCompanyId());

        taskTemplateMapper.updateById(template);

        caffeineCache.asMap().remove(form.getTemplateId());
    }

    /**
     * 删除测量模板
     *
     * @param template
     */
    @Override
    public void deleteTaskTemplate(MeasureTaskTemplate template){
        caffeineCache.asMap().remove(template.getTemplateId());
        taskTemplateMapper.updateById(template);
    }

//    /**
//     * 查看移动端测量任务模板列表
//     *
//     */
//    @Override
//    public Map<String, List> findTemplateList(AuthUser user, boolean isMobile){
//        List<MeasureTaskTemplate> templateList = this.findTemplateIdList(user);
//        Map result = new HashMap();
//        if(CollUtil.isNotEmpty(templateList)){
//            if(isMobile){
//                this.treeListCachedTemplate(result,templateList);
//            }
//        }
//
//    }
//
//    private List<MeasureTaskTemplate> findTemplateIdList(AuthUser user){
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("company_id",user.getCurrentProjectId());
//        wrapper.eq("is_delete",DeleteEnum.NORMAL.value());
//        wrapper.eq("enabled", EnabledEnum.ENABLE.value());
//        wrapper.select("template_id","one_name","two_name","type_id");
//        return this.list(wrapper);
//    }
//
////    根据templatelist的每个数据的id，去获得模板详情
//    private void treeListCachedTemplate(Map result,List<MeasureTaskTemplate> templateList){
//        List<MeasureTaskTemplateVO> list = templateList.stream().map(e -> {
//            MeasureTaskTemplateVO templateDetail = getTaskTemplateDetail(e.getTemplateId());
//            return templateDetail;
//        }).collect(Collectors.toList());
//
//        Map<String,List<MeasureTaskTemplateVO>> mapList = list.parallelStream().collect(Collectors.groupingBy(e -> e.getOneName()));
//        mapList.forEach((k,v) -> {
//            List nodeList = new ArrayList();
//        });
//    }
//
//
}
