package my.oktmo;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoReader {

    private int lineCount = 0;
    private long time;

    public void readPlaces(String fileName, OktmoData data) {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream("data-201710.csv"), "cp1251")
                        // читаем файл из двоичного потока
                        // в виде текста с нужной кодировкой
                )
        ){
            deconstructWithIndexOf(data, br);
        }catch (IOException e){

        }
    }


    private void setTimeOfReading(long time){
        this.time = time;
    }

    public long getTimeOfReading(){
        return this.time;
    }

    private void deconstructWithIndexOf(OktmoData data, BufferedReader br) throws IOException{
        long start = System.currentTimeMillis(); //замер времени
        ArrayList <String> p2 = new ArrayList<>();
        int pos, end, i;
        Long code;
        String str, name = "", status, s, tempname;
        while ((s = br.readLine()) != null) { // пока readLine() возвращает не null
            lineCount++;
            p2.clear();
            pos = 0;
            str = "";
            while ((end = s.indexOf(";", pos)) >= 0){
                p2.add((s.substring(pos, end)));
                pos = end + 1;
            }
            for (String line : p2.subList(0,4)) {
                str += line.replace("\"", "");
            }
            code = Long.parseLong(str);
            tempname = p2.get(6);
            status = p2.get(10);
            //ask question
            i = 0;
            for (Character c: tempname.toCharArray()){
                if (c.isUpperCase(c)) {
                    name = tempname.substring(i);
                    if (i != 0)
                        status = tempname.substring(0, i - 1);
                    break;
                }
                i++;
            }
            data.addPlace(new Place(code, name, status));
        }
        this.setTimeOfReading(System.currentTimeMillis() - start); //замер времени
    }

    private void deconstructWithSplit(OktmoData data, BufferedReader br) throws IOException{
        long start = System.currentTimeMillis();
        int i, pos = 0, end;
        Long code;
        String str, strbld, tempname, name = "", status, s;
        String[] p;
        Pattern pattern = Pattern.compile(";");
        while ((s = br.readLine()) != null) { // пока readLine() возвращает не null
            lineCount++;
            str = "";
            strbld = "";
            p = pattern.split(s);
            for (i = 0; i < 6; i++)
                str += p[i].replace("\"", "");
            code = Long.parseLong(str);
            tempname = p[6].replace("\"", "");
            status = p[10];
            i = 0;
            for (Character c: tempname.toCharArray()){
                if (c.isUpperCase(c)) {
                    name = tempname.substring(i);
                    if (i != 0)
                        status = tempname.substring(0, i - 1);
                    break;
                }
                i++;
            }
            data.addPlace(new Place(code, name, status));
        }
        this.setTimeOfReading(System.currentTimeMillis() - start);
    }
}
