package javax.faces.application;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacesMessage implements Serializable{

	public static final String FACES_MESSAGES = "javax.faces.Messages";

	public static final Severity SEVERITY_INFO = new Severity("INFO", 1);

	public static final Severity SEVERITY_WARN = new Severity("WARN", 2);

	public static final Severity SEVERITY_ERROR = new Severity("ERROR", 3);

	public static final Severity SEVERITY_FATAL = new Severity("FATAL", 4);

	private static final Severity[] values = {SEVERITY_INFO, SEVERITY_WARN, SEVERITY_ERROR, SEVERITY_FATAL};

	public static final List VALUES;
	
	public static final Map VALUES_MAP;
	
	static{
		VALUES = Collections.unmodifiableList(Arrays.asList(values));
		Map map = new HashMap();
		for(int i = 0; i < values.length; i++){
			map.put(values[i].toString(), values[i]);
		}
		VALUES_MAP = Collections.unmodifiableMap(map);
	}
	
	private String summary_;
	private String detail_;
	private Severity severity_;
	
	public FacesMessage(){
	}
	
	public FacesMessage(String summary){
		summary_ = summary;
	}
	
	public FacesMessage(String summary, String detail){
		summary_ = summary;
		detail_ = detail;
	}
	
	public FacesMessage(Severity severity, String summary, String detail){
		severity_ = severity;
		summary_ = summary;
		detail_ = detail;
	}
		
	public String getDetail() {
		return (detail_ != null) ? detail_ : summary_;
	}
	
	public void setDetail(String detail) {
		detail_ = detail;
	}
	
	public Severity getSeverity() {
		return severity_;
	}
	
	public void setSeverity(Severity severity) {
		severity_ = severity;
	}
	
	public String getSummary() {
		return summary_;
	}
	
	public void setSummary(String summary) {
		summary_ = summary;
	}
	
	public static class Severity implements Comparable{
		private String type_;
		private int ordinal_;
		
		public Severity(String type, int ordinal){
			type_ = type;
			ordinal_ = ordinal;
		}
		
		public int getOrdinal(){
			return ordinal_;
		}
		
		public String toString(){
			return type_;
		}
		
		public int compareTo(Object o){
			return ordinal_ - ((Severity)o).ordinal_;
		}
	}
}
