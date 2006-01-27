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

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

/**
 * @author higa
 *  
 */

public class SimpleMethodBinding extends MethodBinding implements StateHolder {

	private String outcome_;
	private boolean transient_ = false;

	public SimpleMethodBinding() {
	}
	
	public SimpleMethodBinding(String outcome) {
		outcome_ = outcome;
	}

	public Object invoke(FacesContext context, Object args[])
			throws EvaluationException, MethodNotFoundException {

		return outcome_;
	}

	public Class getType(FacesContext context) throws MethodNotFoundException {
		return String.class;
	}

	public Object saveState(FacesContext context) {
		return outcome_;
	}

	public void restoreState(FacesContext context, Object obj) {
		outcome_ = (String) obj;
	}

	public boolean isTransient() {
		return transient_;
	}

	public void setTransient(boolean b) {
		transient_ = b;
	}
}