package com.ganatan.backend_java.multitenancy;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class TenantListener extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof TenantAware) {
            String tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                for (int i = 0; i < propertyNames.length; i++) {
                    if ("tenantId".equals(propertyNames[i])) {
                        state[i] = tenantId;
                        return true;
                    }
                }
            }
        }
        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof TenantAware) {
            String tenantId = TenantContext.getTenantId();
            if (tenantId != null) {
                for (int i = 0; i < propertyNames.length; i++) {
                    if ("tenantId".equals(propertyNames[i])) {
                        currentState[i] = tenantId;
                        return true;
                    }
                }
            }
        }
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }
}
