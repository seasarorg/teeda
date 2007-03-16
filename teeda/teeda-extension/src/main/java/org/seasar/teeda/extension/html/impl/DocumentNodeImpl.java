/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.extension.html.DocumentNode;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;

/**
 * @author shot
 *
 */
public class DocumentNodeImpl implements DocumentNode {

    private StringBuffer texts = new StringBuffer(256);

    private List children = new ArrayList();

    public void addText(String text) {
        texts.append(text);
    }

    public String getDocType() {
        return texts.toString();
    }

    public void addChild(ElementNode elementNode) {
        children.add(elementNode);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer(512);
        if (texts.length() != 0) {
            buf.append(texts);
        }
        for (int i = 0; i < getChildSize(); i++) {
            HtmlNode child = getChild(i);
            buf.append(child.toString());
        }
        return buf.toString();
    }

    public HtmlNode getChild(int index) {
        return (HtmlNode) children.get(index);
    }

    public int getChildSize() {
        return children.size();
    }
}
