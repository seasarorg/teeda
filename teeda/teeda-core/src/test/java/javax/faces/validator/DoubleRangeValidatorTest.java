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
package javax.faces.validator;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class DoubleRangeValidatorTest extends TestCase {

    public void testConstants() throws Exception {
        assertEquals("javax.faces.validator.DoubleRangeValidator.MAXIMUM",
                DoubleRangeValidator.MAXIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.DoubleRangeValidator.MINIMUM",
                DoubleRangeValidator.MINIMUM_MESSAGE_ID);
        assertEquals("javax.faces.validator.DoubleRangeValidator.TYPE",
                DoubleRangeValidator.TYPE_MESSAGE_ID);
        assertEquals("javax.faces.DoubleRange",
                DoubleRangeValidator.VALIDATOR_ID);
    }

    public void testInstanciation_withMax() throws Exception {
        double d = 2d;
        DoubleRangeValidator validator = new DoubleRangeValidator(d);
        assertEquals(2d, validator.getMaximum());
    }
    
    public void testInstanciation_withMaxAndMin() throws Exception {
        double max = 2000d;
        double min = 3d;
        DoubleRangeValidator validator = new DoubleRangeValidator(max, min);
        assertEquals(2000d, validator.getMaximum());
        assertEquals(3d, validator.getMinimum());
    }

    public void testGetMaximum_notSetMax() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        assertEquals(Double.MAX_VALUE, validator.getMaximum());
    }
    
    public void testGetMinimum_notSetMin() throws Exception {
        DoubleRangeValidator validator = new DoubleRangeValidator();
        assertEquals(Double.MIN_VALUE, validator.getMinimum());        
    }
}
