/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesson8_gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author E4300
 */
public class Polygon
{
    public Polygon (Point[] arr) {
        verticles = arr;
    }
    private final Point[] verticles;
    /**
     * verticles
     * @return polygon's verticles
     */
    public Point[] getVerticles() {
     return verticles;   
    }
    @Override
    public String toString() 
    {
        String val = "Polygon ";
       for (Point p : verticles) {
           val += p.toString()+" ";
       }
        return val;
    }
    /**
     * return point from verticles with specified index
     * @param index index of point. It can be more than verticles's length or less than 0
     * @return point that is find by index
     */
    public Point getPoint(int index){
        if (index >= verticles.length) return getPoint(index - verticles.length);
        if (index < 0) return getPoint(index + verticles.length);
        return verticles[index];
    }
    /**
     * Checks if Point p is inside polygon
     * @param p point that is checked
     * @return true if point is inside
     */
    private boolean isContainsPoint(Point p)
    {
        int counter = 0;
        for (int i = 0; i < verticles.length; i++) 
        {
            if (getPoint(i).x == getPoint(i+1).x) {
                if(p.x <= getPoint(i).x &&((p.y >= verticles[i].y && p.y <= getPoint(i+1).y) ||
                        (p.y <= verticles[i].y && p.y >= getPoint(i+1).y))){
                    if (p.x == getPoint(i).x) return true;
                    counter++;
                }
                continue;
            }
            float k = (getPoint(i+1).y - verticles[i].y)/(getPoint(i+1).x - verticles[i].x);
            float b = getPoint(i+1).y- k*getPoint(i+1).x;
            if (k == 0 && p.y == verticles[i].y &&
                    ((p.x >= verticles[i].x && p.x <= getPoint(i+1).x) ||
                    (p.x <= verticles[i].x && p.x >= getPoint(i+1).x)))
            {
                return true;
            }
            float x = (p.y - b)/k;
            if (k != 0 && p.x <= (p.y -b)/k &&
                    ((x >= verticles[i].x && x <= getPoint(i+1).x) ||
                    (x <= verticles[i].x && x >= getPoint(i+1).x))){ 
                if (p.y == p.x*k + b) return true;
                counter++;
            }
        }
        
        return counter % 2 == 1;
    }
    private static Point getIntersectionPoint(Point p0, Point p1, Point p2, Point p3)
    {
        float k1 = (p1.y-p0.y)/(p1.x-p0.x);
        float b1 = p0.y - k1*p0.x;
        float k2 = (p3.y-p2.y)/(p3.x-p2.x);
        float b2 = p2.y - k2*p2.x;
        if (p0.x== p1.x){ //  ---> k =  Infinity
            float y = k2*p0.x +b2;
            if (((y >= p0.y && y <= p1.y)
                    || (y <= p0.y && y >= p1.y)) &&
                    ((y >= p2.y && y <= p3.y)
                    || (y <= p2.y && y >= p3.y)))
                return new Point(p0.x,y); // TODOTODOTODO SECOND
        }
        if (k1 != k2){
            float x = (b1-b2)/(k2-k1);
            if (((x >= p0.x && x <= p1.x)
                    || (x <= p0.x && x >= p1.x)) &&
                    ((x >= p2.x && x <= p3.x)
                    || (x <= p2.x && x >= p3.x)))
                return new Point(x, k1*x+b1);
        }
        return null;
    }
    private static ArrayList<Point> FindIntersections(Point p1, Point p2, Polygon poly)
    {
        ArrayList<Point> points = new ArrayList<>();
        for(int i = 0; i < poly.getVerticles().length; i++)
        {
            Point p = getIntersectionPoint(p1, p2, poly.getPoint(i), poly.getPoint(i+1));
            if (p != null)
            {
                points.add(p);
            }
        }
        Collections.sort(points);
        if (p1.x > p2.x) Collections.reverse(points);
        return points;
    }
    /**
     * Calculates intersection of two polygons
     * @param poly another polygon
     * @return new Polygon that is intersection of specified polygons
     */
    public Polygon getIntersection (Polygon poly)
    {
        if ((Area() < 0) != (poly.Area() < 0)) //if polygons have different directions we should reveerse one of them
        {
            List<Point> polyVerticles = Arrays.asList(poly.verticles);
            Collections.reverse(polyVerticles);
            poly = new Polygon(polyVerticles.toArray(new Point[0]));
        }
        ArrayList<Point> pointsA = new ArrayList<>();
        ArrayList<Point> pointsB = new ArrayList<>();
        ArrayList<Point> intersections = new ArrayList<>();
        for (int i = 0; i < verticles.length; i++) //find all points for polygon A with intersections
        {
            pointsA.add(getPoint(i));
            ArrayList<Point> ps = FindIntersections(getPoint(i), getPoint(i+1), poly);
            for (Point p : ps)
            {
                pointsA.add(p);
                intersections.add(p);
            }
        }
        for (int i = 0; i < poly.verticles.length; i++) //find all points for polygon A with intersections
        {
            pointsB.add(poly.getPoint(i));
            ArrayList<Point> ps = FindIntersections(poly.getPoint(i), poly.getPoint(i+1), this);
            for (Point p : ps){ pointsB.add(p);}
        }
        int indexA = 0;
        int indexB = 0;
        ArrayList<Point> points = new ArrayList<>(); //the value for the return
        if (intersections.size() > 0) {
        while (true) //we need to find where cycle B will start (if a starts from 0)
            {
                indexB++;
                if (intersections.get(intersections.size() - 1).equals(pointsB.get(indexB)))
                {
                indexB++;
                    if (indexB >= pointsB.size()) 
                    {
                    indexB -= pointsB.size();
                    }
                break;
                }
            }
        }
        for (Point p : intersections)
        {
            while (!p.equals(pointsA.get(indexA))) //We add points between intersection points from A that is inside B
            {
            if (poly.isContainsPoint(pointsA.get(indexA))) points.add(pointsA.get(indexA));
            indexA++;
            }
            indexA++;
            
            while (!p.equals(pointsB.get(indexB))) //We add points between intersection points from B that is inside A
            {
            if (this.isContainsPoint(pointsB.get(indexB))) points.add(pointsB.get(indexB));
            indexB++;
            if (indexB >= pointsB.size()) 
            {
                indexB -= pointsB.size();
            }
            }
            indexB++;
            points.add(p); //And we add the intersection
        }
        while (indexA < pointsA.size()) //we need to add the last points from A
            {
            if (poly.isContainsPoint(pointsA.get(indexA))) points.add(pointsA.get(indexA));
            indexA++;
            }
        return new Polygon(points.toArray(new Point[0]));
    }    
    /**
     * 
     * @return area of the polygon
     */
    public float getArea() {
        return Math.abs(Area());
    }
    private float Area() 
    {
        float area = 0;
        int left, right;
        left = right = 0;
        for (int i = 0; i < verticles.length; i++)
        {
            if (verticles[i].x < verticles[left].x) left = i; //we search right and left edge
            if (verticles[i].x > verticles[right].x) right = i;
        }
        int index = left;
        while (true) //we start from left and move to right, calculating trapezza's area
        {
            if (index == right) break; 
            area += Math.abs((getPoint(index+1).x - getPoint(index).x)) * (getPoint(index+1).y + getPoint(index).y)/2;
            if (index < verticles.length - 1) index++;
            else index = 0;
        }
        while (true) //we start from right and move to left, substracting trapezza's area
        {
            if (index == left) break; 
            area -= Math.abs((getPoint(index+1).x - getPoint(index).x)) * (getPoint(index+1).y + getPoint(index).y)/2;
            if (index < verticles.length -1 ) index++;
            else index = 0;
        }
        return area;
    }
}
