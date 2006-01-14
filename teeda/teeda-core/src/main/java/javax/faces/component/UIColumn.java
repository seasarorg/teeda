/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

/**
 * @author Shinpei Ohtani
 */
public class UIColumn extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "javax.faces.Column";

	public static final String COMPONENT_TYPE = "javax.faces.Column";
	
    private static final String FOOTER_FACET_NAME = "footer";

    private static final String HEADER_FACET_NAME = "header";

	public UIColumn(){
	}
	
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public UIComponent getFooter(){
		return getFacet(FOOTER_FACET_NAME);
	}

	public void setFooter(UIComponent footer){
		ComponentUtils_.assertNotNull("footer", footer);
		getFacets().put(FOOTER_FACET_NAME, footer);
	}
	
	public UIComponent getHeader(){
		return getFacet(HEADER_FACET_NAME);
	}

	public void setHeader(UIComponent header){
		ComponentUtils_.assertNotNull("header", header);
		getFacets().put(HEADER_FACET_NAME, header);
	}

}
