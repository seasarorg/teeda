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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.tiger.AnnotationUtil;
import org.seasar.teeda.extension.annotation.message.MessageAggregation;

/**
 * @author shot
 * 
 */
public class TigerFacesMessageAnnotationHandler extends
		AbstractFacesMessageAnnotationHandler {

	@Override
	protected void processFields(Class componentClass, String componentName) {
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
		final Field[] fields = componentClass.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			processField(componentName, beanDesc, fields[i]);
		}
	}

	protected void processField(String componentName, BeanDesc beanDesc,
			Field field) {
		MessageAggregation annotation = field
				.getAnnotation(MessageAggregation.class);
		if (annotation == null) {
			return;
		}
		Map props = AnnotationUtil.getProperties(annotation);
		String id = (String) props.remove("id");
		FacesMessage facesMessage = null;
		if (!StringUtil.isEmpty(id)) {
			facesMessage = createFacesMessage(id);
		}
		if (facesMessage == null) {
			facesMessage = new FacesMessage();
			BeanUtil.copyProperties(props, facesMessage);
		}
		registerFacesMessage(componentName, field.getName(), facesMessage);
	}

}
