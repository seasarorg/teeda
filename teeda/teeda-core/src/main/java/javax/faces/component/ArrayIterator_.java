package javax.faces.component;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayIterator_ implements Iterator {

    private Object[] items_;
    private int index_ = 0;

    public ArrayIterator_(Object items[]) {
        items_ = items;
    }

    public boolean hasNext() {
        return index_ < items_.length;
    }

    public Object next() {
        try {
            return (items_[index_++]);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
