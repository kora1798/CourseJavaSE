package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void main() {
        //simple items
        Item brick = new Item("Brick", 1.2, "red");
        Item ball = new Item("Ball", 0.7, "round", "white");
        Item laptop = new Item ("Laptop", 1.9, "flat");
        Item book = new Item ("Book", 0.9, "flat");
        Item cat = new Item("Cat", 2.75, "alive");
        //container items
        Item paperbag = new Bag("Paper_Bag", 0.3);
        Item plasticbag = new Bag("Plastic_Bag" , 0.12);
        Item paperbox = new Box("Paper_box", 0.2);
        Item metallbox = new Box("Metall_box", 0.2);
        Item stack1 = new Stack("Stack_1", 0.15);
        Item stack2 = new Stack("Stack_2", 0.15);

        try{
            ((Box) metallbox).add(book);
            ((Bag) paperbag).add(ball);
            ((Bag) plasticbag).add(book);
        }
        catch (ItemStoreException e){
            System.out.println("You can't add more, it's already full!");
            e.printStackTrace(System.out);
        }
        catch (ItemAlreadyPlacedException e){
            System.out.println("This item is already placed in container!");
            e.printStackTrace(System.out);
        }
        catch (ContainerAlreadyInContainer e){
            System.out.println("This container is already in container, so it's impossible to add, delete or get item inside it");
            e.printStackTrace(System.out);
        }
        assertEquals(((Bag) paperbag).getGeneralWeight(), 1, 0.1);
        assertEquals(((Box)metallbox).getGeneralWeight(), 1.1, 0.1);
        assertTrue(book.incontainer);
    }
}