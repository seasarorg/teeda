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
package examples.teeda.render;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.render.html.THtmlOutputTextRenderer;

public class HogeOutputRenderer extends THtmlOutputTextRenderer {

	public HogeOutputRenderer() {
		System.out.println("hogehoge");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.teeda.extension.render.html.THtmlOutputTextRenderer#encodeHtmlOutputTextEnd(javax.faces.context.FacesContext,
	 *      javax.faces.component.html.HtmlOutputText)
	 */
	@Override
	protected void encodeHtmlOutputTextEnd(FacesContext arg0,
			HtmlOutputText arg1) throws IOException {
		System.out.println("encodeHtmlOutputTextEnd");
		super.encodeHtmlOutputTextEnd(arg0, arg1);
	}

}
