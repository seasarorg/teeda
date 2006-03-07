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
package javax.faces.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhaseId {

    public static final PhaseId ANY_PHASE;

    public static final PhaseId APPLY_REQUEST_VALUES;

    public static final PhaseId INVOKE_APPLICATION;

    public static final PhaseId PROCESS_VALIDATIONS;

    public static final PhaseId RENDER_RESPONSE;

    public static final PhaseId RESTORE_VIEW;

    public static final PhaseId UPDATE_MODEL_VALUES;

    private static final String ANY_PHASE_NAME = "ANY_PHASE";

    private static final String RESTORE_VIEW_NAME = "RESTORE_VIEW";

    private static final String APPLY_REQUEST_VALUES_NAME = "APPLY_REQUEST_VALUES";

    private static final String PROCESS_VALIDATIONS_NAME = "PROCESS_VALIDATIONS";

    private static final String UPDATE_MODEL_VALUES_NAME = "UPDATE_MODEL_VALUES";

    private static final String INVOKE_APPLICATIONS_NAME = "INVOKE_APPLICATIONS";

    private static final String RENDER_RESPONSE_NAME = "RENDER_RESPONSE";

    public static final List VALUES;

    private final String name_;

    private final int ordinal_;

    static {
        int i = 0;
        List list = new ArrayList();

        ANY_PHASE = new PhaseId(ANY_PHASE_NAME, i++);
        RESTORE_VIEW = new PhaseId(RESTORE_VIEW_NAME, i++);
        APPLY_REQUEST_VALUES = new PhaseId(APPLY_REQUEST_VALUES_NAME, i++);
        PROCESS_VALIDATIONS = new PhaseId(PROCESS_VALIDATIONS_NAME, i++);
        UPDATE_MODEL_VALUES = new PhaseId(UPDATE_MODEL_VALUES_NAME, i++);
        INVOKE_APPLICATION = new PhaseId(INVOKE_APPLICATIONS_NAME, i++);
        RENDER_RESPONSE = new PhaseId(RENDER_RESPONSE_NAME, i++);

        list.add(ANY_PHASE);
        list.add(RESTORE_VIEW);
        list.add(APPLY_REQUEST_VALUES);
        list.add(PROCESS_VALIDATIONS);
        list.add(UPDATE_MODEL_VALUES);
        list.add(INVOKE_APPLICATION);
        list.add(RENDER_RESPONSE);

        VALUES = Collections.unmodifiableList(list);
    }

    private PhaseId(String name, int ordinal) {
        this.name_ = name;
        this.ordinal_ = ordinal;
    }

    public int compareTo(Object o) {
        return ordinal_ - ((PhaseId) o).ordinal_;
    }

    public int getOrdinal() {
        return ordinal_;
    }

    public String toString() {
        return name_ + ":" + ordinal_;
    }

}
