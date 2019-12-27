package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;

public class Stack extends ItemContainer {
    private int limitcount = 6;

    Stack(String name, double weight, String... props) {
        super(name, weight, props);
    }

    public void add(Item item) throws ItemStoreException, ItemAlreadyPlacedException, ContainerAlreadyInContainer {
        if (!item.properties.contains("flat") || !limit())
            throw new ItemStoreException();
        super.add(item);
    }

    public Item get() throws ContainerAlreadyInContainer{
        if (!this.getIncontainerFlag())
            throw new ContainerAlreadyInContainer();
        return this.items.get(0);
    }

    private boolean limit() {
        return (this.items.size() < limitcount);
    }

    public String toString(){
        return ("This is object of my.items.Stack class with\n name: " + this.name + "\nweight: " + this.weight + "\nproperties: " + properties + "\nItems inside: " + itemsInside());
    }
}
