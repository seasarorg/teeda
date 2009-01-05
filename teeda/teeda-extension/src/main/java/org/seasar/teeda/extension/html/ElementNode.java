/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html;

import java.util.Iterator;
import java.util.Map;

/**
 * @author higa
 * @author manhole
 * @author shot
 */
public interface ElementNode extends HtmlNode {

    String getNamespaceURI();

    String getLocalName();

    String getTagName();

    String getId();

    String getProperty(String name);

    Iterator getPropertyNameIterator();

    Map copyProperties();

    void addText(String text);

    void addElement(ElementNode elementNode);

    void endElement();

    int getChildSize();

    HtmlNode getChild(int index);

    int getChildTextSize();

    void incrementChildTextSize();

    void decrementChildTextSize();

    String getCompleteTagString();

    String getStartTagString();

    String getEndTagString();

    void setParent(ElementNode elementNode);

    ElementNode getParent();

    void removeChild(HtmlNode node);

    TextNode getFirstTextNode();
}
