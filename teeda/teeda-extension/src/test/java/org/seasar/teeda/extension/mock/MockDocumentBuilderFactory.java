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
package org.seasar.teeda.extension.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.seasar.framework.util.ResourceUtil;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author shot
 */
public class MockDocumentBuilderFactory extends DocumentBuilderFactoryImpl {

    private static final String XHTML_DTD_RESOURCES_PATH = "org/seasar/teeda/extension/resource/xhtml1/";

    private static Map dtdPaths = new HashMap();

    static {
        dtdPaths.put("-//W3C//DTD XHTML 1.0 Frameset//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml1-frameset.dtd");
        dtdPaths.put("-//W3C//DTD XHTML 1.0 Strict//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml1-strict.dtd");
        dtdPaths.put("-//W3C//DTD XHTML 1.0 Transitional//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml1-transitional.dtd");
        dtdPaths.put("-//W3C//ENTITIES Latin 1 for XHTML//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml-lat1.ent");
        dtdPaths.put("-//W3C//ENTITIES Symbols for XHTML//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml-symbol.ent");
        dtdPaths.put("-//W3C//ENTITIES Special for XHTML//EN",
                XHTML_DTD_RESOURCES_PATH + "xhtml-special.ent");
    };

    public DocumentBuilder newDocumentBuilder()
            throws ParserConfigurationException {
        DocumentBuilder db = super.newDocumentBuilder();
        EntityResolver er = new EntityResolver() {

            public InputSource resolveEntity(String publicId, String systemId)
                    throws SAXException, IOException {
                String dtdPath = null;
                if (publicId != null) {
                    dtdPath = (String) dtdPaths.get(publicId);
                }
                if (dtdPath == null) {
                    return null;
                }
                return new InputSource(ResourceUtil
                        .getResourceAsStream(dtdPath));
            }

        };
        db.setEntityResolver(er);
        return db;
    }

}
