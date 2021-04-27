package cn.pinming.microservice.measure.configuration;

import cn.pinming.core.cookie.AuthUser;
import cn.pinming.core.cookie.support.AuthUserHolder;
import cn.pinming.core.web.exception.UnauthorizedException;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;


/**
 * @author tcz
 * @date 2020/02/19
 */
@Component
public class SiteContextHolder implements AuthUserHolder {

    private final ThreadLocal<AuthUser> authUserThreadLocal = new ThreadLocal<>();

    public AuthUser getNonNullCurrentUser() {
        AuthUser currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new UnauthorizedException();
        }
        return currentUser;
    }

    @Override
    @Nullable
    public AuthUser getCurrentUser() {
        return authUserThreadLocal.get();
    }

    @Override
    public void setCurrentUser(AuthUser user) {
        authUserThreadLocal.set(user);
    }

    @Override
    public void removeCurrentUser() {
        authUserThreadLocal.remove();
    }
}
