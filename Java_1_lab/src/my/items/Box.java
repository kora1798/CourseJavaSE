package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;

public class Box extends ItemContainer {
    private int limitcount = 3;
    Item it;

    Box(String name, double weight, String... props) {
        super(name, weight, props);
    }

    public void add(Item item) throws ItemStoreException, ItemAlreadyPlacedException, ContainerAlreadyInContainer {
        if (!this.limit())
            throw new ItemStoreException();
        super.add(item);
    }

    public Item get() throws ContainerAlreadyInContainer {
        if (this.getIncontainerFlag())
            throw new ContainerAlreadyInContainer();
        it = this.items.get(items.size() - 1);
        this.weight -= it.weight;
        return it;
    }

    private boolean limit() {
        return (this.items.size() <= limitcount);
    }

    public String toString(){
        return ("This is object of my.items.Box class with\n name: " + this.name + "\nweight: " + this.weight + "\nproperties: " + properties + "\nItems inside: " + itemsInside());
    }
}
