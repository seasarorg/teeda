package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.InputSecretTag;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;
import org.seasar.teeda.extension.html.impl.PageDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;

public class InputSecretFactoryTest extends TestCase {

    public void testIsMatch() throws Exception {
        InputSecretFactory factory = new InputSecretFactory();
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "password");
        ElementNode elementNode = new ElementNodeImpl("input", props);
        assertTrue("1", factory.isMatch(elementNode));
        ElementNode elementNode2 = new ElementNodeImpl("hoge", props);
        assertFalse("2", factory.isMatch(elementNode2));
        Map props2 = new HashMap();
        props2.put("id", "aaa");
        props2.put("type", "text");
        ElementNode elementNode3 = new ElementNodeImpl("input", props2);
        assertFalse("3", factory.isMatch(elementNode3));
    }
    
    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("inputSecret");
        tagElement.setTagClass(InputSecretTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);
        InputSecretFactory factory = new InputSecretFactory();
        factory.setTaglibManager(taglibManager);
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "password");
        ElementNode elementNode = new ElementNodeImpl("input", props);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode, pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", InputSecretTag.class, processor.getTagClass());
        assertEquals("3", "#{fooPage.aaa}", processor.getProperty("value"));
    }

}
