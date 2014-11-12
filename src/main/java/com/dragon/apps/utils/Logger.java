package com.dragon.apps.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {

    public static void error(Class<?> clazz, String message, Throwable e) {
        Log log = LogFactory.getLog(clazz.getName());
        if (log.isErrorEnabled()) {
            if (e == null) {
                log.error(message);
            } else {
                log.error(message, e);
            }
        }
    }

    public static void error(Class<?> clazz, String message) {
        error(clazz, message, null);
    }

    public static void error(Object obj, String message, Throwable e) {
        error(obj.getClass(), message, e);
    }

    public static void error(Object obj, String message) {
        error(obj.getClass(), message);
    }


    public static void warn(Class<?> clazz, String message, Throwable e) {
        Log log = LogFactory.getLog(clazz.getName());
        if (log.isWarnEnabled()) {
            if (e == null) {
                log.warn(message);
            } else {
                log.warn(message, e);
            }
        }
    }

    public static void warn(Class<?> clazz, String message) {
        warn(clazz, message, null);
    }

    public static void warn(Object obj, String message, Throwable e) {
        warn(obj.getClass(), message, e);
    }

    public static void warn(Object obj, String message) {
        warn(obj.getClass(), message);
    }


    public static void info(Class<?> clazz, String message) {
        Log log = LogFactory.getLog(clazz.getName());
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    public static void info(Object obj, String message) {
        info(obj.getClass(), message);
    }

    public static void debug(Class<?> clazz, String message) {
        Log log = LogFactory.getLog(clazz.getName());
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    public static void debug(Object obj, String message) {
        debug(obj.getClass(), message);
    }


    public static boolean isInfoEnabled(Class<?> clazz) {
        return LogFactory.getLog(clazz.getName()).isInfoEnabled();
    }

    public static boolean isDebugEnabled(Class<?> clazz) {
        return LogFactory.getLog(clazz.getName()).isDebugEnabled();
    }

}
