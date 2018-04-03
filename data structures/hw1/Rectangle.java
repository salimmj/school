//***********************
// Rectangle.java
// Written by Salim M'jahad on 1/25/17
// msm2243
// Class representing a comparable rectangle
//***********************

public class Rectangle implements Comparable<Rectangle>{

    double length, width;

    public Rectangle(double L, double W){
        length = L;
        width = W;
    }


    //overrides the compareTo method and fulfills the interface
    //requirement
    @Override
    public int compareTo(Rectangle o) {
        double c1 = length + width, c2 = o.length + o.width;

        if (c1>c2) {
            return 1;
        } else if (c1<c2) {
            return -1;
        } else {
            return 0;
        }
    }

    //method to print info about the rectangle
    public void print() {
        System.out.println("L: "+length+" W: "+width);
    }
}
