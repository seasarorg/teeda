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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.UploadedFile;

/**
 * @author koichik
 */
public class TUploadedFileStringConverter extends AbstractUploadedFileConverter {

    protected String encoding;

    public Object getAsObject(final FacesContext context,
            final UploadedFile uploadedFile) throws Exception {
        if (StringUtil.isEmpty(encoding)) {
            return uploadedFile.getString();
        }
        return uploadedFile.getString(encoding);
    }

    public String getAsString(final FacesContext context,
            final UIComponent component, final Object value)
            throws ConverterException {
        if (value == null || !(value instanceof String)) {
            return "";
        }
        return (String) value;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        encoding = (String) values[1];
    }

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = encoding;
        return values;
    }

}
