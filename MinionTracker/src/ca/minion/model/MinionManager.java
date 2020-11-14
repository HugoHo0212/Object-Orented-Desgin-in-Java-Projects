package ca.minion.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represent a minion manager to hold a list of minions
 */
public class MinionManager implements Iterable<Minion>{
    private List<Minion> minions = new ArrayList<>();

    public void add(Minion minion) {
        minions.add(minion);
    }

    public void remove(int index) {
        minions.remove(index);
    }

    public int getMinionsLen() {
        return minions.size();
    }

    public Minion getMinionAtIndex(int index) {
        return minions.get(index);
    }

    public void incEvilDeedAtIndex(int index) {
        getMinionAtIndex(index).incEvilDeed();
    }

    @Override
    public Iterator<Minion> iterator() {
        return minions.iterator();
    }
}
