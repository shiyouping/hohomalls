package com.hohomalls.web.aop;

import com.hohomalls.web.common.Role;

import java.lang.annotation.*;

/**
 * HasAllRoles.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 23/12/2021
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasAllRoles {

  Role[] value();
}
