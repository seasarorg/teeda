package examples.teeda.render;

import java.io.IOException;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.render.html.THtmlOutputTextRenderer;

public class HogeOutputRenderer extends THtmlOutputTextRenderer {

	public HogeOutputRenderer() {
		System.out.println("hogehoge");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.teeda.extension.render.html.THtmlOutputTextRenderer#encodeHtmlOutputTextEnd(javax.faces.context.FacesContext,
	 *      javax.faces.component.html.HtmlOutputText)
	 */
	@Override
	protected void encodeHtmlOutputTextEnd(FacesContext arg0,
			HtmlOutputText arg1) throws IOException {
		System.out.println("増えるわかめちゃんか・・・");
		super.encodeHtmlOutputTextEnd(arg0, arg1);
	}

}
