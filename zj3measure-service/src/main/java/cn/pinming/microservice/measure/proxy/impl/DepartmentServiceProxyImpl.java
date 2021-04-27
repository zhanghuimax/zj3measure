package cn.pinming.microservice.measure.proxy.impl;

import cn.pinming.microservice.measure.proxy.DepartmentServiceProxy;
import cn.pinming.v2.company.api.dto.department.DepartmentDto;
import cn.pinming.v2.company.api.service.SpecialDepartmentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DepartmentServiceProxyImpl implements DepartmentServiceProxy {

    @Reference
    private SpecialDepartmentService specialDepartmentService;

    @Override
    public Optional<DepartmentDto> findParentOrganizationDepartment(Integer departmentId) {
        return Optional.ofNullable(specialDepartmentService.findParentOrganizationDepartment(departmentId));
    }
}
