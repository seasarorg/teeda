package org.seasar.teeda.core.lifecycle;

import java.util.Iterator;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.teeda.core.util.LifecycleUtil;


public abstract class AbstractPhase implements Phase{
    
    public final void prePhase(FacesContext context){
        Lifecycle lifecycle = getLifecycle(context);
        PhaseListener[] phaseListeners = lifecycle.getPhaseListeners();
        if(phaseListeners != null){
            for(int i = 0;i < phaseListeners.length;i++){
                PhaseListener phaseListener = phaseListeners[i];
                PhaseId phaseId = getCurrentPhaseId();
                if(isTargetListener(phaseListener, phaseId)){
                    phaseListener.beforePhase(new PhaseEvent(context, phaseId, lifecycle));
                }
            }
        }
    }

    public void doPhase(FacesContext context){
        prePhase(context);
        executePhase(context);
        postPhase(context);
    }
    
    public final void postPhase(FacesContext context){
        Lifecycle lifecycle = getLifecycle(context);
        PhaseListener[] phaseListeners = lifecycle.getPhaseListeners();
        if(phaseListeners != null){
            for(int i = phaseListeners.length;i >= 0 ;i--){
                PhaseListener phaseListener = phaseListeners[i];
                PhaseId phaseId = getCurrentPhaseId();
                if(isTargetListener(phaseListener, phaseId)){
                    phaseListener.afterPhase(new PhaseEvent(context, phaseId, lifecycle));
                }
            }
        }
    }

    protected void initializeChildren(FacesContext context, UIComponent component) {
        for (Iterator i = component.getFacetsAndChildren(); i.hasNext();) {
            UIComponent child = (UIComponent) i.next();
            if (child instanceof EditableValueHolder) {
                EditableValueHolder editableValueHolder = (EditableValueHolder) child;
                editableValueHolder.setValid(true);
                editableValueHolder.setSubmittedValue(null);
                editableValueHolder.setValue(null);
                editableValueHolder.setLocalValueSet(false);
            }//For each
            initializeChildren(context, child);
        }
    }

    protected boolean isTargetListener(PhaseListener listener, PhaseId phaseId) {
        int listenerOrdinal = listener.getPhaseId().getOrdinal();
        return listenerOrdinal == PhaseId.ANY_PHASE.getOrdinal()
                || listenerOrdinal == phaseId.getOrdinal();
    }

    protected final Lifecycle getLifecycle(FacesContext context){
        ExternalContext externalContext = context.getExternalContext();
        Lifecycle lifecycle = LifecycleUtil.getLifecycle(externalContext);
        return lifecycle;
    }
    
    protected abstract void executePhase(FacesContext context);
    
    protected abstract PhaseId getCurrentPhaseId();
}
