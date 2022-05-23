package com.ubs.sis.admin.auth.annotation;

import com.ubs.sis.admin.auth.policy.PolicyEnforcement;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GrantsAspect {

    private final PolicyEnforcement policyEnforcement;

    public GrantsAspect(PolicyEnforcement policyEnforcement) {
        this.policyEnforcement = policyEnforcement;
    }

    @Before("@annotation(assignGrant)")
    public void before(JoinPoint joinPoint, AssignGrant assignGrant) {
        //policyEnforcement.check(assignGrant.component(), assignGrant.action());
    }
}
