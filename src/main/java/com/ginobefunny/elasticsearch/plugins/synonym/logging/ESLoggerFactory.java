package com.ginobefunny.elasticsearch.plugins.synonym.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ESLoggerFactory {
    private ESLoggerFactory() {
    }

    static public Logger getLogger(String name) {
        return getLogger("", LogManager.getLogger(name));
    }

    static Logger getLogger(String prefix, String name) {
        return getLogger(prefix, LogManager.getLogger(name));
    }

    static Logger getLogger(String prefix, Class<?> clazz) {
        return getLogger(prefix, LogManager.getLogger(clazz.getName()));
    }

    static Logger getLogger(String prefix, Logger logger) {
        return prefix != null && prefix.length() != 0 ? new PrefixLogger(logger, prefix) : logger;
    }
}
