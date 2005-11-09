package javax.faces.component;

import junit.framework.TestCase;
import java.util.*;

public class TestComponentFacetAndChildrenIterator_ extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner
                .run(TestComponentFacetAndChildrenIterator_.class);
    }

    public void testHasNext() {
        Map map = new HashMap();
        map.put("a","1");
        ComponentFacetAndChildrenIterator_ itr = 
            new ComponentFacetAndChildrenIterator_(map, null);
        assertNotNull(itr.next());
        try{
            itr.next();
        }catch(NoSuchElementException e){
            assertTrue(true);
        }
        
        List list = new ArrayList();
        list.add("c");
        itr = new ComponentFacetAndChildrenIterator_(null, list);
        assertNotNull(itr.next());
        
        itr = new ComponentFacetAndChildrenIterator_(map, list);
        assertNotNull(itr.next());
        assertNotNull(itr.next());
        
    }

}
