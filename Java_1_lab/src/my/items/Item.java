package my.items;

import java.lang.*;
import java.util.*;

public class Item {
    String name;
    ArrayList <String> properties = new ArrayList <String>();
    double weight;
    boolean incontainer;

    Item(){
        this.name = null;
        this.weight = 0;
        this.incontainer  = false;
    }

    Item(String name, double weight, String... args){
        this.name = name;
        for (String prop : args)
            this.properties.add(prop);
        this.weight = weight;
        this.incontainer  = false;
    }

    public boolean getIncontainerFlag(){
        return this.incontainer;
    }
    public void getInfo(){
        System.out.println(this.name);
        System.out.println(properties);
        System.out.println(weight);
    }

    public String toString(){
        return ("This is an item with\n name: " + this.name + "\nweight: " + this.weight + "\nproperties" + properties + "\n");
    }
}
