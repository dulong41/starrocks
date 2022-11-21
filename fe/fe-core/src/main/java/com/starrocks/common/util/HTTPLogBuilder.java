// This file is licensed under the Elastic License 2.0. Copyright 2021-present, StarRocks Inc.
package com.starrocks.common.util;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class HTTPLogBuilder {
    private final StringBuilder sb;
    private final List<LogEntry> entries;

    public HTTPLogBuilder(String identifier) {
        sb = new StringBuilder(identifier).append("-");
        entries = Lists.newLinkedList();
    }

    public HTTPLogBuilder(LogKey key, Long identifier) {
        sb = new StringBuilder().append(key.name()).append("=").append(identifier).append(", ");
        entries = Lists.newLinkedList();
    }

    public HTTPLogBuilder(LogKey key, UUID identifier) {
        sb = new StringBuilder().append(key.name()).append("=").append(DebugUtil.printId(identifier)).append(", ");
        entries = Lists.newLinkedList();
    }

    public HTTPLogBuilder(LogKey key, String identifier) {
        sb = new StringBuilder().append(key.name()).append("=").append(identifier).append(", ");
        entries = Lists.newLinkedList();
    }

    public HTTPLogBuilder add(String key, long value) {
        entries.add(new LogEntry(key, String.valueOf(value)));
        return this;
    }

    public HTTPLogBuilder add(String key, int value) {
        entries.add(new LogEntry(key, String.valueOf(value)));
        return this;
    }

    public HTTPLogBuilder add(String key, float value) {
        entries.add(new LogEntry(key, String.valueOf(value)));
        return this;
    }

    public HTTPLogBuilder add(String key, boolean value) {
        entries.add(new LogEntry(key, String.valueOf(value)));
        return this;
    }

    public HTTPLogBuilder add(String key, String value) {
        entries.add(new LogEntry(key, String.valueOf(value)));
        return this;
    }

    public HTTPLogBuilder add(String key, Object value) {
        if (value == null) {
            entries.add(new LogEntry(key, "null"));
        } else {
            entries.add(new LogEntry(key, value.toString()));
        }
        return this;
    }

    public String build() {
        Iterator<LogEntry> it = entries.iterator();
        while (it.hasNext()) {
            LogEntry logEntry = it.next();
            sb.append(logEntry.key).append(":{").append(logEntry.value).append("}");
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private class LogEntry {
        String key;
        String value;

        public LogEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return build();
    }
}