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
package org.seasar.teeda.core.config.webapp.rule;

import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.core.config.faces.handler.SimpleStringTagHandler;
import org.seasar.teeda.core.config.webapp.handler.ContextParamTagHandler;
import org.seasar.teeda.core.config.webapp.handler.FilterTagHandler;
import org.seasar.teeda.core.config.webapp.handler.InitParamTagHandler;
import org.seasar.teeda.core.config.webapp.handler.ServletMappingTagHandler;
import org.seasar.teeda.core.config.webapp.handler.ServletTagHandler;
import org.seasar.teeda.core.config.webapp.handler.TaglibTagHandler;
import org.seasar.teeda.core.config.webapp.handler.WebappTagHandler;

/**
 * @author manhole
 */
public class WebappTagHandlerRule extends TagHandlerRule {

    private static final long serialVersionUID = 1L;

    public WebappTagHandlerRule() {
        addTagHandler("/web-app", new WebappTagHandler());

        // icon
        // display-name
        // description
        // distributable

        // context-param
        addTagHandler("/web-app/context-param", new ContextParamTagHandler());
        addTagHandler("param-name", new SimpleStringTagHandler("param-name"));
        addTagHandler("param-value", new SimpleStringTagHandler("param-value"));

        // filter
        addTagHandler("/web-app/filter", new FilterTagHandler());
        addTagHandler("/web-app/filter/filter-name",
                new SimpleStringTagHandler("filter-name"));
        addTagHandler("/web-app/filter/filter-class",
                new SimpleStringTagHandler("filter-class"));

        addTagHandler("init-param", new InitParamTagHandler());

        // filter-mapping
        // listener

        // servlet
        addTagHandler("/web-app/servlet", new ServletTagHandler());
        addTagHandler("/web-app/servlet/servlet-name",
                new SimpleStringTagHandler("servlet-name"));
        addTagHandler("/web-app/servlet/servlet-class",
                new SimpleStringTagHandler("servlet-class"));
        addTagHandler("/web-app/servlet/load-on-startup",
                new SimpleStringTagHandler("load-on-startup"));

        // servlet-mapping
        addTagHandler("/web-app/servlet-mapping",
                new ServletMappingTagHandler());
        addTagHandler("/web-app/servlet-mapping/servlet-name",
                new SimpleStringTagHandler("servlet-name"));
        addTagHandler("/web-app/servlet-mapping/url-pattern",
                new SimpleStringTagHandler("url-pattern"));

        // session-config
        // mime-mapping
        // welcome-file-list
        // error-page

        // taglib
        addTagHandler("/web-app/taglib", new TaglibTagHandler());
        addTagHandler("/web-app/taglib/taglib-uri", new SimpleStringTagHandler(
                "taglib-uri"));
        addTagHandler("/web-app/taglib/taglib-location",
                new SimpleStringTagHandler("taglib-location"));

        // resource-env-ref
        // resource-ref
        // security-constraint
        // login-config
        // security-role
        // env-entry
        // ejb-ref
        // ejb-local-ref
    }
}
