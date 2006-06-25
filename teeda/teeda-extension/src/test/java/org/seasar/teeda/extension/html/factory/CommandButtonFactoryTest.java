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

    public void testIsMatch_go() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "goNextPage");
        ElementNode elementNode = new ElementNodeImpl("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = new ElementNodeImpl("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }
    
    public void testIsMatch_do() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "doBbb");
        ElementNode elementNode = new ElementNodeImpl("input", properties);
        PageDesc pageDesc = new PageDescImpl(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
        assertTrue(factory.isMatch(elementNode, pageDesc, actionDesc));
        
        Map properties2 = new HashMap();
        properties2.put("type", "submit");
        properties2.put("id", "doCcc");
        ElementNode elementNode2 = new ElementNodeImpl("input", properties2);
        assertTrue(factory.isMatch(elementNode2, pageDesc, actionDesc));
        assertTrue(factory.isMatch(elementNode2, null, actionDesc));
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_button() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "button");
        properties.put("id", "goNextPage");
        ElementNode elementNode = new ElementNodeImpl("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = new ElementNodeImpl("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
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
        properties2.put("id", "doCcc");
        properties2.put("type", "submit");
        ElementNode elementNode2 = new ElementNodeImpl("input", properties2);
        ElementProcessor processor2 = factory.createProcessor(elementNode2,
                pageDesc, actionDesc);
        assertEquals("#{fooAction.doCcc}", processor2.getProperty("action"));
        
        Map properties3 = new HashMap();
        properties3.put("id", "goNextPage");
        properties3.put("type", "submit");
        ElementNode elementNode3 = new ElementNodeImpl("input", properties3);
        ElementProcessor processor3 = factory.createProcessor(elementNode3,
                pageDesc, actionDesc);
        assertEquals("nextPage", processor3.getProperty("action"));
    }
}
