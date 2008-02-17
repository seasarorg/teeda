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
package org.seasar.teeda.extension.portlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.portlet.ActionRequest;
import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.util.ExternalMessageUtil;

/**
 * @author shinsuke
 * 
 */
public class MultipartFormDataActionRequestWrapper implements ActionRequest {

    public static final String WWW_FORM_URLENCODED_TYPE = "application/x-www-form-urlencoded";

    private static final Logger logger = Logger
            .getLogger(MultipartFormDataActionRequestWrapper.class);

    protected PortletFileUpload fileUpload;

    protected Map parameters = new HashMap(64);

    protected Map fileItems = new HashMap(64);

    protected String encoding = null;

    private ActionRequest request;

    /**
     * @param request 
     * @param maxSize
     * @param maxFileSize 
     * @param thresholdSize 
     * @param repositoryPath 
     */
    public MultipartFormDataActionRequestWrapper(final ActionRequest request,
            final int maxSize, final int maxFileSize, final int thresholdSize,
            final String repositoryPath, final String encoding) {
        this.request = request;
        final DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(thresholdSize);
        if (!StringUtil.isEmpty(repositoryPath)) {
            factory.setRepository(new File(repositoryPath));
        }
        fileUpload = new PortletFileUpload(factory);
        fileUpload.setSizeMax(maxSize);
        fileUpload.setFileSizeMax(maxFileSize);
        this.encoding = encoding;
        parseRequest(request);
    }

    protected void parseRequest(final ActionRequest request) {
        try {
            String enc = encoding;
            if (enc == null) {
                enc = request.getCharacterEncoding();
            }
            final List requestParameters = fileUpload.parseRequest(request);
            for (final Iterator it = requestParameters.iterator(); it.hasNext();) {
                final FileItem fileItem = (FileItem) it.next();
                if (fileItem.isFormField()) {
                    final String name = fileItem.getFieldName();
                    String value = fileItem.getString(enc);
                    addParameter(name, value);
                } else {
                    fileItems.put(fileItem.getFieldName(), fileItem);
                }
            }
        } catch (final SizeLimitExceededException e) {
            logger.log("ETDA0110", new Object[0], e);
            ExternalMessageUtil.addMessage(request,
                    ExtensionConstants.FILE_UPLOAD_SIZE_ERROR_MESSAGE,
                    new Object[] { new Long(e.getPermittedSize()),
                            new Long(e.getActualSize()) });
        } catch (final FileSizeLimitExceededException e) {
            logger.log("ETDA0110", new Object[0], e);
            ExternalMessageUtil.addMessage(request,
                    ExtensionConstants.FILE_UPLOAD_FILE_SIZE_ERROR_MESSAGE,
                    new Object[] { new Long(e.getPermittedSize()),
                            new Long(e.getActualSize()) });
        } catch (final Exception e) {
            logger.log("ETDA0110", new Object[0], e);
            ExternalMessageUtil.addMessage(request,
                    ExtensionConstants.FILE_UPLOAD_ERROR_MESSAGE);
        }

        for (final Iterator it = request.getParameterMap().entrySet()
                .iterator(); it.hasNext();) {
            final Entry entry = (Entry) it.next();
            final String name = (String) entry.getKey();
            final String[] values = (String[]) entry.getValue();
            addParameters(name, values);
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
        if (ExtensionConstants.REQUEST_ATTRIBUTE_UPLOADED_FILES.equals(string)) {
            return getFileItems();
        }
        return request.getAttribute(string);
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

    public String getCharacterEncoding() {
        if (encoding != null) {
            return encoding;
        }
        return request.getCharacterEncoding();
    }

    public Enumeration getAttributeNames() {
        return request.getAttributeNames();
    }

    public String getAuthType() {
        return request.getAuthType();
    }

    public int getContentLength() {
        return request.getContentLength();
    }

    public String getContextPath() {
        return request.getContextPath();
    }

    public Locale getLocale() {
        return request.getLocale();
    }

    public Enumeration getLocales() {
        return request.getLocales();
    }

    public PortalContext getPortalContext() {
        return request.getPortalContext();
    }

    public InputStream getPortletInputStream() throws IOException {
        return request.getPortletInputStream();
    }

    public PortletMode getPortletMode() {
        return request.getPortletMode();
    }

    public PortletSession getPortletSession() {
        return request.getPortletSession();
    }

    public PortletSession getPortletSession(boolean arg0) {
        return request.getPortletSession(arg0);
    }

    public PortletPreferences getPreferences() {
        return request.getPreferences();
    }

    public Enumeration getProperties(String arg0) {
        return request.getProperties(arg0);
    }

    public String getProperty(String arg0) {
        return request.getProperty(arg0);
    }

    public Enumeration getPropertyNames() {
        return request.getPropertyNames();
    }

    public BufferedReader getReader() throws UnsupportedEncodingException,
            IOException {
        return request.getReader();
    }

    public String getRemoteUser() {
        return request.getRemoteUser();
    }

    public String getRequestedSessionId() {
        return request.getRequestedSessionId();
    }

    public String getResponseContentType() {
        return request.getResponseContentType();
    }

    public Enumeration getResponseContentTypes() {
        return request.getResponseContentTypes();
    }

    public String getScheme() {
        return request.getScheme();
    }

    public String getServerName() {
        return request.getServerName();
    }

    public int getServerPort() {
        return request.getServerPort();
    }

    public Principal getUserPrincipal() {
        return request.getUserPrincipal();
    }

    public WindowState getWindowState() {
        return request.getWindowState();
    }

    public boolean isPortletModeAllowed(PortletMode arg0) {
        return request.isPortletModeAllowed(arg0);
    }

    public boolean isRequestedSessionIdValid() {
        return request.isRequestedSessionIdValid();
    }

    public boolean isSecure() {
        return request.isSecure();
    }

    public boolean isUserInRole(String arg0) {
        return request.isUserInRole(arg0);
    }

    public boolean isWindowStateAllowed(WindowState arg0) {
        return request.isWindowStateAllowed(arg0);
    }

    public void removeAttribute(String arg0) {
        request.removeAttribute(arg0);
    }

    public void setAttribute(String arg0, Object arg1) {
        request.setAttribute(arg0, arg1);
    }

    public void setCharacterEncoding(String arg0)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding(arg0);
    }

    public ActionRequest getRequest() {
        return request;
    }
}
