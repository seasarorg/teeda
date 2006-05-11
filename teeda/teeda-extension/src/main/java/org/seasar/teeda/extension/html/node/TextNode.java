package org.seasar.teeda.extension.html.node;

import org.seasar.teeda.extension.html.HtmlNode;

public class TextNode implements HtmlNode {

    private String text;
    
    public TextNode(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
    
    public String toString() {
        return text;
    }
}