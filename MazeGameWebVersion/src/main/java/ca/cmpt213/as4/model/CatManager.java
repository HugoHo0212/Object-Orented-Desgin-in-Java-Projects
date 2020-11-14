package ca.cmpt213.as4.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represent a cat manager to hold a list of cat
 */
public class CatManager implements Iterable<Cat>{
    private List<Cat> cats = new ArrayList<>();

    public void add(Cat cat) {
        cats.add(cat);
    }

    @Override
    public Iterator<Cat> iterator() {
        return cats.iterator();
    }
}
