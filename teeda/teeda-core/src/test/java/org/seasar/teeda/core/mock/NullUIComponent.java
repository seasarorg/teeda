/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

/**
 * @author manhole
 */
public class NullUIComponent extends UIComponent {

    public Map getAttributes() {
        return null;
    }

    public ValueBinding getValueBinding(String name) {
        return null;
    }

    public void setValueBinding(String s, ValueBinding valueBinding) {
    }

    public String getClientId(FacesContext facescontext) {
        return null;
    }

    public void setClientId(String clientId) {
    }

    public String getFamily() {
        return null;
    }

    public String getId() {
        return null;
    }

    public void setId(String id) {
    }

    public UIComponent getParent() {
        return null;
    }

    public void setParent(UIComponent parent) {
    }

    public boolean isRendered() {
        return false;
    }

    public void setRendered(boolean flag) {
    }

    public String getRendererType() {
        return null;
    }

    public void setRendererType(String type) {
    }

    public boolean getRendersChildren() {
        return false;
    }

    public List getChildren() {
        return null;
    }

    public int getChildCount() {
        return 0;
    }

    public UIComponent findComponent(String expr) {
        return null;
    }

    public Map getFacets() {
        return null;
    }

    public UIComponent getFacet(String s) {
        return null;
    }

    public Iterator getFacetsAndChildren() {
        return null;
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
    }

    public void decode(FacesContext context) {
    }

    public void encodeBegin(FacesContext context) throws IOException {
    }

    public void encodeChildren(FacesContext context) throws IOException {
    }

    public void encodeEnd(FacesContext context) throws IOException {
    }

    protected void addFacesListener(FacesListener listener) {
    }

    protected FacesListener[] getFacesListeners(Class clazz) {
        return null;
    }

    protected void removeFacesListener(FacesListener listener) {
    }

    public void queueEvent(FacesEvent event) {
    }

    public void processRestoreState(FacesContext context, Object state) {
    }

    public void processDecodes(FacesContext context) {
    }

    public void processValidators(FacesContext context) {
    }

    public void processUpdates(FacesContext context) {
    }

    public Object processSaveState(FacesContext context) {
        return null;
    }

    protected FacesContext getFacesContext() {
        return null;
    }

    protected Renderer getRenderer(FacesContext context) {
        return null;
    }

    public boolean isTransient() {
        return false;
    }

    public void setTransient(boolean transientValue) {
    }

    public Object saveState(FacesContext context) {
        return null;
    }

    public void restoreState(FacesContext context, Object state) {
    }

}
