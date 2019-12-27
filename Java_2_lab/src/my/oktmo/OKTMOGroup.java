package my.oktmo;

import java.util.ArrayList;
import java.util.List;

public class OKTMOGroup {
    OKTMOLevel level;
    String name;
    long code;
    List<OKTMOGroup> placesInside;

    OKTMOGroup(OKTMOLevel level, String name,long code){
        this.level = level;
        this.name = name;
        this.code = code;
        placesInside = new ArrayList<>();
    }
    public void addToInsidePlaces(OKTMOGroup group){
        this.placesInside.add(group);
    }
    public String getNameOfGroup(){
        return this.name;
    }
    public Long getCodeOfGroup(){
        return this.code;
    }
    public List<OKTMOGroup> getPlacesInsideGroup(){
        return this.placesInside;
    }
    public boolean contains(Long codepar){
        return placesInside.stream().anyMatch(s->s.code == codepar);
    }
}
