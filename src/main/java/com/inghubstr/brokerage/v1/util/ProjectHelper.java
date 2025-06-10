package com.inghubstr.brokerage.v1.util;

import com.inghubstr.brokerage.v1.model.user.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ProjectHelper {

    public static Long getLoggedInUserId() {
        UserDetailsImpl userDto = getLoggedInUserImpl();
        if (Objects.isNull(userDto)) {
            return 1L;
        }
        return userDto.getId();
    }

    public static UserDetailsImpl getLoggedInUserImpl() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) return null;
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user;
    }

}
