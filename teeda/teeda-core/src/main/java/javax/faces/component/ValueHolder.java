package javax.faces.component;

import javax.faces.convert.Converter;

public interface ValueHolder {

	public Object getLocalValue();

	public Object getValue();

	public void setValue(Object value);

	public Converter getConverter();

	public void setConverter(Converter converter);
}
