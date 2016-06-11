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
        
            List<String> lines = null;
        try
        {
            lines = Files.readAllLines(
                    Paths.get("D:\\downloads/JsonDZ/poligons.json")
                    , Charset.defaultCharset());
            lines.size();
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        //System.out.println(lines);
        String line = "";
        for (String s : lines) 
        {
            line += s;
        }
        //System.out.println(line);
        
        Gson gson = new Gson();
        Polygon[] pols;
        pols = gson.fromJson(line, Polygon[].class);
        System.out.println(Arrays.toString(pols));
        
        
//        Point[] p = new Point[3];
//        p[0]= new Point(-3,-1);
//        p[1] = new Point(0,2);
//        p[2] = new Point(3,-1);
//        Polygon pol1 = new Polygon(p);
//        Point[] p1 = new Point[3];
//        p1[0]= new Point(-3,1);
//        p1[1] = new Point(3,1);
//        p1[2] = new Point(0,-2);
//        Polygon pol2 = new Polygon(p1);
        System.out.println(pols[0].getIntersection(pols[2]));
        //System.out.println(pol1.getIntersection(pol2));
////        System.out.println(pol1.getArea());
////        System.out.println(pol2.getArea());
//        for (Polygon p : pols) 
//        {
//            System.out.println(p);
//        }

//        Polygon p = new Polygon();
//        p.verticles = new Point[]{new Point(1,1), new Point (2,2), new Point(0,0)};
//        Polygon[] pols = new Polygon[]{p,p};
        
        //System.out.println(gson.toJson(pols));
    }
    
}
