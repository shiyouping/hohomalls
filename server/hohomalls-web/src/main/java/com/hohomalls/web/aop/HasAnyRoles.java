package com.hohomalls.web.aop;

import com.hohomalls.core.enumeration.Role;

import java.lang.annotation.*;

/**
 * HasAnyRoles.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 23/12/2021
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasAnyRoles {

  Role[] value();
}
