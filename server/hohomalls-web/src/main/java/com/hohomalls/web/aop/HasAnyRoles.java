package com.hohomalls.web.aop;

import com.hohomalls.web.common.Role;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasAnyRoles {

  Role[] value();
}
