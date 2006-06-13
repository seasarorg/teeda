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
package org.seasar.teeda.core.mock;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.AspectDef;
import org.seasar.framework.container.AutoBindingDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.CyclicReferenceRuntimeException;
import org.seasar.framework.container.DestroyMethodDef;
import org.seasar.framework.container.Expression;
import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.InterTypeDef;
import org.seasar.framework.container.MetaDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.TooManyRegistrationRuntimeException;

/**
 * @author shot
 * 
 */
public class NullComponentDefImpl implements ComponentDef {

    public Object getComponent() throws TooManyRegistrationRuntimeException,
            CyclicReferenceRuntimeException {
        return null;
    }

    public void injectDependency(Object outerComponent) {
    }

    public S2Container getContainer() {
        return null;
    }

    public void setContainer(S2Container container) {
    }

    public Class getComponentClass() {
        return null;
    }

    public String getComponentName() {
        return null;
    }

    public void setComponentName(String componentName) {
    }

    public Class getConcreteClass() {
        return null;
    }

    public AutoBindingDef getAutoBindingDef() {
        return null;
    }

    public void setAutoBindingDef(AutoBindingDef autoBindingDef) {
    }

    public InstanceDef getInstanceDef() {
        return null;
    }

    public void setInstanceDef(InstanceDef instanceDef) {
    }

    public Expression getExpression() {
        return null;
    }

    public void setExpression(Expression Expression) {
    }

    public boolean isExternalBinding() {
        return false;
    }

    public void setExternalBinding(boolean externalBinding) {
    }

    public void init() {
    }

    public void destroy() {
    }

    public void addArgDef(ArgDef argDef) {
    }

    public int getArgDefSize() {
        return 0;
    }

    public ArgDef getArgDef(int index) {
        return null;
    }

    public void addInterTypeDef(InterTypeDef interTypeDef) {
    }

    public int getInterTypeDefSize() {
        return 0;
    }

    public InterTypeDef getInterTypeDef(int index) {
        return null;
    }

    public void addPropertyDef(PropertyDef propertyDef) {
    }

    public int getPropertyDefSize() {
        return 0;
    }

    public PropertyDef getPropertyDef(int index) {
        return null;
    }

    public PropertyDef getPropertyDef(String propertyName) {
        return null;
    }

    public boolean hasPropertyDef(String propertyName) {
        return false;
    }

    public void addInitMethodDef(InitMethodDef methodDef) {
    }

    public int getInitMethodDefSize() {
        return 0;
    }

    public InitMethodDef getInitMethodDef(int index) {
        return null;
    }

    public void addDestroyMethodDef(DestroyMethodDef methodDef) {
    }

    public int getDestroyMethodDefSize() {
        return 0;
    }

    public DestroyMethodDef getDestroyMethodDef(int index) {
        return null;
    }

    public void addAspectDef(AspectDef aspectDef) {
    }

    public int getAspectDefSize() {
        return 0;
    }

    public AspectDef getAspectDef(int index) {
        return null;
    }

    public void addMetaDef(MetaDef metaDef) {
    }

    public int getMetaDefSize() {
        return 0;
    }

    public MetaDef getMetaDef(int index) {
        return null;
    }

    public MetaDef getMetaDef(String name) {
        return null;
    }

    public MetaDef[] getMetaDefs(String name) {
        return null;
    }

}
