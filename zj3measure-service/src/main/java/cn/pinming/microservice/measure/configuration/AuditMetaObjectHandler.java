package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.cookie.AuthUser;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 审计对象变更
 * 如 createId modifyId gmtCreate gmtModify
 * Created by jin on 2020-03-06.
 */
@Component
@Slf4j
public class AuditMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private SiteContextHolder siteContextHolder;

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime localDateNow = LocalDateTime.now(ZoneId.of("GMT+8"));
        if (log.isDebugEnabled()) {
            log.debug("now is {}", localDateNow);
        }
        this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime.class, localDateNow);
        this.strictInsertFill(metaObject, "gmtModify", LocalDateTime.class, localDateNow);
        AuthUser currentUser = siteContextHolder.getCurrentUser();
        if (metaObject.hasGetter("createId")) {
            if (currentUser != null) {
                String id = currentUser.getId();
                String createId = (String)this.getFieldValByName("createId", metaObject);
                if (createId == null) {
                    this.strictInsertFill(metaObject, "createId", String.class, id);
                    this.strictInsertFill(metaObject, "modifyId", String.class, id);
                }
            }
        }

        if (metaObject.hasGetter("companyId")) {
            if (currentUser != null) {
                Integer companyId = currentUser.getCurrentCompanyId();
                this.strictInsertFill(metaObject, "companyId", Integer.class, companyId);
            }
        }

        if (metaObject.hasGetter("projectId")) {
            if (currentUser != null) {
                Integer projectId = currentUser.getCurrentProjectId();
                this.strictInsertFill(metaObject, "projectId", Integer.class, projectId);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime localDateNow = LocalDateTime.now(ZoneId.of("GMT+8"));
        if (log.isDebugEnabled()) {
            log.debug("now is {}", localDateNow);
        }
        this.strictUpdateFill(metaObject, "gmtModify", LocalDateTime.class, localDateNow);
        AuthUser currentUser = siteContextHolder.getCurrentUser();
        if (metaObject.hasGetter("modifyId")) {
            if (currentUser != null) {
                String id = currentUser.getId();
                String modifyId = (String)this.getFieldValByName("modifyId", metaObject);
                if (modifyId == null) {
                    this.strictUpdateFill(metaObject, "modifyId", String.class, id);
                }
            }
        }
    }
}
