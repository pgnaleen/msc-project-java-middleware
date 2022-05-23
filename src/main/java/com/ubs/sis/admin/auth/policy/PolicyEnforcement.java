package com.ubs.sis.admin.auth.policy;

public interface PolicyEnforcement {

    boolean check(String component, String action);
}
