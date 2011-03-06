/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package examples.teeda.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.UIComponentUtil;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.util.AssertionUtil;

import com.octo.captcha.service.CaptchaServiceException;

import examples.teeda.util.CaptchaUtil;

/**
 * @author Asuka Ito
 * 
 */
public class CaptchaValidator implements StateHolder, Validator {

	static {
		System.out.println("validator loaded");
	}

	public static final String MESSAGE_ID = "teeda.captcha.CAPTCHA_UNMATCH";

	private String id;

	private String messageId;

	private CapthaType capthaType;

	public String getId() {
		return id;
	}

	public String getMessageId() {
		return messageId;
	}

	/**
	 * @return Returns the capthaType.
	 */
	public CapthaType getCapthaType() {
		return capthaType;
	}

	/**
	 * @param capthaType
	 *            The capthaType to set.
	 */
	public void setCapthaType(CapthaType capthaType) {
		this.capthaType = capthaType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.StateHolder#isTransient()
	 */
	public boolean isTransient() {
		return capthaType == CapthaType.TRANSIENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object obj) {
		Object[] state = (Object[]) obj;
		id = (String) state[0];
		messageId = (String) state[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		Object[] state = new Object[] { id, messageId };

		return state;
	}

	public void setId(String salt) {
		this.id = salt;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.StateHolder#setTransient(boolean)
	 */
	public void setTransient(boolean transientValue) {
		this.capthaType = transientValue ? capthaType.TRANSIENT
				: capthaType.PERSISTENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws FacesException {
		AssertionUtil.assertNotNull("context", context);
		AssertionUtil.assertNotNull("component", component);

		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();

		boolean result;

		try {
			result = CaptchaUtil.validate(request, (String) value, id);
		} catch (CaptchaServiceException e) {
			result = false;
		}

		if (!result) {
			String mid;

			if (messageId == null || messageId.length() == 0) {
				mid = MESSAGE_ID;
			} else {
				mid = messageId;
			}

			Object args[] = new Object[] { UIComponentUtil.getLabel(component) };

			FacesMessage message = FacesMessageUtil.getMessage(context, mid,
					args);

			throw new ValidatorException(message, mid, args);
		}
	}
}
