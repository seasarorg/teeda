package org.seasar.teeda.extension.spike.rhino;

import java.io.File;
import java.net.URL;

import org.mozilla.javascript.ScriptableObject;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 
 * @author mopemope
 */
public class ScriptablePageLoader {

    public ScriptableObject load(String file) throws Exception {
        File f = new File(file);
        URL url = f.toURL();

        WebClient client = new WebClient();
        Page page = client.getPage(url);
        WebWindow window = new TopLevelWindow("", client);
        window.setEnclosedPage(page);
        HtmlPage html = (HtmlPage) page;
        return (ScriptableObject) html.getScriptObject();
        
        //parentScope version
        //ScriptableObject object = (ScriptableObject) html.getScriptObject();
        //return (ScriptableObject)object.getParentScope();

    }

}
