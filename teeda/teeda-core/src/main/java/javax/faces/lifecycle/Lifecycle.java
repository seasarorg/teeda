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
package javax.faces.lifecycle;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

/**
 * @author shot
 */
public abstract class Lifecycle {

    public abstract void addPhaseListener(PhaseListener listener);

    public abstract void execute(FacesContext context) throws FacesException;

    public abstract PhaseListener[] getPhaseListeners();

    public abstract void removePhaseListener(PhaseListener listener);

    public abstract void render(FacesContext context) throws FacesException;
}
