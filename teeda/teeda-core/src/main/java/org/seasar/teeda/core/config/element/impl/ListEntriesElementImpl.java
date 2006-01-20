package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.ListEntriesElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class ListEntriesElementImpl implements ListEntriesElement {

    private String valueClassName_;
    private List values_;
    private boolean nullValue_;
    public ListEntriesElementImpl(){
        values_ = new ArrayList();
    }
    
	public void setValueClass(String valueClassName) {
        valueClassName_ = valueClassName;
	}

	public void addValue(String value) {
        values_.add(value);
	}

	public String getValueClass() {
		return valueClassName_;
	}

	public List getValues() {
		return values_;
	}

	public void setNullValue(boolean nullValue) {
        nullValue_ = nullValue;
        values_.add(null);
	}

	public boolean isNullValue() {
		return nullValue_;
	}

}
