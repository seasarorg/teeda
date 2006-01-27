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
package org.seasar.teeda.core.el;

import javax.faces.application.Application;
import javax.faces.el.MethodBinding;

/**
 * @author Shinpei Ohtani
 */
public interface MethodBindingContext {

    public void setMethodBindingName(String methodBindingName);
    
    public String getMethodBindingName();
    
    public void setValueBindingContext(ValueBindingContext valueBindingContext);
    
    public MethodBinding createMethodBinding(Application application, String ref, Class[] params);
}
