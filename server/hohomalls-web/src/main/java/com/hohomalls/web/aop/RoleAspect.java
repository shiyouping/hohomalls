package com.hohomalls.web.aop;

import com.hohomalls.core.exception.InternalServerException;
import com.hohomalls.core.util.ArrayUtil;
import com.hohomalls.web.common.Role;
import com.hohomalls.web.service.SessionService;
import com.hohomalls.web.util.HttpHeaderUtil;
import com.netflix.graphql.dgs.exceptions.InvalidDgsConfigurationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Aspect: An aspect is a class that implements enterprise application concerns that cut across
 * multiple classes, such as transaction management. Aspects can be a normal class configured
 * through Spring XML configuration or we can use Spring AspectJ integration to define a class as
 * Aspect using @Aspect annotation.
 *
 * <p>Join Point: A join point is a specific point in the application such as method execution,
 * exception handling, changing object variable values, etc. In Spring AOP a join point is always
 * the execution of a method.
 *
 * <p>Advice: Advices are actions taken for a particular join point. In terms of programming, they
 * are methods that get executed when a certain join point with matching pointcut is reached in the
 * application. You can think of Advices as Struts2 interceptors or Servlet Filters.
 *
 * <p>Pointcut: Pointcut is expressions that are matched with join points to determine whether
 * advice needs to be executed or not. Pointcut uses different kinds of expressions that are matched
 * with the join points and Spring framework uses the AspectJ pointcut expression language.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/7/2021
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RoleAspect {

  private final SessionService sessionService;

  /**
   * DGS framework has a bug so @EnableReactiveMethodSecurity doesn't work with @PreAuthorize
   * in @DgsComponent.
   *
   * <p>Using Spring AOP is just a temporary workaround.
   *
   * <p>See the bug reported at https://github.com/Netflix/dgs-framework/issues/458
   */
  @Before(
      "@annotation(com.netflix.graphql.dgs.DgsData) || "
          + "@annotation(com.netflix.graphql.dgs.DgsQuery) || "
          + "@annotation(com.netflix.graphql.dgs.DgsMutation) || "
          + "@annotation(com.netflix.graphql.dgs.DgsSubscription)")
  public void checkRoles(JoinPoint joinPoint) {

    var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    var hasAnyRoles = method.getAnnotation(HasAnyRoles.class);
    var hasAllRoles = method.getAnnotation(HasAllRoles.class);

    if (hasAllRoles != null && hasAnyRoles != null) {
      throw new InvalidDgsConfigurationException(
          "HasAllRoles clashes with HasAnyRoles. Use them separately");
    }

    if (hasAllRoles == null && hasAnyRoles == null) {
      throw new InvalidDgsConfigurationException(
          "HasAllRoles or HasAnyRoles must be defined together with DGS components");
    }

    if (hasAnyRoles != null) {
      checkHasAnyRoles(joinPoint, method, hasAnyRoles);
      return;
    }

    checkHasAllRoles(joinPoint, method, Objects.requireNonNull(hasAllRoles));
  }

  private void checkHasAllRoles(JoinPoint joinPoint, Method method, HasAllRoles hasAllRoles) {
    var roles = Arrays.asList(hasAllRoles.value());
    if (roles.isEmpty()) {
      throw new InvalidDgsConfigurationException("No roles found in hasAllRoles annotation");
    }

    var auth = getAuth(joinPoint.getArgs(), method);
    if (!roles.stream().allMatch(role -> hasRole(auth, role))) {
      throw new AccessDeniedException("No authorization");
    }
  }

  private void checkHasAnyRoles(JoinPoint joinPoint, Method method, HasAnyRoles hasAnyRoles) {
    var roles = Arrays.asList(hasAnyRoles.value());
    if (roles.isEmpty()) {
      throw new InvalidDgsConfigurationException("No roles found in HasAnyRoles annotation");
    }

    if (roles.contains(Role.ROLE_ANONYMOUS)) {
      return;
    }

    var auth = getAuth(joinPoint.getArgs(), method);
    if (roles.stream().noneMatch(role -> hasRole(auth, role))) {
      throw new AccessDeniedException("No authorization");
    }
  }

  private Authentication getAuth(Object[] args, Method method) {
    try {
      var token = getAuthToken(args, method);
      return sessionService.getAuthentication(token).toFuture().get();
    } catch (InterruptedException | ExecutionException ex) {
      log.error("Failed to get session from the database");
      throw new InternalServerException(ex);
    }
  }

  private String getAuthToken(Object[] args, Method method) {
    var parameters = method.getParameters();
    var exception = new InvalidDgsConfigurationException("No authorization header found");

    if (ArrayUtil.isEmpty(parameters)) {
      throw exception;
    }

    var annotations = method.getParameterAnnotations();
    for (int i = 0; i < annotations.length; i++) {
      if (annotations[i].length > 0 && annotations[i][0] instanceof RequestHeader) {
        return HttpHeaderUtil.getAuth((String) args[i]).orElseThrow(() -> exception);
      }
    }

    throw exception;
  }

  private boolean hasRole(Authentication authentication, Role role) {
    return authentication.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals(role.name()));
  }
}
