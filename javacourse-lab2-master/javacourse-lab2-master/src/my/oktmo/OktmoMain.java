
package my.oktmo;


public class OktmoMain {

    public static void main(String[] args) {
        OktmoData data = new OktmoData();
        OktmoReader reader = new OktmoReader();
        reader.readPlaces("data-201710.csv", data);
        for (Place p : data.getPlaces()){
                System.out.println(p.getName());
        }
    }
}
