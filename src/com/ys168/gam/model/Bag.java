package com.ys168.gam.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @author Kevin
 * @since 2017年5月22日
 */
public class Bag {

    private int capacity;
    private Set<Item> sheets;

    public Bag() {
        this.capacity = 20;
        this.sheets = new LinkedHashSet<>();
    }

    public boolean add(Item item) {
        if (isFull()) {
            return false;
        }
        return sheets.add(item);
    }

    public void clear() {
        sheets.clear();
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean has(Item item) {
        return sheets.contains(item);
    }

    public boolean isFull() {
        return sheets.size() == capacity;
    }

    public boolean remove(Item item) {
        return sheets.remove(item);
    }

}
