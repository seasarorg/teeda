package org.seasar.teeda.extension.spike.rhino;

import org.mozilla.javascript.ScriptableObject;

/**
 * 
 * @author mopemope
 */
public class ScriptablePageLoader {

    public ScriptableObject load(String file) throws Exception {
        return null;
        //        File f = new File(file);
        //        URL url = f.toURL();
        //
        //        WebClient client = new WebClient();
        //        Page page = client.getPage(url);
        //        WebWindow window = new TopLevelWindow("", client);
        //        window.setEnclosedPage(page);
        //        HtmlPage html = (HtmlPage) page;
        //        return (ScriptableObject) html.getScriptObject();

        //parentScope version
        //ScriptableObject object = (ScriptableObject) html.getScriptObject();
        //return (ScriptableObject)object.getParentScope();

    }

}
