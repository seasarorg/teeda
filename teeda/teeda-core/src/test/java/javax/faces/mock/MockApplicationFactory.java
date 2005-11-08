package javax.faces.mock;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.Validator;

public class MockApplicationFactory extends ApplicationFactory {

	private Application app = new Application() {

        private ActionListener actionListener_;
        
		public ActionListener getActionListener() {
			return actionListener_;
		}

		public void setActionListener(ActionListener listener) {
            actionListener_ = listener;
		}

		public Locale getDefaultLocale() {
			return null;
		}

		public void setDefaultLocale(Locale locale) {
		}

		public String getDefaultRenderKitId() {
			return null;
		}

		public void setDefaultRenderKitId(String renderKitId) {
		}

		public String getMessageBundle() {
			return null;
		}

		public void setMessageBundle(String bundle) {
		}

		public NavigationHandler getNavigationHandler() {
			return null;
		}

		public void setNavigationHandler(NavigationHandler handler) {
		}

		public PropertyResolver getPropertyResolver() {
			return null;
		}

		public void setPropertyResolver(PropertyResolver resolver) {
		}

		public VariableResolver getVariableResolver() {
			return null;
		}

		public void setVariableResolver(VariableResolver resolver) {
		}

		public ViewHandler getViewHandler() {
			return null;
		}

		public void setViewHandler(ViewHandler handler) {
		}

		public StateManager getStateManager() {
			return null;
		}

		public void setStateManager(StateManager manager) {
		}

		public void addComponent(String componentType, String componentClass) {
		}

		public UIComponent createComponent(String componentType)
				throws FacesException {
			return null;
		}

		public UIComponent createComponent(ValueBinding componentBinding,
				FacesContext context, String componentType)
				throws FacesException {
			return null;
		}

		public Iterator getComponentTypes() {
			return null;
		}

		public void addConverter(String converterId, String converterClass) {
		}

		public void addConverter(Class targetClass, String converterClass) {
		}

		public Converter createConverter(String converterId) {
			return null;
		}

		public Converter createConverter(Class targetClass) {
			return null;
		}

		public Iterator getConverterIds() {
			return null;
		}

		public Iterator getConverterTypes() {
			return null;
		}

		public MethodBinding createMethodBinding(String ref, Class[] params)
				throws ReferenceSyntaxException {
			return null;
		}

		public Iterator getSupportedLocales() {
			return null;
		}

		public void setSupportedLocales(Collection locales) {
		}

		public void addValidator(String validatorId, String validatorClass) {
		}

		public Validator createValidator(String validatorId)
				throws FacesException {
			return null;
		}

		public Iterator getValidatorIds() {
			return null;
		}

		public ValueBinding createValueBinding(String ref)
				throws ReferenceSyntaxException {
			return null;
		}

	};

	public Application getApplication() {
		return app;
	}

	public void setApplication(Application application) {
		app = application;
	}

}