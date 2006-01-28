/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.validator;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


public class DoubleRangeValidator implements Validator, StateHolder {

	public static final String VALIDATOR_ID = "javax.faces.DoubleRange";

	public static final String MAXIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MAXIMUM";
	
	public static final String MINIMUM_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.MINIMUM";
	
	public static final String TYPE_MESSAGE_ID = "javax.faces.validator.DoubleRangeValidator.TYPE";
		
	private Double maximum_ = null;
	
	private Double minimum_ = null;
	
	private boolean transientValue_ = false;
	
	public DoubleRangeValidator(){
		super();
	}
	
	public DoubleRangeValidator(double maximum){
		super();
		maximum_ = new Double(maximum);
	}
	
	public DoubleRangeValidator(double maximum, double minimum){
		super();
		maximum_ = new Double(maximum);
		minimum_ = new Double(minimum);
	}
	
	public boolean equals(Object obj){
		if(!(obj instanceof DoubleRangeValidator)){
			return false;
		}
		
		DoubleRangeValidator v = (DoubleRangeValidator)obj;
		
		if((maximum_ != null && v.maximum_ == null) && (maximum_ == null && v.maximum_ != null)){
			return false;
		}
		
		if((minimum_ != null && v.minimum_ == null) && (minimum_ == null && v.minimum_ != null)){
			return false;
		}		
		
		return (this.getMaximum() == v.getMaximum() && this.getMinimum() == v.getMinimum());
	}
	
	public double getMaximum(){
		return (maximum_ != null) ? maximum_.doubleValue() : Double.MAX_VALUE;
	}
	
	public double getMinimum(){
		return (minimum_ != null) ? minimum_.doubleValue() : Double.MIN_VALUE;
	}
	
	public boolean isTransient() {
		return transientValue_;
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] obj = (Object[])state;
		maximum_ = (Double)obj[0];
		minimum_ = (Double)obj[1];
	}

	public Object saveState(FacesContext context) {
		Object[] obj = new Object[2];
		obj[0] = maximum_;
		obj[1] = minimum_;
		return obj;
	}

	public void setMaximum(double maximum){	
		maximum_ = new Double(maximum);
	}
	
	public void setMinimum(double minimum){
		minimum_ = new Double(minimum);
	}

	public void setTransient(boolean transientValue) {
		transientValue_ = transientValue;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		assertNotNull(context);
		assertNotNull(component);
		
		if(value == null){
			return;
		}

		double doubleValue;
		try{
			doubleValue = parseDoubleValue(value);
			
			if(maximum_ != null && minimum_ != null){
				
				double minValue = minimum_.doubleValue();
				double maxValue = maximum_.doubleValue();
				
				if(doubleValue < minValue || doubleValue > maxValue){
					Object[] args = {minimum_, maximum_, component.getId()};
					throw new ValidatorException(FacesMessageUtils_.getMessage(context, NOT_IN_RANGE_MESSAGE_ID, args));
				}
				
			}else if(minimum_ != null){
				
				double minValue = minimum_.doubleValue();
				if(doubleValue < minValue){
					Object[] args = {minimum_, component.getId()};
					throw new ValidatorException(FacesMessageUtils_.getMessage(context, MINIMUM_MESSAGE_ID, args));
				}
				
			}else if(maximum_ != null){
				
				double maxValue = maximum_.doubleValue();
				if(doubleValue > maxValue){
					Object[] args = {maximum_, component.getId()};
					throw new ValidatorException(FacesMessageUtils_.getMessage(context, MAXIMUM_MESSAGE_ID, args));
				}
			}
			
		}catch(NumberFormatException e){
			throw new ValidatorException(
					FacesMessageUtils_.getMessage(context, TYPE_MESSAGE_ID, new Object[]{component.getId()})
				);
		}
		
	}
	
	private static void assertNotNull(Object obj){
		if(obj == null){
			throw new NullPointerException();
		}
	}
	
	private static double parseDoubleValue(Object obj) throws NumberFormatException{
		
		double value;
		if(obj instanceof Number){
			value = ((Number)obj).doubleValue();
		}else{
			value = Double.parseDouble(obj.toString());
		}
		return value;
	}
	
}
