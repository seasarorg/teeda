/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package javax.faces.el;

/**
 * @author shot
 */
public class ReferenceSyntaxException extends EvaluationException {

    private static final long serialVersionUID = 3689069551740336181L;

    public ReferenceSyntaxException() {
        super();
    }

    public ReferenceSyntaxException(String message) {
        super(message);
    }

    public ReferenceSyntaxException(Throwable cause) {
        super(cause);
    }

    public ReferenceSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
