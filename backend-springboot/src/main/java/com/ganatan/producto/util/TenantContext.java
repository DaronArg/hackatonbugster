package com.ganatan.producto.util;

public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static String getCurrentTenant() {
        // En una implementación real, si es nulo, podría lanzar una excepción.
        // Para este ejemplo, devolvemos un valor por defecto si no está configurado.
        return currentTenant.get() != null ? currentTenant.get() : "default_tenant";
    }

    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static void clear() {
        currentTenant.remove();
    }
}
