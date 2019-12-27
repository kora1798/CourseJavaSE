package my.oktmo;

import java.util.*;

public class OktmoData {
    private ArrayList<Place> places;
    private ArrayList<Place> sortedPlaces;
    private HashSet<String> allStatusesHS;
    private TreeSet<String> allStatusesTS;

    OktmoData() {
        places = new ArrayList<>();
        allStatusesHS = new HashSet<>();
        allStatusesTS = new TreeSet<>();
    }

    public void addPlace(Place p){
        places.add(p);
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public Set<String> getAllStatusesHS(){ return allStatusesHS; }

    public Set<String> getAllStatusesTS(){ return allStatusesTS; }

    public void sortingPlaces(){
        sortedPlaces = new ArrayList<>(places);
        sortedPlaces.sort(new Comparator<Place> (){
            public int compare(Place left, Place right){
                return left.getName().compareTo(right.getName());
            }
        });
    }

    public ArrayList<Place> getSortedPlaces(){
        return sortedPlaces;
    }
}
