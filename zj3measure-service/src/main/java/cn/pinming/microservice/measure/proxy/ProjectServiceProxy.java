package cn.pinming.microservice.measure.proxy;

import cn.pinming.v2.common.QueryPagination;
import cn.pinming.v2.project.api.dto.ConstructionProjectDto;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author jin
 * @date 2020-03-06
 */
public interface ProjectServiceProxy {

    /**
     * 分页项目列表
     * @param companyIdQuery
     * @return
     */
    List<ConstructionProjectDto> findCompanyProjects(@NonNull QueryPagination<Integer> companyIdQuery);

    /**
     * 获取项目名称
     * @param projectId 项目id
     * @return 获取项目名称
     */
    Optional<String> findProjectName(Integer projectId);
}
