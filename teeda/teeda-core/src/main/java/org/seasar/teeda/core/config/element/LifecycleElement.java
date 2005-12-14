package org.seasar.teeda.core.config.element;

import java.util.List;

public interface LifecycleElement extends JsfConfig {

    public void addPhaseListener(String phaseListener);

    public List getPhaseListeners();
}
