
package my.oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class OktmoMain {

 public static void main(String[] args) {
     OktmoData data = new OktmoData();
     OktmoReader reader = new OktmoReader();
     OktmoAnalyzer analyzer = new OktmoAnalyzer(data);
     reader.readPlaces("data-201710.csv", data);
//     System.out.println(analyzer.findMostPopularPlaceName("Населенные пункты, входящие в состав муниципальных образований Алтайского края"));
     //analyzer.findMostPopularPlaceName("Населенные пункты, входящие в состав муниципальных образований Алтайского края");
     analyzer.printStatusTableForRegion("Населенные пункты, входящие в состав муниципальных образований Алтайского края");
 }
}
