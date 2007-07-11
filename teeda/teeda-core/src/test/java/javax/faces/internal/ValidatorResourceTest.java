/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import java.util.HashMap;
import java.util.Map;

import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author higa
 * @author shot
 */
public class ValidatorResourceTest extends TeedaTestCase {

    public void tearDown() {
        ValidatorResource.removeAll();
    }

    public void setUpGetValidator() throws Exception {
        register(DoubleRangeValidator.class, "doubleRangeValidator");
        HotDeployValidatorBuilderImpl builder = new HotDeployValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
    }

    public void tearDownGetValidator() throws Exception {
        ValidatorResource.setValidatorBuilder(null);
    }

    public void testGetValidator() throws Exception {
        ValidatorResource.addValidator("#{a.name}", "doubleRangeValidator");
        assertNotNull(ValidatorResource.getValidator("#{a.name}"));
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof DoubleRangeValidator);
    }

    public void setUpGetValidator2() throws Exception {
        register(DoubleRangeValidator.class, "doubleRangeValidator");
        HotDeployValidatorBuilderImpl builder = new HotDeployValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
    }

    public void tearDownGetValidator2() throws Exception {
        ValidatorResource.setValidatorBuilder(null);
    }

    public void testGetValidator2() throws Exception {
        Map properties = new HashMap();
        properties.put("maximum", new Double(111));
        properties.put("minimum", new Double(1));
        ValidatorResource.addValidator("#{a.name}", "doubleRangeValidator",
                properties);
        assertNotNull(ValidatorResource.getValidator("#{a.name}"));
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof DoubleRangeValidator);
        DoubleRangeValidator dv = (DoubleRangeValidator) ValidatorResource
                .getValidator("#{a.name}");
        assertTrue(dv.getMaximum() == 111);
        assertTrue(dv.getMinimum() == 1);
    }

    public void setUpGetValidator3() throws Exception {
        register(HogeDoubleRangeValidator.class, "doubleRangeValidator");
        NormalValidatorBuilderImpl builder = new NormalValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
    }

    public void tearDownGetValidator3() throws Exception {
        ValidatorResource.setValidatorBuilder(null);
    }

    public void testGetValidator3() throws Exception {
        Map properties = new HashMap();
        properties.put("maximum", new Double(111));
        ValidatorResource.addValidator("#{a.name}", "doubleRangeValidator",
                properties);
        assertNotNull(ValidatorResource.getValidator("#{a.name}"));
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof HogeDoubleRangeValidator);
        HogeDoubleRangeValidator dv = (HogeDoubleRangeValidator) ValidatorResource
                .getValidator("#{a.name}");
        assertTrue(dv.getMaximum() == 111);
        assertTrue(dv.NUM == 1);

        DoubleRangeValidator dv2 = (DoubleRangeValidator) ValidatorResource
                .getValidator("#{a.name}");
        assertTrue(dv2.getMaximum() == 111);
        assertTrue(dv.NUM == 2);
    }

    public void setUpAddValidator() throws Exception {
        register(DoubleRangeValidator.class, "doubleRangeValidator");
        register(LengthValidator.class, "lengthValidator");
        HotDeployValidatorBuilderImpl builder = new HotDeployValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
    }

    public void tearDownAddValidator() throws Exception {
        ValidatorResource.setValidatorBuilder(null);
    }

    public void testAddValidator() throws Exception {
        ValidatorResource.addValidator("#{a.name}", "doubleRangeValidator");
        ValidatorResource.addValidator("#{a.name}", "lengthValidator");
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof ValidatorChain);
        ValidatorChain chain = (ValidatorChain) ValidatorResource
                .getValidator("#{a.name}");
        assertTrue(chain.getValidator(0) instanceof DoubleRangeValidator);
        assertTrue(chain.getValidator(1) instanceof LengthValidator);
    }

    public void setUpAddValidator2() throws Exception {
        register(DoubleRangeValidator.class, "doubleRangeValidator");
        register(LengthValidator.class, "lengthValidator");
        HotDeployValidatorBuilderImpl builder = new HotDeployValidatorBuilderImpl();
        builder.setContainer(getContainer());
        ValidatorResource.setValidatorBuilder(builder);
    }

    public void tearDownAddValidator2() throws Exception {
        ValidatorResource.setValidatorBuilder(null);
    }

    public void testAddValidator2() throws Exception {
        Map properties = new HashMap();
        properties.put("maximum", new Double(111));
        properties.put("minimum", new Double(1));
        ValidatorResource.addValidator("#{a.name}", "doubleRangeValidator",
                properties);

        properties = new HashMap();
        properties.put("maximum", new Integer(4));
        properties.put("minimum", new Integer(1));
        ValidatorResource.addValidator("#{a.name}", "lengthValidator",
                properties);
        assertTrue(ValidatorResource.getValidator("#{a.name}") instanceof ValidatorChain);
        ValidatorChain chain = (ValidatorChain) ValidatorResource
                .getValidator("#{a.name}");
        assertTrue(chain.getValidator(0) instanceof DoubleRangeValidator);
        assertTrue(chain.getValidator(1) instanceof LengthValidator);
        DoubleRangeValidator dv = (DoubleRangeValidator) chain.getValidator(0);
        assertTrue(dv.getMaximum() == 111);
        assertTrue(dv.getMinimum() == 1);
        LengthValidator lv = (LengthValidator) chain.getValidator(1);
        assertTrue(lv.getMaximum() == 4);
        assertTrue(lv.getMinimum() == 1);
    }

    public static class HogeDoubleRangeValidator extends DoubleRangeValidator {

        public int NUM = 0;

        public double getMaximum() {
            NUM++;
            return super.getMaximum();
        }

    }
}
