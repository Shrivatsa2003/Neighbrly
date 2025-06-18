package com.projects.Neighbrly.Neighbrly.utils;

import com.projects.Neighbrly.Neighbrly.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class userUtil {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
