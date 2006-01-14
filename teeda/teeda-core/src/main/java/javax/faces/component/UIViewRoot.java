package javax.faces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 * TODO test.....it's kind oh hard though......
 */
public class UIViewRoot extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "javax.faces.UIViewRoot";

	public static final String COMPONENT_TYPE = "javax.faces.UIViewRoot";

	public static final String UNIQUE_ID_PREFIX = "_id";

	private String renderKitId_ = null;

	private String viewId_ = null;

	private transient List[] events_ = null;

	private int lastId_ = 0;

	private Locale locale_ = null;

	public UIViewRoot() {
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public String getRenderKitId() {
		if (renderKitId_ != null) {
			return renderKitId_;
		}
		return (String) ComponentUtils_.getValueBindingValue(this,
				"renderKitId");
	}

	public void setRenderKitId(String renderKitId) {
		renderKitId_ = renderKitId;
	}

	public String getViewId() {
		if (viewId_ != null) {
			return viewId_;
		}
		return (String) ComponentUtils_.getValueBindingValue(this, "viewId");
	}

	public void setViewId(String viewId) {
		viewId_ = viewId;
	}

	public void queueEvent(FacesEvent event) {
		ComponentUtils_.assertNotNull("event", event);
		if (events_ == null) {
			int length = PhaseId.VALUES.size();
			events_ = new List[length];
			for (int i = 0; i < length; i++) {
				events_[i] = new ArrayList();
			}
		}
		events_[event.getPhaseId().getOrdinal()].add(event);
	}

	public void processDecodes(FacesContext context) {
		super.processDecodes(context);
		broadcastEvents(context, PhaseId.APPLY_REQUEST_VALUES);
		clearEventsIfResponseRendered(context);
	}

	public void encodeBegin(FacesContext context) throws IOException {
		lastId_ = 0;
		super.encodeBegin(context);
	}

	public void processValidators(FacesContext context) {
		super.processValidators(context);
		broadcastEvents(context, PhaseId.PROCESS_VALIDATIONS);
		clearEventsIfResponseRendered(context);
	}

	public void processUpdates(FacesContext context) {
		super.processUpdates(context);
		broadcastEvents(context, PhaseId.UPDATE_MODEL_VALUES);
	}

	public void processApplication(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		broadcastEvents(context, PhaseId.INVOKE_APPLICATION);
	}

	public String createUniqueId() {
		return UNIQUE_ID_PREFIX + (lastId_++);
	}

	public Locale getLocale() {
		if (locale_ != null) {
			return locale_;
		}
		Locale locale = null;
		FacesContext context = getFacesContext();
		if (getValueBinding("locale") != null) {
			Object obj = ComponentUtils_.getValueBindingValue(this, "locale");
			if (obj == null) {
				locale = ComponentUtils_.calculateLocale(context);
			} else if (obj instanceof Locale) {
				locale = (Locale) obj;
			} else if (obj instanceof String) {
				locale = getLocaleFromString((String) obj);
			}
		} else {
			locale = ComponentUtils_.calculateLocale(context);
		}
		return locale;
	}

	public void setLocale(Locale locale) {
		locale_ = locale;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		super.restoreState(context, values[0]);
		renderKitId_ = (String) values[1];
		viewId_ = (String) values[2];
		locale_ = (Locale) values[3];
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[4];
		values[0] = super.saveState(context);
		values[1] = renderKitId_;
		values[2] = viewId_;
		values[3] = locale_;
		return values;
	}

	private Locale getLocaleFromString(String localeStr) {
		Locale locale = Locale.getDefault();
		if (ComponentUtils_.isLocaleShort(localeStr)) {
			locale = new Locale(localeStr);
		} else if (ComponentUtils_.isLocaleLong(localeStr)) {
			String language = localeStr.substring(0, 2);
			String country = localeStr.substring(3, 5);
			locale = new Locale(language, country);
		}
		return locale;
	}

	private void broadcastEvents(FacesContext context, PhaseId phaseId) {
		if (events_ == null) {
			return;
		}
		boolean hasMoreAnyPhaseEvents = true;
		boolean hasMoreCurrentPhaseEvents = true;

		List eventsForAnyPhase = events_[PhaseId.ANY_PHASE.getOrdinal()];
		List eventsForCurrentPhase = events_[phaseId.getOrdinal()];

		for (; hasMoreAnyPhaseEvents || hasMoreCurrentPhaseEvents;) {
			broadcastEventsReally(eventsForAnyPhase);
			hasMoreAnyPhaseEvents = hasMorePhaseEvents(eventsForAnyPhase);

			broadcastEventsReally(eventsForCurrentPhase);
			hasMoreCurrentPhaseEvents = hasMorePhaseEvents(eventsForCurrentPhase);
		}
	}

	private void broadcastEventsReally(List events) {
		if (events != null) {
			for (int cursor = 0; cursor < events.size();) {
				FacesEvent facesEvent = (FacesEvent) events.get(cursor);
				UIComponent source = facesEvent.getComponent();
				try {
					source.broadcast(facesEvent);
				} catch (AbortProcessingException ignore) {
				}
				events.remove(cursor);
			}
		}
	}

	private boolean hasMorePhaseEvents(List events) {
		return (events != null && events.size() > 0);
	}

	private void clearEventsIfResponseRendered(FacesContext context) {
		if (context.getRenderResponse()||context.getResponseComplete()) {
			events_ = null;
		}
	}
}
