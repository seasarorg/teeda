package org.seasar.teeda.core.spike.cl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;

import junit.framework.TestCase;
import junitx.framework.StringAssert;

import org.seasar.teeda.core.util.EnumerationIterator;

public class ClassLoaderResourceTest extends TestCase {

    public void testGetResourceFromClassLoader() throws Exception {
        // ## Arrange ##
        File projectRoot = new File(".").getCanonicalFile();
        File jarDir = new File(projectRoot,
                "teeda-core/target/test-classes/org/seasar/teeda/core/spike/cl");
        File[] jars = jarDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jar")) {
                    return true;
                }
                return false;
            }
        });
        assertEquals(3, jars.length);
        URL[] jarUrls = new URL[jars.length];
        for (int i = 0; i < jars.length; i++) {
            System.out.println(jars[i].toURL());
            jarUrls[i] = jars[i].toURL();
        }
        URLClassLoader cl = new URLClassLoader(jarUrls);

        // ## Act ##
        Iterator facesConfigUrlIterator = getConfigurationUrlFromResources(cl);

        // ## Assert ##
        assertEquals(true, facesConfigUrlIterator.hasNext());
        URL url1 = (URL) facesConfigUrlIterator.next();
        StringAssert.assertEndsWith(
                "/faces-config-contains-1.jar!/META-INF/faces-config.xml", url1
                        .getPath());

        assertEquals(true, facesConfigUrlIterator.hasNext());
        URL url2 = (URL) facesConfigUrlIterator.next();
        StringAssert.assertEndsWith(
                "/faces-config-contains-2.jar!/META-INF/faces-config.xml", url2
                        .getPath());

        assertEquals(false, facesConfigUrlIterator.hasNext());
    }

    Iterator getConfigurationUrlFromResources(ClassLoader cl)
            throws IOException {
        Enumeration resources = cl.getResources("META-INF/faces-config.xml");
        return new EnumerationIterator(resources);
    }

}
