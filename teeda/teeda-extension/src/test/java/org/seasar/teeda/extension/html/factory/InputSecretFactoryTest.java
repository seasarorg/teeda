package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.taglib.TInputSecretTag;

public class InputSecretFactoryTest extends ElementProcessorFactoryTestCase {

    protected AbstractElementProcessorFactory createFactory() {
        return new InputSecretFactory();
    }

    protected void registerTagElements() {
        registerTagElement(ExtensionConstants.TEEDA_EXTENSION_URI,
                "inputSecret", TInputSecretTag.class);
    }

    public void testIsMatch() throws Exception {
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "password");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue("1", factory.isMatch(elementNode, pageDesc, null));
        ElementNode elementNode2 = createElementNode("hoge", props);
        assertFalse("2", factory.isMatch(elementNode2, pageDesc, null));
        Map props2 = new HashMap();
        props2.put("id", "aaa");
        props2.put("type", "text");
        ElementNode elementNode3 = createElementNode("input", props2);
        assertFalse("3", factory.isMatch(elementNode3, pageDesc, null));
        Map props3 = new HashMap();
        props3.put("id", "xxx");
        props3.put("type", "password");
        ElementNode elementNode4 = createElementNode("input", props3);
        assertFalse("3", factory.isMatch(elementNode4, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "password");
        props.put("title", "aaa");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TInputSecretTag.class, processor.getTagClass());
        assertEquals("3", "#{fooPage.aaa}", processor.getProperty("value"));
        assertEquals("4", null, processor.getProperty("label"));
    }
}