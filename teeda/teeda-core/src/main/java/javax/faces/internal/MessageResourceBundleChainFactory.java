/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.message.MessageResourceBundleFactory;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 */
public class MessageResourceBundleChainFactory {

    private static MessageResourceBundle[] userBundles = null;

    private MessageResourceBundleChainFactory() {
    }

    public static MessageResourceBundle createChain(String bundleName,
            Locale locale) {
        if (!DIContainerUtil.hasContainer()
                || !DIContainerUtil.hasComponent(MessageResourceBundle.class)) {
            return createSimpleMessageResourceBundle(bundleName, locale);
        }
        MessageResourceBundleChain chain = new MessageResourceBundleChain();
        if (userBundles == null) {
            List list = new ArrayList();
            Object[] components = DIContainerUtil
                    .findAllComponents(MessageResourceBundle.class);
            for (int i = 0; i < components.length; i++) {
                MessageResourceBundle bundle = (MessageResourceBundle) components[i];
                chain.addMessageResourceBundle(bundle);
                list.add(bundle);
            }
            userBundles = (MessageResourceBundle[]) list
                    .toArray(new MessageResourceBundle[components.length]);
        } else {
            for (int i = 0; i < userBundles.length; i++) {
                chain.addMessageResourceBundle(userBundles[i]);
            }
        }
        MessageResourceBundle defaultBundle = createSimpleMessageResourceBundle(
                bundleName, locale);
        if (defaultBundle != null) {
            chain.addMessageResourceBundle(defaultBundle);
        }
        return chain;
    }

    public static void clearUserBundles() {
        userBundles = null;
    }

    private static MessageResourceBundle createSimpleMessageResourceBundle(
            String bundleName, Locale locale) {
        if (HotdeployUtil.isHotdeploy()) {
            return MessageResourceBundleFactory.getNullableBundle(bundleName,
                    locale);
        } else {
            return new JavaMessageResourceBundle(bundleName, locale);
        }
    }

}
