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
package org.seasar.teeda.extension.html.impl;

import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.parsers.XML11Configuration;

/**
 * @author shot
 *
 */
public class TeedaXMLConfiguration extends XML11Configuration {

    public TeedaXMLConfiguration() {
        XMLNSDocumentScannerImpl scanner = new TeedaXMLDocumentScannerImpl();
        setProperty(DOCUMENT_SCANNER, scanner);
        addComponent(scanner);
        fEntityManager.setEntityHandler(scanner);
        fNamespaceScanner = scanner;
    }

}
