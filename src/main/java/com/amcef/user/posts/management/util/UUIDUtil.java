package com.amcef.user.posts.management.util;

import java.util.UUID;

/**
 * @author Michal Remis
 */
public class UUIDUtil {

    private UUIDUtil() {
        // This class should not be instantiated
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
