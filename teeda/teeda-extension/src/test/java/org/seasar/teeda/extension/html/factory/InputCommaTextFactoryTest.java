package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.seasar.teeda.extension.config.taglib.element.impl.TaglibElementImpl;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.AaaPage;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.taglib.TInputCommaTextTag;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

public class InputCommaTextFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch() throws Exception {
        InputCommaTextFactory factory = new InputCommaTextFactory();
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "text");
        props.put("class", "T_currency");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
        ElementNode elementNode2 = createElementNode("hoge", props);
        assertFalse(factory.isMatch(elementNode2, pageDesc, null));
        Map props2 = new HashMap();
        props2.put("id", "aaa");
        props2.put("type", "text");
        ElementNode elementNode3 = createElementNode("input", props2);
        assertFalse(factory.isMatch(elementNode3, pageDesc, null));
        Map props3 = new HashMap();
        props3.put("id", "xxx");
        props3.put("type", "text");
        ElementNode elementNode4 = createElementNode("input", props3);
        assertFalse(factory.isMatch(elementNode4, pageDesc, null));

        Map props4 = new HashMap();
        props4.put("id", "aaa");
        props4.put("type", "text");
        props4.put("class", "T_currency hoge");
        ElementNode elementNode5 = createElementNode("input", props4);
        assertTrue(factory.isMatch(elementNode5, pageDesc, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(ExtensionConstants.TEEDA_EXTENSION_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("inputCommaText");
        tagElement.setTagClass(TInputCommaTextTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);
        InputCommaTextFactory factory = new InputCommaTextFactory();
        factory.setTaglibManager(taglibManager);
        Map props = new HashMap();
        props.put("id", "aaa");
        props.put("type", "text");
        props.put("class", "T_Currency");
        ElementNode elementNode = createElementNode("input", props);
        PageDesc pageDesc = createPageDesc(AaaPage.class, "aaaPage");
        ActionDesc actionDesc = createActionDesc(FooAction.class, "fooAction");

        // ## Act ##
        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", TInputCommaTextTag.class, processor.getTagClass());
        assertEquals("3", "#{aaaPage.aaa}", processor.getProperty("value"));
        assertEquals("4", "#{aaaPage.aaaFraction}", processor
                .getProperty("fraction"));
        assertEquals("5", "#{aaaPage.aaaFractionSeparator}", processor
                .getProperty("fractionSeparator"));
        assertEquals("6", "#{aaaPage.aaaGroupingSeparator}", processor
                .getProperty("groupingSeparator"));
    }
}
