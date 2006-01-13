package javax.faces.component.html;

import javax.faces.component.TestUIOutput;
import javax.faces.component.UIComponent;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

public class HtmlOutputTextTest extends TestUIOutput {

    public void testDefaultRendererType() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals("javax.faces.Text", htmlOutputText.getRendererType());
    }

    public void testSetGetEscape() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        // default is true
        assertEquals(true, htmlOutputText.isEscape());
        htmlOutputText.setEscape(false);
        assertEquals(false, htmlOutputText.isEscape());
    }

    public void testSetGetEscape_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.FALSE);
        htmlOutputText.setValueBinding("escape", vb);
        assertEquals(false, htmlOutputText.isEscape());
    }

    public void testSetGetStyle() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals(null, htmlOutputText.getStyle());
        htmlOutputText.setStyle("foo style");
        assertEquals("foo style", htmlOutputText.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        htmlOutputText.setValueBinding("style", vb);
        assertEquals("bar style", htmlOutputText.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals(null, htmlOutputText.getStyleClass());
        htmlOutputText.setStyleClass("foo class");
        assertEquals("foo class", htmlOutputText.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar class");
        htmlOutputText.setValueBinding("styleClass", vb);
        assertEquals("bar class", htmlOutputText.getStyleClass());
    }

    public void testSetGetTitle() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals(null, htmlOutputText.getTitle());
        htmlOutputText.setTitle("foo title");
        assertEquals("foo title", htmlOutputText.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        htmlOutputText.setValueBinding("title", vb);
        assertEquals("bar title", htmlOutputText.getTitle());
    }

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();
        HtmlOutputText htmlOutputText1 = createHtmlOutputText();
        htmlOutputText1.setEscape(false);
        htmlOutputText1.setTitle("foo title");
        htmlOutputText1.setStyle("foo style");
        htmlOutputText1.setStyleClass("foo styleClass");
        MockFacesContext context = getFacesContext();
        Object state = htmlOutputText1.saveState(context);
    
        HtmlOutputText htmlOutputText2 = createHtmlOutputText();
        htmlOutputText2.restoreState(context, state);
    
        assertEquals(htmlOutputText1.isEscape(), htmlOutputText2.isEscape());
        assertEquals(htmlOutputText1.getTitle(), htmlOutputText2.getTitle());
        assertEquals(htmlOutputText1.getStyle(), htmlOutputText2.getStyle());
        assertEquals(htmlOutputText1.getStyleClass(), htmlOutputText2
            .getStyleClass());
    }

    private HtmlOutputText createHtmlOutputText() {
        return (HtmlOutputText) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputText();
    }

}
