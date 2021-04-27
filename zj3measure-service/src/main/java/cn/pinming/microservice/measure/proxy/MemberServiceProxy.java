package cn.pinming.microservice.measure.proxy;

import cn.pinming.v2.company.api.dto.EmployeeDto;
import cn.pinming.v2.passport.api.dto.MemberDto;
import cn.pinming.v2.project.api.dto.ConstructionProjectManDto;
import lombok.NonNull;

import java.util.Optional;

/**
 *
 * @author jin
 * @date 2020-03-06
 */
public interface MemberServiceProxy {

    /**
     * 企业人员
     * @param memberId
     * @param companyId
     * @return
     */
    Optional<EmployeeDto> findEmployee(@NonNull String memberId, @NonNull Integer companyId);

    Optional<ConstructionProjectManDto> findProjectMan(@NonNull String memberId, @NonNull Integer projectId);

    /**
     * 人员信息
     * @param memberId
     * @return
     */
    Optional<MemberDto> findMemberById(@NonNull String memberId);

    /**
     * 企业人员名称
     * @param memberId
     * @param companyId
     * @return
     */
    Optional<String> findMemberName(@NonNull String memberId, @NonNull Integer companyId);

    /**
     * 判断人员是否在项目中
     * @param projectId  项目id
     * @param userId     人员id
     * @return
     */
    Optional<Byte> projectUserType(Integer projectId, String userId);
}
