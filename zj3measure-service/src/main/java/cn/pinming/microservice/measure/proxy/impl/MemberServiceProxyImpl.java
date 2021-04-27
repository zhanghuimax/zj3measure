package cn.pinming.microservice.measure.proxy.impl;

import cn.pinming.microservice.measure.proxy.MemberServiceProxy;
import cn.pinming.v2.company.api.dto.EmployeeDto;
import cn.pinming.v2.company.api.service.EmployeeService;
import cn.pinming.v2.passport.api.dto.MemberDto;
import cn.pinming.v2.passport.api.service.MemberService;
import cn.pinming.v2.project.api.dto.ConstructionProjectManDto;
import cn.pinming.v2.project.api.service.ConstructionProjectManService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *
 * @author huangshuai
 * @date 2020-4-10
 */
@Component
@Slf4j
public class MemberServiceProxyImpl implements MemberServiceProxy {


    @Reference(mock = "return null")
    private MemberService memberService;

    @Reference(mock = "return null")
    private EmployeeService employeeService;

    @Reference(mock = "return null")
    private ConstructionProjectManService constructionProjectManService;

    @Override
    public Optional<EmployeeDto> findEmployee(@NonNull String memberId, @NonNull Integer companyId) {
        return Optional.ofNullable(employeeService.findEmployee(companyId,memberId));
    }

    @Override
    public Optional<ConstructionProjectManDto> findProjectMan(@NonNull String memberId, @NonNull Integer projectId) {
        return Optional.ofNullable(constructionProjectManService.findProjectMan(memberId,projectId));
    }

    @Override
    public Optional<MemberDto> findMemberById(@NonNull String memberId) {
        return Optional.ofNullable(memberService.findMember(memberId));
    }

    @Override
    public Optional<String> findMemberName(@NonNull String memberId, @NonNull Integer companyId) {
        Optional<EmployeeDto> employeeOptional = findEmployee(memberId,companyId);
        if(employeeOptional.isPresent()){
            return employeeOptional.map(EmployeeDto::getMemberName);
        }
        return findMemberById(memberId).map(MemberDto::getMemberName);
    }

    @Override
    public Optional<Byte> projectUserType(Integer projectId, String memberId) {
        if (projectId == null || memberId == null) {
            return Optional.of((byte) 0);
        }
        return Optional.ofNullable(constructionProjectManService.projectUserType(memberId, projectId));
    }
}
