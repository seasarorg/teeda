/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.application;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shot
 * @author manhole
 */
public class FacesMessage implements Serializable {

    private static final long serialVersionUID = 3258125843346766128L;

    public static final String FACES_MESSAGES = "javax.faces.Messages";

    public static final Severity SEVERITY_INFO = new Severity("INFO", 1);

    public static final Severity SEVERITY_WARN = new Severity("WARN", 2);

    public static final Severity SEVERITY_ERROR = new Severity("ERROR", 3);

    public static final Severity SEVERITY_FATAL = new Severity("FATAL", 4);

    private static final Severity[] values = { SEVERITY_INFO, SEVERITY_WARN,
            SEVERITY_ERROR, SEVERITY_FATAL };

    public static final List VALUES;

    public static final Map VALUES_MAP;

    static {
        VALUES = Collections.unmodifiableList(Arrays.asList(values));
        Map map = new HashMap();
        for (int i = 0; i < values.length; i++) {
            map.put(values[i].toString(), values[i]);
        }
        VALUES_MAP = Collections.unmodifiableMap(map);
    }

    private String summary_;

    private String detail_;

    private Severity severity_;

    public FacesMessage() {
        severity_ = SEVERITY_INFO;
    }

    public FacesMessage(String summary) {
        severity_ = SEVERITY_INFO;
        summary_ = summary;
    }

    public FacesMessage(String summary, String detail) {
        severity_ = SEVERITY_INFO;
        summary_ = summary;
        detail_ = detail;
    }

    public FacesMessage(Severity severity, String summary, String detail) {
        setSeverity(severity);
        summary_ = summary;
        detail_ = detail;
    }

    public String getDetail() {
        return (detail_ != null) ? detail_ : summary_;
    }

    public void setDetail(String detail) {
        detail_ = detail;
    }

    public Severity getSeverity() {
        return severity_;
    }

    public void setSeverity(Severity severity) {
        if (!VALUES.contains(severity)) {
            throw new IllegalArgumentException("severity");
        }
        severity_ = severity;
    }

    public String getSummary() {
        return summary_;
    }

    public void setSummary(String summary) {
        summary_ = summary;
    }

    public static class Severity extends Object implements Comparable {
        private String type_;

        private int ordinal_;

        public Severity(String type, int ordinal) {
            type_ = type;
            ordinal_ = ordinal;
        }

        public int getOrdinal() {
            return ordinal_;
        }

        public String toString() {
            return type_;
        }

        public int compareTo(Object o) {
            return ordinal_ - ((Severity) o).ordinal_;
        }
    }
}
