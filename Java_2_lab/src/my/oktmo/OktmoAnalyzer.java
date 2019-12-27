package my.oktmo;


import java.lang.management.PlatformLoggingMXBean;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OktmoAnalyzer {
    private Pattern pattern;
    private Matcher matcher;
    private OktmoData data;
    private List<OKTMOGroup> list;

    OktmoAnalyzer(OktmoData data){
        this.data = data;
    }

    public Stream findAllPlacesInGroup(OKTMOGroup group){
        Long code = group.code;
        Map<Long,Place> places = data.getMapPlacesWithCode();
        switch (group.level){
            case REGION:
                return (((TreeMap<Long,Place>)places).subMap(code, code + 1000000000L).values()).stream().filter(p->p.getCode()%1000!=0);
            case RAYON:
                return (((TreeMap<Long,Place>)places).subMap(code, code + 1000000L).values()).stream().filter(p->p.getCode()%1000!=0);
            case VILLAGE:
                return (((TreeMap<Long,Place>)places).subMap(code, code + 1000L).values()).stream().filter(p->p.getCode()%1000!=0);
            default:
                return null;
        }
    }

    public String findMostPopularPlaceName(String regionName){
        OKTMOGroup region = data.getRegionGroupByName(regionName);
        Supplier<Stream<Place>> streamSupplier = ()->findAllPlacesInGroup(region);
        //streamSupplier.get().forEach(System.out::println);
        List<String> t = streamSupplier.get().distinct().map(Place::getName).collect(Collectors.toList());
        Map<String,Integer> tempmap = new HashMap<>();
        for (String str : t){
            tempmap.put(str, 0);
        }
        streamSupplier.get().forEach(p->tempmap.replace(p.getName(), tempmap.get(p.getName())+ 1));
        return tempmap.entrySet().stream().max((a,b)->a.getValue()>b.getValue()?1:-1).get().getKey();
    }

    public void printStatusTableForRegion(String regionName){
        OKTMOGroup region = data.getRegionGroupByName(regionName);
        Stream<Place> templist = findAllPlacesInGroup(region);
        Map<String,List<Place>> tempmap = templist.collect(Collectors.groupingBy(Place::getStatus));
        Map<String, Integer> tempmap2 = new TreeMap<>();
        for (Map.Entry<String,List<Place>> entry : tempmap.entrySet()){
            tempmap2.put(entry.getKey(), entry.getValue().size());
            System.out.println(entry.getKey() + "   " + entry.getValue().size());
        }
    }

    public void searchOne(){
        String regex = ".{2,3}ово$";
        pattern = Pattern.compile(regex);
        for (Place obj : data.getPlaces()) {
            matcher = pattern.matcher(obj.getName());
            if (matcher.lookingAt()) {
                System.out.println(obj.toString());
            }
        }
    }

    public void searchSecond(){
        String regex = "^([^аеёиоуыэюя]).*(\\1)$";//[А,Е,Ё,И,О,У,Ы,Э,Ю,Я]
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        for (Place obj : data.getPlaces()) {
            matcher = pattern.matcher(obj.getName());
            if (matcher.lookingAt()) {
                System.out.println(obj.toString());
            }
        }
    }
}
