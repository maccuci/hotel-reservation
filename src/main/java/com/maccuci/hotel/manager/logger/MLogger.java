package com.maccuci.hotel.manager.logger;

import java.util.Arrays;

public class MLogger {

    private String prefix;

    public MLogger(String prefix) {
        this.prefix = prefix;
    }

    public void log(String... message) {
        log(Level.DEFAULT, null, message);
    }

    public void log(Level level, String... message) {
        log(level, null, message);
    }

    public void log(Level level, Throwable throwable, String... message) {
        if(throwable == null)
            System.out.printf("[%s - %s] %s", prefix, level.getPrefix(), Arrays.toString(message));
        else
            System.out.printf("[%s - %s] %s\nError: %s", prefix, level.getPrefix(), Arrays.toString(message), throwable.getMessage());
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public enum Level {
        SUCCESS("SUCCESS"),
        WARNING("WARNING"),
        ERROR("ERROR"),
        DEFAULT(" ");

        final String prefix;

        Level(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
