//***********************
// Problem1.java
// Written by Salim M'jahad on 1/25/17
// msm2243
// Class that finds the largest rectangle depending on perimeter
//***********************
import java.util.Random;

public class Problem1 {
    //this method finds the rectangle with the biggest perimeter
    public static <AnyType extends Comparable<AnyType>>
    AnyType findMax(AnyType[] arr) {
        int maxIndex = 0;
        for (int i = 1; i < arr.length; i++)
            if ( arr[i].compareTo(arr[maxIndex]) > 0 )
                maxIndex = i;
        return arr[maxIndex];
    }

    public static void main(String[] args) {

        Random r = new Random();

        Rectangle[] arr = new Rectangle[50];

        //fills the array with random rectangles
        for (int i=0; i<50; i++)
            arr[i] = new Rectangle(100 * r.nextDouble(), 100 * r.nextDouble());

        //prints all rectangles in the array
        for (int i=0; i<50; i++) {
            arr[i].print();
        }

        //finds the max then prints it
        Rectangle largest = findMax(arr);

        System.out.println("LARGEST:");
        largest.print();
    }
}
