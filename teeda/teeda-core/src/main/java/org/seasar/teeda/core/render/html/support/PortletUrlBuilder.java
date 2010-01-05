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
package org.seasar.teeda.core.render.html.support;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import org.seasar.teeda.core.portlet.FacesPortlet;

public class PortletUrlBuilder extends UrlBuilder {
    public String build() {
        URI uri = URI.create(getBase());
        if (uri.getScheme() != null) {
            return super.build();
        }
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            String viewId = calculatePath(context.getViewRoot().getViewId(),
                    uri.getPath());
            ExternalContext externalContext = context.getExternalContext();
            if (externalContext.getResponse() instanceof RenderResponse) {
                RenderResponse response = (RenderResponse) externalContext
                        .getResponse();
                // uses ActionURL 
                PortletURL portletUrl = response.createActionURL();
                portletUrl.setParameter(FacesPortlet.VIEW_ID, viewId);
                portletUrl.setParameter(FacesPortlet.RENDER_PARAMETER, "true");

                if (uri.getQuery() != null) {
                    String[] queries = uri.getQuery().split("&");
                    for (int i = 0; i < queries.length; i++) {
                        int index = queries[i].indexOf("=");
                        if (index > 0) {
                            portletUrl.setParameter(queries[i].substring(0,
                                    index), queries[i].substring(index + 1));
                        }
                    }
                }
                for (final Iterator it = getUrlParameters().entrySet()
                        .iterator(); it.hasNext();) {
                    final Map.Entry entry = (Map.Entry) it.next();
                    final String key = (String) entry.getKey();
                    final UrlParameter parameter = (UrlParameter) entry
                            .getValue();
                    final String[] values = parameter.getValues();
                    for (int i = 0; i < values.length; i++) {
                        final String value = values[i];
                        portletUrl.setParameter(key, value);
                    }
                }
                return portletUrl.toString();
            }
        }
        return super.build();

    }

    protected String calculatePath(String currentPath, String path) {
        if (path.startsWith("/")) {
            return path;
        }
        String[] cPaths = currentPath.split("/");
        String[] nPaths = path.split("/");
        ArrayList list = new ArrayList();
        for (int i = 0; i < cPaths.length - 1; i++) {
            if (!cPaths[i].equals("")) {
                list.add(cPaths[i]);
            }
        }
        for (int i = 0; i < nPaths.length; i++) {
            if ("..".equals(nPaths[i])) {
                int size = list.size();
                if (size > 0) {
                    list.remove(size - 1);
                }
            } else if (".".equals(nPaths[i])) {
                // nothing
            } else {
                if (!nPaths[i].equals("")) {
                    list.add(nPaths[i]);
                }
            }
        }
        StringBuffer p = new StringBuffer("");
        for (int i = 0; i < list.size(); i++) {
            p.append("/");
            p.append(list.get(i));
        }
        return p.toString();
    }
}
