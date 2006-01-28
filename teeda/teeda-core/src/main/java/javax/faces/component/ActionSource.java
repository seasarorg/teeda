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
package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.ActionListener;

/**
 * @author shot
 */
public interface ActionSource {

	public MethodBinding getAction();

	public void setAction(MethodBinding action);

	public MethodBinding getActionListener();

	public void setActionListener(MethodBinding actionListener);

	public boolean isImmediate();

	public void setImmediate(boolean immediate);

	public void addActionListener(ActionListener listener);

	public ActionListener[] getActionListeners();

	public void removeActionListener(ActionListener listener);
}
