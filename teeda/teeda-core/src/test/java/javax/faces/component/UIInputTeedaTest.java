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
package javax.faces.component;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.ValidatorException;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.resource.ValidatorResource;
import org.seasar.teeda.core.resource.ValidatorResourceImpl;

/**
 * @author manhole
 */
public class UIInputTeedaTest extends UIOutputTeedaTest {

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        createUIInput();
        // TODO test
    }

    public final void testHandleValidationException() throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setId("id");
        ValidatorException ve = new ValidatorException(new FacesMessage("aaa"));
        FacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
        context.setViewRoot(root);

        // ## Act ##
        input.handleValidationException(context, ve);

        // ## Assert ##
        Iterator itr = context.getMessages();
        Object o = itr.next();
        assertTrue(o instanceof FacesMessage);
        FacesMessage message = (FacesMessage) o;
        assertEquals("aaa", message.getSummary());
    }

    public void testValidateValue() throws Exception {
        // ## Arrange ##
        UIInput input = new UIInput();
        input.setValid(true);
        input.setRequired(true);

        MockValueBinding vb = new MockValueBinding();
        vb.setExpressionString("#{a.name}");
        input.setValueBinding("value", vb);

        ValidatorResource resource = new ValidatorResourceImpl();
        resource.addValidatorResource("#{a.name}", new LengthValidator(5, 2));
        input.setValidatorResource(resource);

        input.validateValue(getFacesContext(), new Integer(6));

        Iterator itr = getFacesContext().getMessages();
        Object o = itr.next();
        assertTrue(o instanceof FacesMessage);
        FacesMessage message = (FacesMessage) o;
        assertNotNull(message);
        assertEquals(FacesMessage.SEVERITY_ERROR, message.getSeverity());
    }

    private UIInput createUIInput() {
        return (UIInput) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIInput();
    }

}
