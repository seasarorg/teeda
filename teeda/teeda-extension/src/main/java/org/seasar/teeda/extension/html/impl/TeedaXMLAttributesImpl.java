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

import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;

/**
 * @author shot
 * through all getValue.
 */
public class TeedaXMLAttributesImpl extends XMLAttributesImpl {

    private XMLAttributesImpl attributes;

    public TeedaXMLAttributesImpl(XMLAttributesImpl attributes) {
        super();
        this.attributes = attributes;
    }

    public String getValue(int index) {
        String value = attributes.getNonNormalizedValue(index);
        return value;
    }

    public String getValue(String qname) {
        int index = getIndex(qname);
        return index != -1 ? getNonNormalizedValue(index) : null;
    }

    public String getValue(String uri, String localName) {
        int index = getIndex(uri, localName);
        return index != -1 ? getNonNormalizedValue(index) : null;
    }

    public int addAttribute(QName name, String type, String value) {
        return attributes.addAttribute(name, type, value);
    }

    public void addAttributeNS(QName name, String type, String value) {
        attributes.addAttributeNS(name, type, value);
    }

    public QName checkDuplicatesNS() {
        return attributes.checkDuplicatesNS();
    }

    public Augmentations getAugmentations(int attributeIndex) {
        return attributes.getAugmentations(attributeIndex);
    }

    public Augmentations getAugmentations(String uri, String localName) {
        return attributes.getAugmentations(uri, localName);
    }

    public Augmentations getAugmentations(String qName) {
        return attributes.getAugmentations(qName);
    }

    public int getIndex(String uri, String localPart) {
        return attributes.getIndex(uri, localPart);
    }

    public int getIndex(String qName) {
        return attributes.getIndex(qName);
    }

    public int getIndexFast(String uri, String localPart) {
        return attributes.getIndexFast(uri, localPart);
    }

    public int getIndexFast(String qName) {
        return attributes.getIndexFast(qName);
    }

    public int getLength() {
        return attributes.getLength();
    }

    public String getLocalName(int index) {
        return attributes.getLocalName(index);
    }

    public void getName(int attrIndex, QName attrName) {
        attributes.getName(attrIndex, attrName);
    }

    public String getName(int index) {
        return attributes.getName(index);
    }

    public String getNonNormalizedValue(int attrIndex) {
        return attributes.getNonNormalizedValue(attrIndex);
    }

    public String getPrefix(int index) {
        return attributes.getPrefix(index);
    }

    public String getQName(int index) {
        return attributes.getQName(index);
    }

    public boolean getSchemaId(int index) {
        return attributes.getSchemaId(index);
    }

    public boolean getSchemaId(String uri, String localName) {
        return attributes.getSchemaId(uri, localName);
    }

    public boolean getSchemaId(String qname) {
        return attributes.getSchemaId(qname);
    }

    public String getType(int index) {
        return attributes.getType(index);
    }

    public String getType(String uri, String localName) {
        return attributes.getType(uri, localName);
    }

    public String getType(String qname) {
        return attributes.getType(qname);
    }

    public String getURI(int index) {
        return attributes.getURI(index);
    }

    public boolean isSpecified(int attrIndex) {
        return attributes.isSpecified(attrIndex);
    }

    public void removeAllAttributes() {
        attributes.removeAllAttributes();
    }

    public void removeAttributeAt(int attrIndex) {
        attributes.removeAttributeAt(attrIndex);
    }

    public void setAugmentations(int attrIndex, Augmentations augs) {
        attributes.setAugmentations(attrIndex, augs);
    }

    public void setName(int attrIndex, QName attrName) {
        attributes.setName(attrIndex, attrName);
    }

    public void setNamespaces(boolean namespaces) {
        attributes.setNamespaces(namespaces);
    }

    public void setNonNormalizedValue(int attrIndex, String attrValue) {
        attributes.setNonNormalizedValue(attrIndex, attrValue);
    }

    public void setSchemaId(int attrIndex, boolean schemaId) {
        attributes.setSchemaId(attrIndex, schemaId);
    }

    public void setSpecified(int attrIndex, boolean specified) {
        attributes.setSpecified(attrIndex, specified);
    }

    public void setType(int attrIndex, String attrType) {
        attributes.setType(attrIndex, attrType);
    }

    public void setURI(int attrIndex, String uri) {
        attributes.setURI(attrIndex, uri);
    }

    public void setValue(int attrIndex, String attrValue) {
        attributes.setValue(attrIndex, attrValue);
    }

}
