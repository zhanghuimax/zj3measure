package cn.pinming.microservice.measure.proxy.impl;

import cn.pinming.core.common.model.PageList;
import cn.pinming.microservice.measure.proxy.ProjectServiceProxy;
import cn.pinming.v2.common.QueryPagination;
import cn.pinming.v2.common.api.LocusAware;
import cn.pinming.v2.project.api.dto.ConstructionProjectDto;
import cn.pinming.v2.project.api.dto.ConstructionProjectQueryDto;
import cn.pinming.v2.project.api.dto.SimpleConstructionProjectDto;
import cn.pinming.v2.project.api.service.ConstructionProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 *
 * @author huangshuai
 * @date 2020-4-10
 */
@Component
@Slf4j
public class ProjectServiceProxyImpl implements ProjectServiceProxy {


    @Reference(parameters = {
            "findSimpleProject.mock","return null"
    })
    private ConstructionProjectService constructionProjectService;


    @Override
    public List<ConstructionProjectDto> findCompanyProjects(QueryPagination<Integer> companyIdQuery) {
        ConstructionProjectQueryDto query = new ConstructionProjectQueryDto();
        query.setCompanyId(companyIdQuery.getT());
        query.setPage(companyIdQuery.getPagination().getPage());
        query.setPageSize(companyIdQuery.getPagination().getPageSize());
        PageList<LocusAware<ConstructionProjectDto>> pageList = constructionProjectService.findProjectsWithLocus(query);
        List<LocusAware<ConstructionProjectDto>> list = pageList.getDataList();
        if(list != null){
            return list.stream().map(e -> e.getObj()).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Optional<String> findProjectName(Integer projectId){
        SimpleConstructionProjectDto simpleConstructionProjectDto = constructionProjectService.findSimpleProject(projectId);
        if(simpleConstructionProjectDto != null){
            return Optional.ofNullable(simpleConstructionProjectDto.getProjectTitle());
        }
        return Optional.empty();
    }

    private String getProjectName(Integer projectId) {
        ConstructionProjectDto constructionProjectDto = constructionProjectService.projectDetail(projectId);
        String projectName = "";
        if (constructionProjectDto != null) {
            projectName = constructionProjectDto.getProjectTitle();
        }
        return projectName;
    }

}
