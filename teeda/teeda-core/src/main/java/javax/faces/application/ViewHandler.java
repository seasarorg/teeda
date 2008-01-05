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
package javax.faces.application;

import java.io.IOException;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public abstract class ViewHandler {

    public static final String CHARACTER_ENCODING_KEY = "javax.faces.request.charset";

    public static final String DEFAULT_SUFFIX = ".jsp";

    public static final String DEFAULT_SUFFIX_PARAM_NAME = "javax.faces.DEFAULT_SUFFIX";

    public abstract Locale calculateLocale(FacesContext context);

    public abstract String calculateRenderKitId(FacesContext context);

    public abstract UIViewRoot createView(FacesContext context, String viewId);

    public abstract String getActionURL(FacesContext context, String viewId);

    public abstract String getResourceURL(FacesContext context, String path);

    public abstract void renderView(FacesContext context, UIViewRoot viewRoot)
            throws IOException, FacesException;

    public abstract UIViewRoot restoreView(FacesContext context, String viewId);

    public abstract void writeState(FacesContext context) throws IOException;
}
