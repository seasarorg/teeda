package examples.teeda.web.common;

public class PageUtil {

    public static final int EDIT_NOT_CHANGE = 0;

    public static final int EDIT_ADD        = 1;

    public static final int EDIT_UPDATE     = 2;

    public static final int EDIT_DELETE     = 3;

    public static String getStyleClass(int status) {
        String styleClass = null;

        switch (status) {
        case EDIT_ADD:
            styleClass = "editStatusAdd";
            break;
        case EDIT_UPDATE:
            styleClass = "editStatusUpdate";
            break;
        case EDIT_DELETE:
            styleClass = "editStatusDelete";
            break;
        default:
            break;
        }

        return styleClass;
    }

    public static String getStyle(int status) {
        String style = null;

        switch (status) {
        case EDIT_ADD:
            style = "";
            break;
        case EDIT_UPDATE:
            style = "";
            break;
        case EDIT_DELETE:
            style = "display : none;";
            break;
        default:
            break;
        }

        return style;
    }

}
