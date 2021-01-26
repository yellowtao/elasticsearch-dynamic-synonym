package com.ginobefunny.elasticsearch.plugins.synonym.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

import java.util.WeakHashMap;

public class PrefixLogger extends ExtendedLoggerWrapper {
    private static final WeakHashMap<String, Marker> markers = new WeakHashMap();
    private final Marker marker;

    static int markersSize() {
        return markers.size();
    }

    public String prefix() {
        return this.marker.getName();
    }

    PrefixLogger(Logger logger, String prefix) {
        super((ExtendedLogger)logger, logger.getName(), (MessageFactory)null);
        if (prefix != null && !prefix.isEmpty()) {
            Marker actualMarker;
            synchronized(markers) {
                Marker maybeMarker = (Marker)markers.get(prefix);
                if (maybeMarker == null) {
                    actualMarker = new MarkerManager.Log4jMarker(prefix);
                    markers.put(new String(prefix), actualMarker);
                } else {
                    actualMarker = maybeMarker;
                }
            }

            this.marker = (Marker)actualMarker;
        } else {
            throw new IllegalArgumentException("if you don't need a prefix then use a regular logger");
        }
    }

    @Override
    public void logMessage(String fqcn, Level level, Marker marker, Message message, Throwable t) {
        assert marker == null;

        super.logMessage(fqcn, level, this.marker, message, t);
    }
}
