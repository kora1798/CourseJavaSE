package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;

import java.util.*;

public class Bag extends ItemContainer {
    private double limitweight = 3.12;
    private Random random = new Random();

    Bag(String name, double weight, String... props) {
        super(name, weight, props);
    }

    public void add(Item item) throws ItemStoreException, ItemAlreadyPlacedException, ContainerAlreadyInContainer {
        if (!this.limit(item.weight))
            throw new ItemStoreException();
        super.add(item);
    }

    public Item get() throws ContainerAlreadyInContainer{
        if (!this.getIncontainerFlag())
            throw new ContainerAlreadyInContainer();
        return this.items.get(random.nextInt(items.size()));
    }

    private boolean limit(double weight) {
        return  ((this.getGeneralWeight() + weight) <= limitweight);
    }

    public String toString() {
        return ("This is object of my.items.Bag class with\n name: " + this.name + "\nweight: " + this.weight + "\nproperties: " + properties + "\nItems inside: " + itemsInside());
    }
}