package com.ganatan.backend_java.multitenancy;

public interface TenantAware {
    void setTenantId(String tenantId);
}
