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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.seasar.teeda.extension.component.UIText;

/**
 * @author higa
 *
 */
public class HtmlTextRenderer extends Renderer {

	public void encodeBegin(FacesContext facesContext, UIComponent component)
			throws IOException {

		if (!component.isRendered()) {
			return;
		}
		ResponseWriter writer = facesContext.getResponseWriter();
		String value = ((UIText) component).getValue();
		if (value != null) {
			writer.write(value);
		}
	}

	public void encodeEnd(FacesContext facesContext, UIComponent component)
			throws IOException {

	}

	/**
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return false;
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
	}
}