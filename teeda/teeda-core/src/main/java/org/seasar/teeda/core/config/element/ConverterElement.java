package org.seasar.teeda.core.config.element;

public interface ConverterElement extends JsfConfig, AttributeHolder, PropertyHolder{
    
    public void setConverterId(String converterId);

    public void setConverterForClass(String converterForClass);

    public void setConverterClass(String converterClass);

    public String getConverterClass();

    public String getConverterForClass();

    public String getConverterId();
}