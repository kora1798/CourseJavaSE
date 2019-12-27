package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;
import my.items.exceptions.NoItemInContainer;

import java.util.ArrayList;

public abstract class ItemContainer extends Item implements InterfaceContainer {

    protected ArrayList<Item> items;

    ItemContainer() {
        super();
        items = new ArrayList();
    }

    ItemContainer(String name,  double weight, String... props) {
        super(name, weight, props);
        items = new ArrayList();
    }

    public void add(Item item) throws ItemStoreException, ItemAlreadyPlacedException, ContainerAlreadyInContainer {
        if (this.getIncontainerFlag())
            throw new ContainerAlreadyInContainer();
        if (item.getIncontainerFlag())
            throw new ItemAlreadyPlacedException();
        items.add(item);
        item.incontainer = true;
    }


    public Item find(String name) throws NoItemInContainer {
        Item item = null;
        for (Item obj : this.items) {
            if (obj.name.equals(name))
                item = obj;
        }
        if (item != null)
            return item;
        else
            throw new NoItemInContainer();
    }

    public abstract Item get() throws ContainerAlreadyInContainer;

    public ArrayList<String> itemsInside() {
        ArrayList<String> s = new ArrayList<>();
        for (Item obj : this.items) {
           s.add(obj.name);
        }
        return s;
    }

    public void delete(String name) throws ContainerAlreadyInContainer, NoItemInContainer{
        if (this.getIncontainerFlag())
            throw new ContainerAlreadyInContainer();
        items.remove(find(name));
    }

    public double getGeneralWeight() {
        double temp = 0;
        for (Item obj : this.items) {
            if (obj instanceof ItemContainer)
                temp += ((ItemContainer) obj).getGeneralWeight();
            else
                temp += obj.weight;
        }
        temp += this.weight;
        return temp;
    }
}
