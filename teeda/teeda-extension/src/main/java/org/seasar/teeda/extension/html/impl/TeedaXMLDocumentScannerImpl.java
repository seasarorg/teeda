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
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;

import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLStringBuffer;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 *
 * avoid ";" is required becauseof a.html?b=B&c=C format.
 */
public class TeedaXMLDocumentScannerImpl extends XMLNSDocumentScannerImpl {

    protected static final boolean DEBUG_ATTR_NORMALIZATION = false;

    private static Logger logger = Logger
            .getLogger(TeedaXMLDocumentScannerImpl.class);

    private XMLStringBuffer fStringBuffer = new XMLStringBuffer(128);

    private XMLStringBuffer fStringBuffer2 = new XMLStringBuffer(128);

    private XMLStringBuffer fStringBuffer3 = new XMLStringBuffer(128);

    protected void scanAttributeValue(XMLString value,
            XMLString nonNormalizedValue, String atName, boolean checkEntities,
            String eleName) throws IOException, XNIException {
        // quote
        int quote = fEntityScanner.peekChar();
        if (quote != '\'' && quote != '"') {
            reportFatalError("OpenQuoteExpected", new Object[] { eleName,
                    atName });
        }
        fEntityScanner.scanChar();
        int entityDepth = fEntityDepth;
        int c = fEntityScanner.scanLiteral(quote, value);
        fStringBuffer2.clear();
        fStringBuffer2.append(value);
        normalizeWhitespace(value);
        if (c != quote) {
            fScanningAttribute = true;
            fStringBuffer.clear();
            do {
                fStringBuffer.append(value);
                if (c == '&') {
                    fEntityScanner.skipChar('&');
                    if (entityDepth == fEntityDepth) {
                        fStringBuffer2.append('&');
                    }
                    if (fEntityScanner.skipChar('#')) {
                        if (entityDepth == fEntityDepth) {
                            fStringBuffer2.append('#');
                        }
                        int ch = scanCharReferenceValue(fStringBuffer,
                                fStringBuffer2);
                        if (ch != -1) {
                            if (DEBUG_ATTR_NORMALIZATION) {
                                System.out.println("** value3: \"" +
                                        fStringBuffer.toString() + "\"");
                            }
                        }
                    } else {
                        String entityName = fEntityScanner.scanName();
                        if (entityName == null) {
                            reportFatalError("NameRequiredInReference", null);
                        } else if (entityDepth == fEntityDepth) {
                            fStringBuffer2.append(entityName);
                        }
                        if (!fEntityScanner.skipChar(';')) {
                            logger
                                    .debug("[TeedaXMLDocumentScannerImpl] no ';'");
                            fEntityManager.startEntity(entityName + ";", true);
                            c = fEntityScanner.scanLiteral(quote, value);
                            if (entityDepth == fEntityDepth) {
                                fStringBuffer2.append(value);
                            }
                            normalizeWhitespace(value);
                            continue;
                        } else if (entityDepth == fEntityDepth) {
                            fStringBuffer2.append(';');
                        }
                        if (entityName == fAmpSymbol) {
                            fStringBuffer.append('&');
                        } else if (entityName == fAposSymbol) {
                            fStringBuffer.append('\'');
                        } else if (entityName == fLtSymbol) {
                            fStringBuffer.append('<');
                        } else if (entityName == fGtSymbol) {
                            fStringBuffer.append('>');
                        } else if (entityName == fQuotSymbol) {
                            fStringBuffer.append('"');
                        } else {
                            if (fEntityManager.isExternalEntity(entityName)) {
                                reportFatalError("ReferenceToExternalEntity",
                                        new Object[] { entityName });
                            } else {
                                if (!fEntityManager
                                        .isDeclaredEntity(entityName)) {
                                    //WFC & VC: Entity Declared
                                    if (checkEntities) {
                                        if (fValidation) {
                                            fErrorReporter
                                                    .reportError(
                                                            XMLMessageFormatter.XML_DOMAIN,
                                                            "EntityNotDeclared",
                                                            new Object[] { entityName },
                                                            XMLErrorReporter.SEVERITY_ERROR);
                                        }
                                    } else {
                                        reportFatalError("EntityNotDeclared",
                                                new Object[] { entityName });
                                    }
                                }
                                fEntityManager.startEntity(entityName, true);
                            }
                        }
                    }
                } else if (c == '<') {
                    reportFatalError("LessthanInAttValue", new Object[] {
                            eleName, atName });
                    fEntityScanner.scanChar();
                    if (entityDepth == fEntityDepth) {
                        fStringBuffer2.append((char) c);
                    }
                } else if (c == '%' || c == ']') {
                    fEntityScanner.scanChar();
                    fStringBuffer.append((char) c);
                    if (entityDepth == fEntityDepth) {
                        fStringBuffer2.append((char) c);
                    }
                } else if (c == '\n' || c == '\r') {
                    fEntityScanner.scanChar();
                    fStringBuffer.append(' ');
                    if (entityDepth == fEntityDepth) {
                        fStringBuffer2.append('\n');
                    }
                } else if (c != -1 && XMLChar.isHighSurrogate(c)) {
                    fStringBuffer3.clear();
                    if (scanSurrogates(fStringBuffer3)) {
                        fStringBuffer.append(fStringBuffer3);
                        if (entityDepth == fEntityDepth) {
                            fStringBuffer2.append(fStringBuffer3);
                        }
                    }
                } else if (c != -1 && isInvalidLiteral(c)) {
                    reportFatalError("InvalidCharInAttValue", new Object[] {
                            eleName, atName, Integer.toString(c, 16) });
                    fEntityScanner.scanChar();
                    if (entityDepth == fEntityDepth) {
                        fStringBuffer2.append((char) c);
                    }
                }
                c = fEntityScanner.scanLiteral(quote, value);
                if (entityDepth == fEntityDepth) {
                    fStringBuffer2.append(value);
                }
                normalizeWhitespace(value);
            } while (c != quote || entityDepth != fEntityDepth);
            fStringBuffer.append(value);
            value.setValues(fStringBuffer);
            fScanningAttribute = false;
        }
        nonNormalizedValue.setValues(fStringBuffer2);
        int cquote = fEntityScanner.scanChar();
        if (cquote != quote) {
            reportFatalError("CloseQuoteExpected", new Object[] { eleName,
                    atName });
        }
    }

    public void startEntity(String name, XMLResourceIdentifier identifier,
            String encoding) throws XNIException {
        if (name.indexOf(';') > 0) {
            name = StringUtil.replace(name, ";", "");
        }
        super.startEntity(name, identifier, encoding);
    }

}
