package io.tenants.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

import static io.tenants.database.BaseTenant.TENANT_FILTER;
import static io.tenants.database.BaseTenant.TENANT_PARAMETER;

@Aspect
@Component
public class TenantAspect {

    @Pointcut(value = "@annotation(io.tenants.database.CurrentTenant)")
    void hasCurrentTenantAnnotation() {
        // NOOP
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Around("execution(public * *(..)) && hasCurrentTenantAnnotation()")
    public Object aroundExecution(ProceedingJoinPoint pjp) throws Throwable {
        var methodName = pjp.getSignature().getName();
        var methodSignature = (MethodSignature) pjp.getSignature();
        var method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            method = pjp.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
        }
        var currentTenantAnnotation = method.getAnnotation(CurrentTenant.class);
        if (null != currentTenantAnnotation) {
            Serializable tenantId = TenantContextHolder.getContext().getTenantId();
            var filter = entityManager
                    .unwrap(Session.class)
                    .enableFilter(TENANT_FILTER)
                    .setParameter(TENANT_PARAMETER, tenantId);
            filter.validate();
        }
        return pjp.proceed();
    }
}
