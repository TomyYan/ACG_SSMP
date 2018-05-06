package cn.tomy.tool;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tomy on 18-4-19.
 */
public class ReadFile {
    public static Set<String> readFile(String fileName){
        Set<String> resuleSet=new HashSet<>();
        File file = new File(fileName);
        try {
            FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String aLine;
            while ((aLine=bufferedReader.readLine())!=null){
                resuleSet.add(aLine);
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resuleSet;
    }
}
