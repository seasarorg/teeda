package javax.faces.component;

/**
 * @author manhole
 */
public class UINamingContainerTest extends UIComponentBaseTest {

    private UINamingContainer createUINamingContainer() {
        return (UINamingContainer) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UINamingContainer();
    }

}
