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
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;

import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;

/**
 * @author shot
 */
public class TeedaXMLDocumentScannerImpl extends XMLNSDocumentScannerImpl {

    //TODO testing
    protected static final boolean DEBUG_ATTR_NORMALIZATION = false;

    protected void scanAttributeValue(XMLString value,
            XMLString nonNormalizedValue, String atName, boolean checkEntities,
            String eleName) throws IOException, XNIException {
        super.scanAttributeValue(value, nonNormalizedValue, atName,
                checkEntities, eleName);
        value.setValues(nonNormalizedValue);
    }
}
