package com.conquer_java.util.log4j_util;

import org.apache.log4j.Logger;
import sun.reflect.Reflection;

public class Log4JUtil {
    private static Logger logger = null;
    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger(Reflection.getCallerClass().getName());
            logger.debug("调用者类：" + Reflection.getCallerClass().getName());
        }
        return logger;
    }
}
