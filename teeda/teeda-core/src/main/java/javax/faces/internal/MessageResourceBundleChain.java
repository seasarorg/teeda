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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.seasar.framework.message.MessageResourceBundle;
import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class MessageResourceBundleChain implements MessageResourceBundle {

    private List bundles = new ArrayList();

    public String get(String key) {
        for (Iterator itr = bundles.iterator(); itr.hasNext();) {
            MessageResourceBundle bundle = (MessageResourceBundle) itr.next();
            String str = bundle.get(key);
            if (str != null) {
                return str;
            }
        }
        return null;
    }

    public MessageResourceBundle getParent() {
        throw new UnsupportedOperationException();
    }

    public void setParent(MessageResourceBundle parent) {
        throw new UnsupportedOperationException();
    }

    public void addMessageResourceBundle(
            MessageResourceBundle messageResourceBundle) {
        AssertionUtil.assertNotNull("messageResourceBundle",
                messageResourceBundle);
        bundles.add(messageResourceBundle);
    }
}
