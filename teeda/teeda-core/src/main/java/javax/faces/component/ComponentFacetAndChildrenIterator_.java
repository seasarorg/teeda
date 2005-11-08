package javax.faces.component;

import java.util.Iterator;
import java.util.*;

public class ComponentFacetAndChildrenIterator_ implements Iterator {

	private Iterator facetItr_ = null;
	private Iterator childrenItr_ = null;
	private static final Iterator EMPTY_ITERATOR = new Iterator(){

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasNext() {
			return false;
		}

		public Object next() {
			return null;
		}		
	};
	public ComponentFacetAndChildrenIterator_(Map facetMap, List childrenList){
		facetItr_ = facetMap.entrySet().iterator();
		childrenItr_ = childrenList.iterator();
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return (facetHasNext() || childrenHasNext());
	}

	public Object next() {
		
		if(facetHasNext()){
			return facetItr_.next();
		}
		
		if(childrenHasNext()){
			return childrenItr_.next();
		}
		
		return 	EMPTY_ITERATOR;
	}

	private boolean facetHasNext(){
		return (facetItr_ != null && facetItr_.hasNext());
	}
	
	private boolean childrenHasNext(){
		return (childrenItr_ != null && childrenItr_.hasNext());
	}
}
