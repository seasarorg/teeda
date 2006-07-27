package org.seasar.teeda.extension.html.factory;

import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.HtmlNode;
import org.seasar.teeda.extension.html.PageDesc;

public class SelectOneCheckboxFactory extends AbstractElementProcessorFactory {

    //TODO fix this
    private static final String TAG_NAME = "selectOneCheckbox";

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        String items = id + ExtensionConstants.ITEMS_SUFFIX;
        if (!pageDesc.hasItemsProperty(items)) {
            return false;
        }
        for (int i = 0; i < elementNode.getChildSize(); i++) {
            HtmlNode child = elementNode.getChild(i);
            if (child instanceof ElementNode) {
                ElementNode node = (ElementNode) child;
                String childTagName = node.getTagName();
                if (!JsfConstants.INPUT_ELEM.equalsIgnoreCase(childTagName)
                        || !JsfConstants.CHECKBOX_VALUE.equalsIgnoreCase(node
                                .getProperty(JsfConstants.TYPE_ATTR))) {
                    return false;
                }
                String name = node.getProperty(JsfConstants.NAME_ATTR);
                if (name == null || !name.equals(id)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        String pageName = pageDesc.getPageName();
        String id = elementNode.getId();
        properties.put(JsfConstants.VALUE_ATTR, getBindingExpression(pageName,
                id));
        properties.put("items", getBindingExpression(pageName,
                (id + ExtensionConstants.ITEMS_SUFFIX)));
    }

    public boolean isLeaf() {
        return true;
    }

}
