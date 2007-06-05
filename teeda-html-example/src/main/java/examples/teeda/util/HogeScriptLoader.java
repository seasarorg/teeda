package examples.teeda.util;

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.component.html.THtmlCommandButton;
import org.seasar.teeda.extension.util.DoubleSubmitProtectionLoader;
import org.seasar.teeda.extension.util.VirtualResource;

public class HogeScriptLoader implements DoubleSubmitProtectionLoader {

	public void loadScript(FacesContext context, THtmlCommandButton button) {
		AssertionUtil.assertNotNull("context", context);
		AssertionUtil.assertNotNull("button", button);
		final String path = ResourceUtil.getResourcePath(
				"org.seasar.teeda.ajax.js.kumu", "js");
		VirtualResource.addJsResource(context, path);
		final String key = button.getClass().getName() + "." + button.getId();
		final StringBuffer buf = new StringBuffer(200);
		buf.append("Kumu.dynamicLoad('disabled');\n");
		buf.append("DisabledConf = {\n");
		buf.append(" time : ").append(button.getTime()).append(",\n");
		final String submitFunction = FacesMessageUtil.getSummary(context,
				SUBMIT_MESSAGE_KEY, null);
		if (submitFunction != null) {
			buf.append(" submitMessage : ");
			buf.append("function() { alert('aaaaa') }").append("\n");
		}
		buf.append("}\n");
		VirtualResource.addInlineJsResource(context, key, buf.toString());
	}

}
