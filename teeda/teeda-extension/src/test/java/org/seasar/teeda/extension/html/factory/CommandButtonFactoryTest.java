package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.taglib.html.CommandButtonTag;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooAction;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.html.impl.ActionDescImpl;
import org.seasar.teeda.extension.mock.MockTaglibManager;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

public class CommandButtonFactoryTest extends TeedaExtensionTestCase {

    public void testIsMatch_go() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "goNextPage");
        ElementNode elementNode = createElementNode("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_do() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "submit");
        properties.put("id", "doBbb");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue(factory.isMatch(elementNode, pageDesc, null));
        assertTrue(factory.isMatch(elementNode, pageDesc, actionDesc));

        Map properties2 = new HashMap();
        properties2.put("type", "submit");
        properties2.put("id", "doCcc");
        ElementNode elementNode2 = createElementNode("input", properties2);
        assertTrue(factory.isMatch(elementNode2, pageDesc, actionDesc));
        assertTrue(factory.isMatch(elementNode2, null, actionDesc));
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testIsMatch_button() throws Exception {
        CommandButtonFactory factory = new CommandButtonFactory();
        Map properties = new HashMap();
        properties.put("type", "button");
        properties.put("id", "goNextPage");
        ElementNode elementNode = createElementNode("input", properties);
        assertTrue(factory.isMatch(elementNode, null, null));

        ElementNode elementNode2 = createElementNode("hoge", properties);
        assertFalse(factory.isMatch(elementNode2, null, null));
    }

    public void testCreateFactory() throws Exception {
        // ## Arrange ##
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "commandButton",
                CommandButtonTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
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
        ElementNode elementNode2 = createElementNode("input", properties2);
        ElementProcessor processor2 = factory.createProcessor(elementNode2,
                pageDesc, actionDesc);
        assertEquals("#{fooAction.doCcc}", processor2.getProperty("action"));

        Map properties3 = new HashMap();
        properties3.put("id", "goNextPage");
        properties3.put("type", "submit");
        ElementNode elementNode3 = createElementNode("input", properties3);
        ElementProcessor processor3 = factory.createProcessor(elementNode3,
                pageDesc, actionDesc);
        assertEquals("nextPage", processor3.getProperty("action"));
    }

    public void testCreateFactory_locationHrefRemove1() throws Exception {
        // ## Arrange ##
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "commandButton",
                CommandButtonTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "location.href='hoge.html'");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(CommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("", processor.getProperty("onclick"));
    }

    public void testCreateFactory_locationHrefRemove2() throws Exception {
        // ## Arrange ##
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "commandButton",
                CommandButtonTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "location.href='hoge.html';foo();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(CommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("foo();", processor.getProperty("onclick"));
    }

    public void testCreateFactory_locationHrefRemove3() throws Exception {
        // ## Arrange ##
        registerTaglibElement(JsfConstants.JSF_HTML_URI, "commandButton",
                CommandButtonTag.class);
        MockTaglibManager taglibManager = getTaglibManager();
        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "doBbb");
        properties.put("type", "submit");
        properties.put("onclick", "hoge();location.href='foo.html';bar();");
        ElementNode elementNode = createElementNode("input", properties);
        PageDesc pageDesc = createPageDesc(FooPage.class, "fooPage");
        ActionDesc actionDesc = new ActionDescImpl(FooAction.class, "fooAction");

        ElementProcessor processor = factory.createProcessor(elementNode,
                pageDesc, actionDesc);
        // ## Assert ##
        assertNotNull(processor);
        assertEquals(CommandButtonTag.class, processor.getTagClass());
        assertEquals("#{fooPage.doBbb}", processor.getProperty("action"));
        assertEquals("hoge();bar();", processor.getProperty("onclick"));
    }

}
