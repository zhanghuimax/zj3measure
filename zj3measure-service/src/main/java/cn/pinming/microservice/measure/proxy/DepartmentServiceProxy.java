package cn.pinming.microservice.measure.proxy;

import cn.pinming.v2.company.api.dto.department.DepartmentDto;
import lombok.NonNull;

import java.util.Optional;

public interface DepartmentServiceProxy {


    Optional<DepartmentDto> findParentOrganizationDepartment(@NonNull Integer departmentId);
}
