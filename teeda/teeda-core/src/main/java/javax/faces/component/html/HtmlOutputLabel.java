package javax.faces.component.html;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class HtmlOutputLabel extends UIOutput {

    public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputLabel";

    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Label";

    private String accesskey_ = null;

    private String dir_ = null;

    private String for_ = null;

    private String lang_ = null;

    private String onblur_ = null;

    private String onclick_ = null;

    private String ondblclick_ = null;

    private String onfocus_ = null;

    private String onkeydown_ = null;

    private String onkeypress_ = null;

    private String onkeyup_ = null;

    private String onmousedown_ = null;

    private String onmousemove_ = null;

    private String onmouseout_ = null;

    private String onmouseover_ = null;

    private String onmouseup_ = null;

    private String style_ = null;

    private String styleClass_ = null;

    private String tabindex_ = null;

    private String title_ = null;

    public HtmlOutputLabel() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public void setAccesskey(String accesskey) {
        accesskey_ = accesskey;
    }

    public String getAccesskey() {
        if(accesskey_ != null){
            return accesskey_;
        }
        ValueBinding vb = getValueBinding("accesskey");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setDir(String dir) {
        dir_ = dir;
    }

    public String getDir() {
        if(dir_ != null){
            return dir_;
        }
        ValueBinding vb = getValueBinding("dir");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setFor(String forValue) {
        for_ = forValue;
    }

    public String getFor() {
        if(for_ != null)
            return for_;
        ValueBinding vb = getValueBinding("for");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setLang(String lang) {
        lang_ = lang;
    }

    public String getLang() {
        if(lang_ != null)
            return lang_;
        ValueBinding vb = getValueBinding("lang");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnblur(String onblur) {
        onblur_ = onblur;
    }

    public String getOnblur() {
        if(onblur_ != null)
            return onblur_;
        ValueBinding vb = getValueBinding("onblur");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnclick(String onclick) {
        onclick_ = onclick;
    }

    public String getOnclick() {
        if(onclick_ != null)
            return onclick_;
        ValueBinding vb = getValueBinding("onclick");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOndblclick(String ondblclick) {
        ondblclick_ = ondblclick;
    }

    public String getOndblclick() {
        if(ondblclick_ != null)
            return ondblclick_;
        ValueBinding vb = getValueBinding("ondblclick");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnfocus(String onfocus) {
        onfocus_ = onfocus;
    }

    public String getOnfocus() {
        if(onfocus_ != null)
            return onfocus_;
        ValueBinding vb = getValueBinding("onfocus");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeydown(String onkeydown) {
        onkeydown_ = onkeydown;
    }

    public String getOnkeydown() {
        if(onkeydown_ != null)
            return onkeydown_;
        ValueBinding vb = getValueBinding("onkeydown");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeypress(String onkeypress) {
        onkeypress_ = onkeypress;
    }

    public String getOnkeypress() {
        if(onkeypress_ != null)
            return onkeypress_;
        ValueBinding vb = getValueBinding("onkeypress");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnkeyup(String onkeyup) {
        onkeyup_ = onkeyup;
    }

    public String getOnkeyup() {
        if(onkeyup_ != null)
            return onkeyup_;
        ValueBinding vb = getValueBinding("onkeyup");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousedown(String onmousedown) {
        onmousedown_ = onmousedown;
    }

    public String getOnmousedown() {
        if(onmousedown_ != null)
            return onmousedown_;
        ValueBinding vb = getValueBinding("onmousedown");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnmousemove(String onmousemove) {
        onmousemove_ = onmousemove;
    }

    public String getOnmousemove() {
        if(onmousemove_ != null)
            return onmousemove_;
        ValueBinding vb = getValueBinding("onmousemove");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseout(String onmouseout) {
        onmouseout_ = onmouseout;
    }

    public String getOnmouseout() {
        if(onmouseout_ != null)
            return onmouseout_;
        ValueBinding vb = getValueBinding("onmouseout");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseover(String onmouseover) {
        onmouseover_ = onmouseover;
    }

    public String getOnmouseover() {
        if(onmouseover_ != null)
            return onmouseover_;
        ValueBinding vb = getValueBinding("onmouseover");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setOnmouseup(String onmouseup) {
        onmouseup_ = onmouseup;
    }

    public String getOnmouseup() {
        if(onmouseup_ != null)
            return onmouseup_;
        ValueBinding vb = getValueBinding("onmouseup");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setStyle(String style) {
        style_ = style;
    }

    public String getStyle() {
        if(style_ != null)
            return style_;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass) {
        styleClass_ = styleClass;
    }

    public String getStyleClass() {
        if(styleClass_ != null)
            return styleClass_;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setTabindex(String tabindex) {
        tabindex_ = tabindex;
    }

    public String getTabindex() {
        if(tabindex_ != null)
            return tabindex_;
        ValueBinding vb = getValueBinding("tabindex");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public void setTitle(String title) {
        title_ = title;
    }

    public String getTitle() {
        if(title_ != null)
            return title_;
        ValueBinding vb = getValueBinding("title");
        return vb != null ? (String)vb.getValue(getFacesContext()) : null;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[21];
        values[0] = super.saveState(context);
        values[1] = accesskey_;
        values[2] = dir_;
        values[3] = for_;
        values[4] = lang_;
        values[5] = onblur_;
        values[6] = onclick_;
        values[7] = ondblclick_;
        values[8] = onfocus_;
        values[9] = onkeydown_;
        values[10] = onkeypress_;
        values[11] = onkeyup_;
        values[12] = onmousedown_;
        values[13] = onmousemove_;
        values[14] = onmouseout_;
        values[15] = onmouseover_;
        values[16] = onmouseup_;
        values[17] = style_;
        values[18] = styleClass_;
        values[19] = tabindex_;
        values[20] = title_;
        return ((Object)(values));
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[])state;
        super.restoreState(context, values[0]);
        accesskey_ = (String)values[1];
        dir_ = (String)values[2];
        for_ = (String)values[3];
        lang_ = (String)values[4];
        onblur_ = (String)values[5];
        onclick_ = (String)values[6];
        ondblclick_ = (String)values[7];
        onfocus_ = (String)values[8];
        onkeydown_ = (String)values[9];
        onkeypress_ = (String)values[10];
        onkeyup_ = (String)values[11];
        onmousedown_ = (String)values[12];
        onmousemove_ = (String)values[13];
        onmouseout_ = (String)values[14];
        onmouseover_ = (String)values[15];
        onmouseup_ = (String)values[16];
        style_ = (String)values[17];
        styleClass_ = (String)values[18];
        tabindex_ = (String)values[19];
        title_ = (String)values[20];
    }
}
