package org.seasar.teeda.extension.util;

import org.seasar.teeda.extension.component.html.THtmlTree;

public interface TreeNavigationImageLocator {

    void setImageRoot(String imageRoot);

    void setUpImageLocation(THtmlTree tree);

    boolean shouldRenderLineBackground();

    String getLineBackgroundSrc(boolean shouldShowLineBackground);
    
    String getAltSrc();

    String getNavSrc();

}