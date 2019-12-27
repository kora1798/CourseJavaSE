package my.items;

import my.items.exceptions.ContainerAlreadyInContainer;
import my.items.exceptions.ItemAlreadyPlacedException;
import my.items.exceptions.ItemStoreException;
import my.items.exceptions.NoItemInContainer;

import java.lang.*;

public class Main {
    public static void main(String[] args){
        //simple items
        Item brick = new Item("Brick", 1.2, "red");
        Item ball = new Item("Ball", 0.7, "round", "white");
        Item laptop = new Item ("Laptop", 1.9, "flat");
        Item book = new Item ("Book", 0.9, "flat");
        Item cat = new Item("Cat", 2.75, "alive");
        //container items
        var paperbag = new Bag("Paper_Bag", 0.3);
        var plasticbag = new Bag("Plastic_Bag" , 0.12);
        var paperbox = new Box("Paper_box", 0.2);
        var metallbox = new Box("Metall_box", 0.2);
        Stack stack1 = new Stack("Stack_1", 0.15);
        Stack stack2 = new Stack("Stack_2", 0.15);

        try{
            metallbox.find("Bird");
            metallbox.add(book);
            paperbag.add(ball);
            paperbox.add(metallbox);
            metallbox.add(brick);
            metallbox.find("book");
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
        catch (NoItemInContainer e){
            System.out.println("There is no such item in container!");
            e.printStackTrace(System.out);
        }
        System.out.println((paperbag).getGeneralWeight());
        System.out.println((paperbox).getGeneralWeight());
    }
}
