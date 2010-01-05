/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.convert;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.ConvertUtil;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.component.html.THtmlInputFile;
import org.seasar.teeda.extension.util.TargetCommandUtil;
import org.seasar.teeda.extension.util.UploadedFile;

/**
 * @author koichik
 */
public abstract class AbstractUploadedFileConverter implements Converter,
        ConvertTargetSelectable, StateHolder {

    protected String objectMessageId;

    protected String stringMessageId;

    protected String target;

    protected String[] targets;

    protected boolean transientValue = false;

    public AbstractUploadedFileConverter() {
        objectMessageId = getClass().getName() + ".CONVERSION";
        stringMessageId = getClass().getName() + ".CONVERSION_STRING";
    }

    public Object getAsObject(final FacesContext context,
            final UIComponent component, final String value)
            throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (!ConverterHelper.isTargetCommand(context, component, targets, this)) {
            return null;
        }
        Object submittedValue = null;
        try {
            final THtmlInputFile inputFile = (THtmlInputFile) component;
            submittedValue = inputFile.getSubmittedValue();
            if (submittedValue instanceof UploadedFile) {
                return getAsObject(context, (UploadedFile) submittedValue);
            }
            return null;
        } catch (final Exception e) {
            final Object[] args = ConvertUtil.createExceptionMessageArgs(
                    component, submittedValue);
            throw new ConverterException(FacesMessageUtil.getMessage(context,
                    objectMessageId, args), e);
        }
    }

    protected abstract Object getAsObject(FacesContext context,
            UploadedFile uploadedFile) throws Exception;

    public String getAsString(final FacesContext context,
            final UIComponent component, final Object value)
            throws ConverterException {
        if (!isTargetCommandConvert(context, targets)) {
            return null;
        }
        return "";
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target;
        if (StringUtil.isEmpty(target)) {
            return;
        }
        targets = StringUtil.split(target, ", ");
    }

    public boolean isTargetCommandConvert(final FacesContext context,
            final String[] targets) {
        return TargetCommandUtil.isTargetCommand(context, targets);
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void setTransient(final boolean transientValue) {
        this.transientValue = transientValue;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        setTarget((String) values[0]);
        objectMessageId = (String) values[1];
        stringMessageId = (String) values[2];
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[3];
        values[0] = target;
        values[1] = objectMessageId;
        values[2] = stringMessageId;
        return values;
    }

    public String getObjectMessageId() {
        return objectMessageId;
    }

    public void setObjectMessageId(final String objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    public String getStringMessageId() {
        return stringMessageId;
    }

    public void setStringMessageId(final String stringMessageId) {
        this.stringMessageId = stringMessageId;
    }

}
