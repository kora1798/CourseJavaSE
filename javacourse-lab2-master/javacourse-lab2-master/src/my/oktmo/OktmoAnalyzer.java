package my.oktmo;

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

    OktmoAnalyzer(OktmoData data){
        this.data = data;
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
