/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesson8_gson;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author E4300
 */
public class Lesson8_Gson
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {        
        List<String> lines;
        try
        {
            lines = Files.readAllLines(
                    Paths.get("D://downloads/JsonDZ/poligons.json")
                    , Charset.defaultCharset());
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            return;
        }
        String line = "";
        for (String s : lines) 
        {
            line += s;
        }
        Gson gson = new Gson();
        Polygon[] pols;
        pols = gson.fromJson(line, Polygon[].class);
        System.out.println(String.format("polygons from file are %s",Arrays.toString(pols)));//checkout polygons
        ArrayList<Polygon> intersections = new ArrayList<>();
        for (int i = 0; i < pols.length; i++) //find intersections
        {
            for (int j = i; j < pols.length; j++) 
            {
                if (i == j) continue;
                Polygon pol = pols[i].getIntersection(pols[j]);
                if (pol.getVerticles().length != 0)
                intersections.add(pol);
            }
        }
        System.out.println(String.format("This is intersections %s",intersections));
        float area = 0; //calculate area
        for (Polygon pol :intersections)
        {
            area += pol.getArea();
            
        }
        System.out.println(String.format("Total area is %f",area));
    }
}
