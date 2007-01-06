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
package org.seasar.teeda.core.unit.xmlunit;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author manhole
 */
public class HtmlDomUtil {

    public static String toStructureString(Document document) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        NodeList childNodes = document.getChildNodes();
        printNodes(pw, childNodes, 0);
        return sw.toString();
    }

    private static void printNodes(PrintWriter pw, NodeList childNodes,
            int indent) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            printIndent(pw, indent);
            String nodeName = node.getNodeName();
            if (Node.TEXT_NODE == node.getNodeType()) {
                if (node.hasChildNodes()) {
                    throw new RuntimeException();
                } else {
                    pw.println("[" + nodeName + "]");
                }
            } else {
                if (node.hasChildNodes()) {
                    pw.println("<" + nodeName + ">");
                    printNodes(pw, node.getChildNodes(), indent + 1);
                    printIndent(pw, indent);
                    pw.println("</" + nodeName + ">");
                } else {
                    pw.println("<" + nodeName + " />");
                }
            }
        }
    }

    private static void printIndent(PrintWriter pw, int indent) {
        for (int i = 0; i < indent; i++) {
            pw.print("  ");
        }
    }

    public static void removeNode(Node node) {
        node.getParentNode().removeChild(node);
    }

    public static void removeBlankTextNode(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Node.TEXT_NODE == node.getNodeType()
                    && 0 == node.getNodeValue().trim().length()) {
                removeNode(node);
            }
        }
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            removeBlankTextNode(node.getChildNodes());
        }
    }

}
