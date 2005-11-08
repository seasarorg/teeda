package javax.faces.component;

import java.io.Serializable;

import javax.faces.context.FacesContext;

class AttachedObjectStateWrapper_ implements Serializable {

	private Object savedState = null;

	private String className = null;

	private boolean isSavedStateHolder = false;

	public AttachedObjectStateWrapper_(FacesContext context, Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}
		className = obj.getClass().getName();
		if (obj instanceof StateHolder) {
			StateHolder stateHolder = (StateHolder) obj;
			if (!stateHolder.isTransient()) {
				savedState = stateHolder.saveState(context);
				isSavedStateHolder = true;
			}
		} else {
			if (obj instanceof Serializable) {
				savedState = obj;
			}
		}
	}

	public Object restore(FacesContext context) throws IllegalStateException{
		Object result = null;
		if (savedState == null) {
			return null;
		}
		if (!isSavedStateHolder) {
			result = savedState;
		} else {
			if (isSavedStateHolder) {
				try {
					ClassLoader loader = ClassLoaderUtil_.getClassLoader(context);
					Class clazz = ClassLoaderUtil_.loadClass(loader, className);
					result = clazz.newInstance();
				} catch (ClassNotFoundException e) {
					throw new IllegalStateException(e.getMessage());					
				} catch (InstantiationException e) {
					throw new IllegalStateException(e.getMessage());
				} catch (IllegalAccessException e) {
					throw new IllegalStateException(e.getMessage());
				}
				if(result != null && result instanceof StateHolder){
					((StateHolder)result).restoreState(context, savedState);
				}
			}
		}
		return result;
	}

}
