/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesson8_gson;

/**
 *
 * @author E4300
 */
public class Point implements Comparable<Point>
{
        public final float x;
        public final float y;
        public Point(float a, float b)
        {
        x = a;
        y = b;
        }
        @Override
    public String toString() 
    {
        return "("+x+";"+y+")";
    }
    /**
     * 
     * @param p another point
     * @return true if current point is greater than second. (compares firstly x's, than y's)
     */
    public boolean moreThan(Point p) 
    {
        return x > p.x || x == p.x && y > p.y;
    }

    @Override
    public int compareTo(Point o)
    {
        if (x == o.x && y == o.y) return 0;
        if (x > o.x) return 1;
        if (x == o.x && y > o.y) return 1;
        return -1;
    }
    @Override
    public boolean equals (Object o) {
        if (!(o instanceof Point)) return false;
        Point p = (Point)o;
        return Math.round(this.x*100) == Math.round(p.x*100) && Math.round(this.y*100) == Math.round(p.y*100);
    }
}
