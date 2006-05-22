package org.seasar.teeda.extension.html.impl;

import org.seasar.teeda.extension.html.TextNode;

public class TextNodeImpl implements TextNode {

    private String value;

    public TextNodeImpl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }
}