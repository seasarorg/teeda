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
package org.seasar.teeda.core.render.autoregister;

import java.lang.reflect.Field;

import javax.faces.render.Renderer;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.autoregister.JarComponentAutoRegister;
import org.seasar.framework.container.factory.AnnotationHandler;
import org.seasar.framework.container.factory.AnnotationHandlerFactory;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ModifierUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.support.HtmlRenderKitKeyGenerateUtil;

/**
 * @author shot
 *
 */
public class TeedaRendererJarComponentAutoRegister extends
        JarComponentAutoRegister {

    private String familyFieldName;

    private String renderTypeFieldName;

    public TeedaRendererJarComponentAutoRegister() {
        familyFieldName = JsfConstants.COMPONENT_FAMILY;
        renderTypeFieldName = JsfConstants.RENDERER_TYPE;
    }

    protected void register(final String className) {
        final AnnotationHandler annoHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        final ComponentDef cd = annoHandler.createComponentDef(className,
                getInstanceDef(), getAutoBindingDef(), isExternalBinding());
        final Class componentClass = cd.getComponentClass();
        if (ClassUtil.isAssignableFrom(Renderer.class, componentClass)) {
            String family = getConstantValue(componentClass,
                    getFamilyFieldName());
            String renderType = getConstantValue(componentClass,
                    getRenderTypeFieldName());
            final String rendererComponentName = HtmlRenderKitKeyGenerateUtil
                    .getGeneratedKey(family, renderType);
            cd.setComponentName(rendererComponentName);
            annoHandler.appendDI(cd);
            annoHandler.appendAspect(cd);
            annoHandler.appendInitMethod(cd);
            customize(cd);
            if (!getContainer().hasComponentDef(rendererComponentName)) {
                getContainer().register(cd);
            }
        }
    }

    public void setFamilyFieldName(String familyFieldName) {
        this.familyFieldName = familyFieldName;
    }

    public void setRenderTypeFieldName(String renderTypeFieldName) {
        this.renderTypeFieldName = renderTypeFieldName;
    }

    public String getFamilyFieldName() {
        return familyFieldName;
    }

    public String getRenderTypeFieldName() {
        return renderTypeFieldName;
    }

    protected String getConstantValue(Class clazz, String fieldName) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        if (beanDesc.hasField(fieldName)) {
            Field f = beanDesc.getField(fieldName);
            if (isPublicFinalConstant(f)) {
                return (String) beanDesc.getFieldValue(fieldName, null);
            }
        }
        return null;
    }

    private static boolean isPublicFinalConstant(Field field) {
        return ModifierUtil.isPublicStaticFinal(field.getModifiers())
                && field.getType().equals(String.class);
    }

}
