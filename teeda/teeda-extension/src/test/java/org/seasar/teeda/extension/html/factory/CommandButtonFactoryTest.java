package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.CommandButtonTag;
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

public class CommandButtonFactoryTest extends TestCase {

    public void testIsMatch() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "submit");
        ElementNode node1 = new ElementNodeImpl("input", properties);
        assertTrue(factory.isMatch(node1));

        ElementNode node2 = new ElementNodeImpl("hoge", properties);
        assertFalse(factory.isMatch(node2));
    }

    public void testIsMatch_typeSubmit() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "submit");
        ElementNode node1 = new ElementNodeImpl("input", properties);
        assertTrue(factory.isMatch(node1));
    }

    public void testIsMatch_typeButton() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "button");
        ElementNode node1 = new ElementNodeImpl("input", properties);
        assertTrue(factory.isMatch(node1));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        MockTaglibManager taglibManager = new MockTaglibManager();
        TaglibElement jsfHtml = new TaglibElementImpl();
        jsfHtml.setUri(JsfConstants.JSF_HTML_URI);
        TagElement tagElement = new TagElementImpl();
        tagElement.setName("commandButton");
        tagElement.setTagClass(CommandButtonTag.class);
        jsfHtml.addTagElement(tagElement);
        taglibManager.addTaglibElement(jsfHtml);

        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        ElementNode elementNode = new ElementNodeImpl("input", properties);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(CommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        
        Map properties2 = new HashMap();
        properties2.put("id", "doAaa");
        properties2.put("type", "submit");
        ElementNode elementNode2 = new ElementNodeImpl("input", properties2);
        ElementProcessor processor2 = factory.createProcessor(elementNode2,
                pageDesc, actionDesc);
        assertEquals("#{fooAction.doAaa}", processor2.getProperty("action"));
    }
}
