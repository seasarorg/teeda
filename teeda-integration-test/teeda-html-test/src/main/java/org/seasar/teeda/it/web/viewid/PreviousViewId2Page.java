package org.seasar.teeda.it.web.viewid;

public class PreviousViewId2Page {

    private String previousViewId;
    private boolean postback;

    public boolean isPostback() {
        return postback;
    }

    public void setPostback(boolean postback) {
        this.postback = postback;
    }

    public String getPreviousViewId() {
        return previousViewId;
    }

    public void setPreviousViewId(String previousViewId) {
        this.previousViewId = previousViewId;
    }

}
