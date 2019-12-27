package my.oktmo;

import java.util.*;

public class OktmoData {
    private List<Place> places;
    private Map<String,OKTMOGroup> regionGroupByName;
    private Map<Long,OKTMOGroup> regionGroupByCode;
    private Map<Long,OKTMOGroup> rayonGroupByCode;
    private Map<Long,OKTMOGroup> villageGroupByCode;
    private Map<Long, Place> placesGroupByCode;

    OktmoData() {
        places = new ArrayList<>();
        regionGroupByName = new TreeMap<>();
        regionGroupByCode = new TreeMap<>();
        rayonGroupByCode = new TreeMap<>();
        villageGroupByCode = new TreeMap<>();
        placesGroupByCode = new TreeMap<>();
    }

    public void addPlace(Place p){
        placesGroupByCode.put(p.getCode(),p);
        places.add(p);
    }

    public void addGroup(OKTMOGroup group){
        getMapGroupWithCode(group.level).put(group.code, group);
        if (group.level == OKTMOLevel.REGION)
            regionGroupByName.put(group.name, group);
    }

    public List<Place> getPlaces(){
        return places;
    }

    public Map<Long,OKTMOGroup> getMapGroupWithCode(OKTMOLevel levelname){
        switch (levelname) {
            case REGION:
                return regionGroupByCode;
            case RAYON:
                return rayonGroupByCode;
            case VILLAGE:
                return villageGroupByCode;
            default:
                return null;
        }
    }

    public OKTMOGroup getRegionGroupByName(String name){
        return regionGroupByName.get(name);
    }

    public Map<Long,Place> getMapPlacesWithCode(){
        return placesGroupByCode;
    }
}
