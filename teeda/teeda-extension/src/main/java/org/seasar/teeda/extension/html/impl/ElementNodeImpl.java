package org.seasar.teeda.extension.html.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;

public class ElementNodeImpl implements ElementNode {

    private String tagName;
    
    private Map properties;
    
    private List children = new ArrayList();
    
    private StringBuffer buffer;
    
    private int childTextSize;
    
    public ElementNodeImpl(String tagName, Map properties) {
        this.tagName = tagName;
        this.properties = properties;
        initializeBuffer();
    }

    protected void initializeBuffer() {
        buffer = new StringBuffer(100);
    }
    
    public String getTagName() {
        return tagName;
    }
    
    public String getId() {
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
        addChild(elementNode);
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
    
    public String getEmptyTagString() {
        return HtmlNodeUtil.getEmptyTagString(tagName, properties);
    }
    
    public String getStartTagString() {
        return HtmlNodeUtil.getStartTagString(tagName, properties);
    }
    
    public String getEndTagString() {
        return HtmlNodeUtil.getEndTagString(tagName);
    }
    
    public String toString() {
        if (getChildSize() == 0) {
            return getEmptyTagString();
        }
        StringBuffer buf = new StringBuffer(512);
        buf.append(getStartTagString());
        for (int i = 0; i < getChildSize(); ++i) {
            buf.append(getChild(i).toString());
        }
        buf.append(getEndTagString());
        return buf.toString();
    }
}