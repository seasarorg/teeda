package org.seasar.teeda.extension.html.factory;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.core.taglib.html.CommandButtonTag;
import org.seasar.teeda.extension.config.taglib.TaglibElementBuilder;
import org.seasar.teeda.extension.config.taglib.impl.FileSystemTaglibManagerImpl;
import org.seasar.teeda.extension.config.taglib.impl.TaglibElementBuilderImpl;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.impl.ElementNodeImpl;

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
        FileSystemTaglibManagerImpl taglibManager = new FileSystemTaglibManagerImpl();
        MockServletContextImpl servletContext = new MockServletContextImpl(null);
        taglibManager.setServletContext(servletContext);
        TaglibElementBuilder builder = new TaglibElementBuilderImpl();
        taglibManager.setBuilder(builder);
        CommandButtonFactory factory = new CommandButtonFactory();
        factory.setTaglibManager(taglibManager);
        Map properties = new HashMap();
        properties.put("id", "aaa");
        properties.put("type", "submit");
        ElementNode elementNode = new ElementNodeImpl("input", properties);

        // ## Act ##
        taglibManager
                .init("org/seasar/teeda/extension/config/taglib/impl/tlds");
        ElementProcessor processor = factory.createProcessor(elementNode, null);
        // ## Assert ##
        assertNotNull("1", processor);
        assertEquals("2", CommandButtonTag.class, processor.getTagClass());
    }
}
