package cn.pinming.microservice.measure.biz.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.pinming.core.cookie.AuthUser;
import cn.pinming.microservice.measure.biz.entity.MeasureDrawing;
import cn.pinming.microservice.measure.biz.enums.DeleteEnum;
import cn.pinming.microservice.measure.biz.form.MeasureDrawingForm;
import cn.pinming.microservice.measure.biz.mapper.MeasureDrawingMapper;
import cn.pinming.microservice.measure.biz.service.IMeasureDrawingService;
import cn.pinming.microservice.measure.biz.service.IMeasurePlaceService;
import cn.pinming.microservice.measure.biz.vo.MeasureDrawingVO;
import cn.pinming.microservice.measure.biz.vo.MeasurePlaceVO;
import cn.pinming.microservice.measure.common.exception.BOException;
import cn.pinming.microservice.measure.common.exception.BOExceptionEnum;
import cn.pinming.microservice.measure.proxy.FileServiceProxy;
import cn.pinming.microservice.measure.proxy.MemberServiceProxy;
import cn.pinming.model.FileInfos;
import cn.pinming.model.dto.OssFileDto;
import cn.pinming.v2.common.api.dto.FileDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 实测实量-项目图纸 服务实现类
 * </p>
 *
 * @author zh
 * @since 2021-04-15
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
public class MeasureDrawingServiceImpl extends ServiceImpl<MeasureDrawingMapper, MeasureDrawing> implements IMeasureDrawingService {

    @Autowired
    private FileServiceProxy fileServiceProxy;
    @Autowired
    private MemberServiceProxy memberServiceProxy;
    @Reference
    private FileInfos fileInfos;
    @Resource
    private MeasureDrawingMapper measureDrawingMapper;

    @Override
    public Page findMeasureDrawingPageList(Page page, AuthUser user){
//        这个page里 是从数据库中是把所有的字段都输出 还是只拿页面要的那几个字段
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("company_id",user.getCurrentCompanyId());
        wrapper.eq("project_id",user.getCurrentProjectId());
        wrapper.eq("is_delete", DeleteEnum.NORMAL.value());
        Page result = this.page(page,wrapper);
//        return result;

//        现在要对取出来的数据进行操作，可能是在page中的数据不好操作 所以把page结果中的数据取出来放到list中
        List<MeasureDrawing> records = result.getRecords();
        List<MeasureDrawingVO> resultList = new ArrayList<>();
        if(CollUtil.isNotEmpty(records)){
//            需要把measuredrawing中的数据放到vo中，所有需要有个measuredrawingvo的list
//            传的时候不是一个list直接传过去，而是一条条数据传
            resultList = records.parallelStream().map(e -> {
                MeasureDrawingVO vo = new MeasureDrawingVO();
                BeanUtil.copyProperties(e,vo);

//                增加需求，判断传过来的数据如果有修改人id，就输出修改人name
                if(StrUtil.isNotBlank(e.getModifyId())){
                    String modifyNme = memberServiceProxy.findMemberName(e.getModifyId(),e.getCompanyId()).orElse("");
                    vo.setModifyName(modifyNme);
                }
//                增加需求 找创建人名字
                if(StrUtil.isNotBlank(e.getCreateId())){
                    String createName = memberServiceProxy.findMemberName(e.getCreateId(),e.getCompanyId()).orElse("" );
                    vo.setCreateName(createName);
                }
//                在页面显示的vo中 关联着文件，通过文件传输对象把文件的uuid传进去
//                这是把数据库中的文件传给页面，但我现在数据库中还没有文件呢，要怎么传进去啊
                FileDto fileDto = fileServiceProxy.findFilesByUUID(vo.getFileUuid());
                vo.setFileDto(fileDto);
                return vo;
            }).collect(Collectors.toList());
//            你现在这样只是在MeasureDrawingVO中增加了创建人和修改人名字的属性，你没有放到最终结果的result中啊
//            找到这个点 是因为如果你根据随便打的创建人修改人id去找name 如果没有对应的人的话 也会返回"" 但结果中并没有
//            现在看看会不会显示
            result.setRecords(resultList);
        }
        return result;
    }

    @Override
    public void addMeasureDrawing(MeasureDrawingForm form, AuthUser user){
//       this.save(form)
//        我一开始就想这样，  你能通过这个form找到对应的数据库的表吗？？实体类都是通过@TableName来映射的
//        你至少得把这个表单放到对应的实体类中吧
        MeasureDrawing drawing = new MeasureDrawing();
        BeanUtils.copyProperties(form,drawing);
        this.save(drawing);
//        保存一个图片一个文件其实是简单的，因为他们是通过uuid来保存，
//        需要注意的是取出来的时候要通过代理找到uuid

//        增加需求 判断重名 这个应该不是指图纸的id，每个图纸都有唯一的id，但一张图纸可以以不同的名字存在
////        判断的是一个工程里面是否有重复的图纸，所以得有projectid,需要有判断的图纸name，也不能有重复的id，不能在根据id找图纸的时候出来多张
        this.checkDrawingName(form.getDrawingId(),form.getName(),user.getCurrentProjectId());
    }

    /**
     * <p>
     * 判断重名
     */
    private void checkDrawingName(String drawingId,String name,Integer projectId){
        int count = this.lambdaQuery()
                .eq(MeasureDrawing::getProjectId,projectId)
                .ne(StrUtil.isNotBlank(drawingId),MeasureDrawing::getDrawingId,drawingId)
                .eq(MeasureDrawing::getName,name)
                .eq(MeasureDrawing::getIsDelete,DeleteEnum.NORMAL.value()).count();
        if(count > 0){
            throw new BOException(BOExceptionEnum.DRAWING_IS_EXIST);
        }
    }

    @Override
    public void updateMeasureDrawing(MeasureDrawingForm form,AuthUser user){
        this.checkDrawingName(form.getDrawingId(),form.getName(),user.getCurrentProjectId());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("company_id",user.getCurrentCompanyId());
        wrapper.eq("project_id",user.getCurrentProjectId());
        wrapper.eq("drawing_id",form.getDrawingId());
        MeasureDrawing drawing = new MeasureDrawing();
        BeanUtils.copyProperties(form,drawing);
        this.update(drawing,wrapper);

        if(StrUtil.isNotBlank(form.getFileUuid())){
            this.bindOssFile(form);
        }

//        更新了之后 这个文件之前的uuid会没吗
//        是的 不见了 所以得重新绑定
//        不知道是因为我没有实质性地用uuid绑定文件 还是因为更新了之后确实会丢失uuid的连接
//        this.bindOssFile(form);
    }

    private void bindOssFile(MeasureDrawingForm form){
        OssFileDto ossFileDto = new OssFileDto();
        ossFileDto.setFileId(form.getFileUuid());
        fileInfos.addFileToFileServer(ossFileDto);
    }

    @Override
    public void deleteMeasureDrawing(Integer id){
        measureDrawingMapper.deleteById(id);
    }



}
