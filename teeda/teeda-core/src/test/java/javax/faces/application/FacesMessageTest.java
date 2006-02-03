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
package javax.faces.application;

import java.util.Iterator;

import javax.faces.application.FacesMessage.Severity;

import junit.framework.TestCase;

import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class FacesMessageTest extends TestCase {

    public void testConstants() throws Exception {
        assertEquals("javax.faces.Messages", FacesMessage.FACES_MESSAGES);
    }

    public void testConstant_Values() throws Exception {
        int prevOrdinal = Integer.MIN_VALUE;
        for (Iterator it = FacesMessage.VALUES.iterator(); it.hasNext();) {
            FacesMessage.Severity severity = (FacesMessage.Severity) it.next();
            severity.getOrdinal();
            final int ordinal = severity.getOrdinal();
            assertEquals(true, prevOrdinal < ordinal);
            prevOrdinal = ordinal;
        }
    }

    public void testConstructor1() throws Exception {
        // ## Arrange ##
        // ## Act ##
        FacesMessage facesMessage = new FacesMessage();

        // ## Assert ##
        assertEquals(FacesMessage.SEVERITY_INFO, facesMessage.getSeverity());
    }

    public void testConstructor2() throws Exception {
        // ## Arrange ##
        // ## Act ##
        FacesMessage facesMessage = new FacesMessage("ss");

        // ## Assert ##
        assertEquals(FacesMessage.SEVERITY_INFO, facesMessage.getSeverity());
        assertEquals("ss", facesMessage.getSummary());
        assertEquals("ss", facesMessage.getDetail());
    }

    public void testConstructor3() throws Exception {
        // ## Arrange ##
        // ## Act ##
        FacesMessage facesMessage = new FacesMessage("ss", "ddd");

        // ## Assert ##
        assertEquals(FacesMessage.SEVERITY_INFO, facesMessage.getSeverity());
        assertEquals("ddd", facesMessage.getDetail());
        assertEquals("ss", facesMessage.getSummary());
    }

    public void testConstructor4() throws Exception {
        // ## Arrange ##
        // ## Act ##
        FacesMessage facesMessage = new FacesMessage(
                FacesMessage.SEVERITY_WARN, "ss", "ddd");

        // ## Assert ##
        assertEquals(FacesMessage.SEVERITY_WARN, facesMessage.getSeverity());
        assertEquals("ddd", facesMessage.getDetail());
        assertEquals("ss", facesMessage.getSummary());
    }

    public void testConstructor4_UnsupportedSeverity() throws Exception {
        try {
            new FacesMessage(new Severity("AAA", 7650), "ss", "ddd");
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public void testSetGetDetail() throws Exception {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setDetail("d");
        assertEquals("d", facesMessage.getDetail());
    }

    public void testSetGetDetail_InsteadSummary() throws Exception {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        assertEquals("s", facesMessage.getDetail());
    }

    public void testSetGetSummary() throws Exception {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary("s");
        assertEquals("s", facesMessage.getSummary());
    }

    public void testSetGetSeverity() throws Exception {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
        assertEquals(FacesMessage.SEVERITY_FATAL, facesMessage.getSeverity());
    }

    public void testSetGetSeverity_UnsupportedSeverity() throws Exception {
        FacesMessage facesMessage = new FacesMessage();
        try {
            facesMessage.setSeverity(new Severity("BBB", -200));
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

}
