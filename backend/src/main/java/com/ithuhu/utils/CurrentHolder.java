package com.ithuhu.utils;

public class CurrentHolder {

    private static final ThreadLocal<Long> tl = new ThreadLocal<>();

    public static void setCurrentUserId(Long userId) {
        tl.set(userId);
    }

    public static Long getCurrentUserId() {
        return tl.get();
    }

    public static void remove() {
        tl.remove();
    }
}
