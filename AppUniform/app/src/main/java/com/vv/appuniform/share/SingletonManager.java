package com.vv.appuniform.share;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Singleton manager.
 * you need to register instance before you get it.
 */
public class SingletonManager {
    /**
     * objectMap of all singleton service
     */
    private static Map<ServiceType, Object> objectMap = new HashMap<>();

    /**
     * The constant DATA_SERVICE.
     */
    public static final String DATA_SERVICE = "data";

    /**
     * The interface Log type.
     */
    @StringDef({DATA_SERVICE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ServiceType {
    }

    private SingletonManager() {
    }

    /**
     * Register service.
     *
     * @param key      the key
     * @param instance the instance
     */
    public static void registerService(ServiceType key, Object instance) {
        if (!objectMap.containsKey(key)) {
            objectMap.put(key, instance);
        }
    }

    /**
     * Gets service.
     *
     * @param key the key
     * @return the service
     */
    public static Object getService(ServiceType key) {
        return objectMap.get(key);
    }
}
