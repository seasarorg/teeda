package javax.faces.component;

/**
 * @author manhole
 */
public class UINamingContainerTeedaTest extends UIComponentBaseTeedaTest {

    private UINamingContainer createUINamingContainer() {
        return (UINamingContainer) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UINamingContainer();
    }

}
