package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;
import my.items.exceptions.NoItemInContainer;

import java.util.ArrayList;

public interface InterfaceContainer {
    void add(Item a) throws ItemStoreException, ItemAlreadyPlacedException, ContainerAlreadyInContainer;
    Item find(String namearg) throws NoItemInContainer;
    Item get() throws ContainerAlreadyInContainer;
    ArrayList<String> itemsInside(); ///вывод предметов на экран
    void delete(String namearg)  throws ContainerAlreadyInContainer, NoItemInContainer;
    double getGeneralWeight();
    String toString();
}
