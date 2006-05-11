package org.seasar.teeda.extension.html.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.teeda.extension.html.HtmlNode;

public class TagNode implements HtmlNode {

    private String tagName;
    
    private Map properties;
    
    private List children = new ArrayList();
    
    private StringBuffer buffer; 
    
    public TagNode(String tagName, Map properties) {
        this.tagName = tagName;
        this.properties = properties;
        initializeBuffer();
    }

    protected void initializeBuffer() {
        buffer = new StringBuffer(100);
    }

    public void addText(String text) {
        buffer.append(text);
    }
    
    public void addTagNode(TagNode tagNode) {
        processText();
        addChild(tagNode);
    }
    
    public void processText() {
        if (buffer.length() > 0) {
            addChild(new TextNode(buffer.toString()));
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

    public void addChild(HtmlNode child) {
        children.add(child);
    }
    
    public String toString() {
        if (getChildSize() == 0) {
            return HtmlNodeUtil.getEmptyTagString(tagName, properties);
        }
        StringBuffer buf = new StringBuffer(512);
        buf.append(HtmlNodeUtil.getStartTagString(tagName, properties));
        for (int i = 0; i < getChildSize(); ++i) {
            buf.append(getChild(i).toString());
        }
        buf.append(HtmlNodeUtil.getEndTagString(tagName));
        return buf.toString();
    }
    
    
}