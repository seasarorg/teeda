package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.FactoryElement;

/**
 * @author Shinpei Ohtani(aka shot)
 * 
 */
public class FactoryElementImpl implements FactoryElement {

	private List applicationFactories_;
	private List facesContextFactories_;
	private List lifecycleFactories_;
	private List renderKitFactories_;
	public FactoryElementImpl(){
		applicationFactories_ = new ArrayList();
		facesContextFactories_ = new ArrayList();
		lifecycleFactories_ = new ArrayList();
		renderKitFactories_ = new ArrayList();
	}
    
	public void addApplicationFactory(String applicationFactory) {
		applicationFactories_.add(applicationFactory);
	}

	public void addFacesContextFactory(String facesContextFactory) {
		facesContextFactories_.add(facesContextFactory);
	}

	public void addLifecycleFactory(String lifecycleFactory) {
		lifecycleFactories_.add(lifecycleFactory);
	}

	public void addRenderKitFactory(String renderKitFactory) {
		renderKitFactories_.add(renderKitFactory);
	}

	public List getApplicationFactories() {
		return applicationFactories_;
	}

	public List getFacesContextFactories() {
		return facesContextFactories_;
	}

	public List getLifecycleFactories() {
		return lifecycleFactories_;
	}

	public List getRenderKitFactories() {
		return renderKitFactories_;
	}

}
