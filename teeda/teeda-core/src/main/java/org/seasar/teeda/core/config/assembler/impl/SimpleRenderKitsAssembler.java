package org.seasar.teeda.core.config.assembler.impl;

import java.util.Iterator;
import java.util.Map;

import javax.faces.render.RenderKitFactory;

import org.seasar.teeda.core.config.assembler.RenderKitsAssembler;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.util.FactoryFinderUtil;
import org.seasar.teeda.core.util.IteratorUtil;

//TODO make it done.
public class SimpleRenderKitsAssembler extends RenderKitsAssembler {

    public SimpleRenderKitsAssembler(Map renderKits) {
        super(renderKits);
    }

    protected void setupChildAssembler() {
        for(Iterator itr = IteratorUtil.getIterator(getRenderKits());itr.hasNext();){
            Map.Entry entry = (Map.Entry)itr.next();
            String renderKitId = (String)entry.getKey();
            RenderKitElement renderKitElement = (RenderKitElement)entry.getValue();
            String className = renderKitElement.getRenderKitClass();
        }
    }

    public void assemble() {
        RenderKitFactory renderKitFactory = FactoryFinderUtil.getRenderKitFactory();
    }

}
