package org.seasar.teeda.extension.html.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;

public class ElementNodeImpl implements ElementNode {

    private String namespaceURI;

    private String localName;

    private String tagName;

    private ElementNode parent;

    private Map properties;

    private List children = new ArrayList();

    private StringBuffer buffer;

    private int childTextSize;

    private String normalizedId;

    public ElementNodeImpl(String namespaceURI, String localName,
            String tagName, Map properties) {
        this.namespaceURI = namespaceURI;
        this.localName = localName;
        this.tagName = tagName;
        this.properties = properties;
        initializeBuffer();
        this.normalizedId = normalizeId();
    }

    protected String normalizeId() {
        String id = getId();
        if (StringUtil.isEmpty(id)) {
            return null;
        }
        int indexOf = id.indexOf("-");
        if (indexOf <= 0) {
            return null;
        }
        return id.substring(0, indexOf);
    }

    protected void initializeBuffer() {
        buffer = new StringBuffer(100);
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }

    public String getLocalName() {
        return localName;
    }

    public String getTagName() {
        return tagName;
    }

    public String getId() {
        if (normalizedId != null) {
            return normalizedId;
        }
        return getProperty(JsfConstants.ID_ATTR);
    }

    public String getProperty(String name) {
        return (String) properties.get(name);
    }

    public Iterator getPropertyNameIterator() {
        return properties.keySet().iterator();
    }

    public Map copyProperties() {
        return new HashMap(properties);
    }

    public void addText(String text) {
        buffer.append(text);
    }

    public void addElement(ElementNode elementNode) {
        processText();
        renewParent(elementNode);
        addChild(elementNode);
    }

    private void renewParent(ElementNode elementNode) {
        final ElementNode oldParent = elementNode.getParent();
        if (oldParent != null) {
            oldParent.removeChild(elementNode);
        }
        elementNode.setParent(this);
    }

    protected void processText() {
        if (buffer.length() > 0) {
            addChild(new TextNodeImpl(buffer.toString()));
            initializeBuffer();
        }
    }

    public void endElement() {
        processText();
    }

    public int getChildSize() {
        return children.size();
    }

    public HtmlNode getChild(int index) {
        return (HtmlNode) children.get(index);
    }

    protected void addChild(HtmlNode child) {
        children.add(child);
    }

    public int getChildTextSize() {
        return childTextSize;
    }

    public void incrementChildTextSize() {
        ++childTextSize;
    }

    public void decrementChildTextSize() {
        --childTextSize;
    }

    public String getCompleteTagString() {
        return HtmlNodeUtil.getCompleteTagString(tagName, properties);
    }

    public String getStartTagString() {
        return HtmlNodeUtil.getStartTagString(tagName, properties);
    }

    public String getEndTagString() {
        return HtmlNodeUtil.getEndTagString(tagName);
    }

    public String toString() {
        if (getChildSize() == 0) {
            return getCompleteTagString();
        }
        StringBuffer buf = new StringBuffer(512);
        buf.append(getStartTagString());
        for (int i = 0; i < getChildSize(); ++i) {
            buf.append(getChild(i).toString());
        }
        buf.append(getEndTagString());
        return buf.toString();
    }

    public ElementNode getParent() {
        return parent;
    }

    public void setParent(ElementNode elementNode) {
        parent = elementNode;
    }

    public void removeChild(HtmlNode node) {
        children.remove(node);
    }

}
