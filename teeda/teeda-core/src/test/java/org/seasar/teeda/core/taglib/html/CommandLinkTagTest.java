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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandLink;

import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class CommandLinkTagTest extends TeedaTestCase {
    
    public void testGetComponentType() throws Exception {
        // # Arrange #
        CommandLinkTag tag = new CommandLinkTag();
        
        // # Act & Assert #
        assertEquals("javax.faces.HtmlCommandLink",
                tag.getComponentType());
    }
    
    public void testGetRenderType() throws Exception {
        // # Arrange #
        CommandLinkTag tag = new CommandLinkTag();
        
        // # Act & Assert #
        assertEquals("javax.faces.Link", tag.getRendererType());        
    }
    
    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlCommandLink command = createHtmlCommandLink();
        CommandLinkTag tag = new CommandLinkTag();
        
        tag.setAction("#{testAction}");
        tag.setActionListener("#{hoge.do}");
        tag.setImmediate("true");
        tag.setValue("value 1");
        tag.setAccesskey("access key");
        tag.setCharset("charset");
        tag.setCoords("coords");
        tag.setDir("dir");
        tag.setHreflang("hreflang");
        tag.setLang("lang");
        tag.setOnblur("onblur");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnfocus("onfocus");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeyup("onkeyup");
        tag.setOnmousedown("onmousedown");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseout("onmouseout");
        tag.setOnmouseover("onmouseover");
        tag.setOnmouseup("onmouseup");
        tag.setRel("rel");
        tag.setRev("rev");
        tag.setShape("shape");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTabindex("5");
        tag.setTarget("target");
        tag.setTitle("title");
        tag.setType("type");

        // # Act #
        tag.setProperties(command);
                
        // # Assert #
        assertTrue(command.getAction() instanceof MockMethodBinding);
        assertTrue(command.getActionListener() instanceof MockMethodBinding);
        assertTrue(command.isImmediate());
        assertEquals("value 1", command.getValue());
        assertEquals("access key", command.getAccesskey());
        assertEquals("charset", command.getCharset());
        assertEquals("coords", command.getCoords());
        assertEquals("dir", command.getDir());
        assertEquals("hreflang", command.getHreflang());
        assertEquals("lang", command.getLang());
        assertEquals("onblur", command.getOnblur());
        assertEquals("onclick", command.getOnclick());
        assertEquals("ondblclick", command.getOndblclick());
        assertEquals("onfocus", command.getOnfocus());
        assertEquals("onkeydown", command.getOnkeydown());
        assertEquals("onkeypress", command.getOnkeypress());
        assertEquals("onkeyup", command.getOnkeyup());
        assertEquals("onmousedown", command.getOnmousedown());
        assertEquals("onmousemove", command.getOnmousemove());
        assertEquals("onmouseout", command.getOnmouseout());
        assertEquals("onmouseover", command.getOnmouseover());
        assertEquals("onmouseup", command.getOnmouseup());
        assertEquals("rel", command.getRel());
        assertEquals("rev", command.getRev());
        assertEquals("shape", command.getShape());
        assertEquals("style", command.getStyle());
        assertEquals("styleclass", command.getStyleClass());
        assertEquals("5", command.getTabindex());
        assertEquals("target", command.getTarget());
        assertEquals("title", command.getTitle());
        assertEquals("type", command.getType());
    }

    private HtmlCommandLink createHtmlCommandLink() {
        return (HtmlCommandLink) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlCommandLink();
    }
    
}
