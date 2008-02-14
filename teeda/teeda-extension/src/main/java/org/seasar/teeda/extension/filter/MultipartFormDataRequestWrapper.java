/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.filter;

import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author koichik
 */
public class MultipartFormDataRequestWrapper extends HttpServletRequestWrapper {

    public static final String WWW_FORM_URLENCODED_TYPE = "application/x-www-form-urlencoded";

    private static final Logger logger = Logger
            .getLogger(MultipartFormDataRequestWrapper.class);

    protected ServletFileUpload fileUpload;

    protected Map parameters = new HashMap(64);

    protected Map fileItems = new HashMap(64);

    /**
     * @param request 
     * @param maxFileSize 
     * @param thresholdSize 
     * @param repositoryPath 
     */
    public MultipartFormDataRequestWrapper(final HttpServletRequest request,
            final int maxFileSize, final int thresholdSize,
            final String repositoryPath) {
        super(request);
        final DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(thresholdSize);
        if (!StringUtil.isEmpty(repositoryPath)) {
            factory.setRepository(new File(repositoryPath));
        }
        fileUpload = new ServletFileUpload(factory);
        fileUpload.setFileSizeMax(maxFileSize);
        parseRequest(request);
    }

    protected void parseRequest(final HttpServletRequest request) {
        try {
            final String encoding = request.getCharacterEncoding();
            final List requestParameters = fileUpload.parseRequest(request);
            for (final Iterator it = requestParameters.iterator(); it.hasNext();) {
                final FileItem fileItem = (FileItem) it.next();
                if (fileItem.isFormField()) {
                    final String name = fileItem.getFieldName();
                    final String value = fileItem.getString(encoding);
                    addParameter(name, value);
                } else {
                    fileItems.put(fileItem.getFieldName(), fileItem);
                }
            }

            for (final Iterator it = request.getParameterMap().entrySet()
                    .iterator(); it.hasNext();) {
                final Entry entry = (Entry) it.next();
                final String name = (String) entry.getKey();
                final String[] values = (String[]) entry.getValue();
                addParameters(name, values);
            }
        } catch (final Exception e) {
            logger.log("ETDA0110", new Object[0], e);
        }
    }

    public Enumeration getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    public String getParameter(final String name) {
        final String[] values = (String[]) parameters.get(name);
        return values == null ? null : values[0];
    }

    public String[] getParameterValues(final String name) {
        return (String[]) parameters.get(name);
    }

    public Map getParameterMap() {
        return parameters;
    }

    public Object getAttribute(final String string) {
        if (string.equals(ExtensionConstants.REQUEST_ATTRIBUTE_UPLOADED_FILES)) {
            return getFileItems();
        }
        return super.getAttribute(string);
    }

    public String getContentType() {
        return WWW_FORM_URLENCODED_TYPE;
    }

    public FileItem getFileItem(final String fieldName) {
        return (FileItem) fileItems.get(fieldName);
    }

    public Map getFileItems() {
        return fileItems;
    }

    protected void addParameter(final String name, final String value) {
        if (!parameters.containsKey(name)) {
            parameters.put(name, new String[] { value });
        } else {
            final String[] oldValues = (String[]) parameters.get(name);
            final String[] newValues = (String[]) ArrayUtil.add(oldValues,
                    value);
            parameters.put(name, newValues);
        }
    }

    protected void addParameters(final String name, final String[] values) {
        if (!parameters.containsKey(name)) {
            parameters.put(name, values);
        } else {
            final String[] oldValues = (String[]) parameters.get(name);
            final String[] newValues = (String[]) ArrayUtil.add(oldValues,
                    values);
            parameters.put(name, newValues);
        }
    }

}
