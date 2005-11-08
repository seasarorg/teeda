package javax.faces.el;

public abstract class PropertyResolver {
	public PropertyResolver() {
	}

	public abstract Class getType(Object base, int index)
			throws EvaluationException, PropertyNotFoundException;

	public abstract Class getType(Object base, java.lang.Object property)
			throws EvaluationException, PropertyNotFoundException;

	public abstract Object getValue(Object base, int index)
			throws EvaluationException, PropertyNotFoundException;

	public abstract Object getValue(Object base, java.lang.Object property)
			throws EvaluationException, PropertyNotFoundException;

	public abstract boolean isReadOnly(Object base, int index)
			throws EvaluationException, PropertyNotFoundException;

	public abstract boolean isReadOnly(Object base, java.lang.Object property)
			throws EvaluationException, PropertyNotFoundException;

	public abstract void setValue(Object base, int index, java.lang.Object value)
			throws EvaluationException, PropertyNotFoundException;

	public abstract void setValue(Object base, Object property,
			java.lang.Object value) throws EvaluationException,
			PropertyNotFoundException;

}
