/**
 *
 */
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
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
	protected void processFields(S2Container container, Class componentClass,
			String componentName, NamingConvention namingConvention,
			BeanDesc beanDesc) {
		final Field[] fields = componentClass.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			processField(container, componentClass, componentName,
					namingConvention, beanDesc, fields[i]);
		}
	}

	protected void processField(S2Container container, Class componentClass,
			String componentName, NamingConvention namingConvention,
			BeanDesc beanDesc, Field field) {
		MessageAggregation annotation = field
				.getAnnotation(MessageAggregation.class);
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
