package org.seasar.teeda.core.taglib;

import javax.faces.component.UIComponent;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.util.BindingUtil;

public class UIComponentTagPropertyUtil {

    protected UIComponentTagPropertyUtil() {
    }

    public static void setComponentProperty(UIComponent component,
            String propertyName, String value) {
        if (value == null) {
            return;
        }
        if (BindingUtil.isValueReference(value)) {
            BindingUtil.setValueBinding(component, propertyName, value);
        } else {
            setBeanProperty(component, propertyName, value);
        }
    }

    private static void setBeanProperty(UIComponent component,
            String propertyName, String value) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
        if (beanDesc.hasPropertyDesc(propertyName)) {
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            if (pd.hasWriteMethod()) {
                pd.setValue(component, value);
            }
        } else {
            component.getAttributes().put(propertyName, value);
        }
    }

}
