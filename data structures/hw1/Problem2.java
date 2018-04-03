//***********************
// Problem2.java
// Written by Salim M'jahad on 1/25/17
// msm2243
// Recursive binary search on any array of comparable objects
//***********************
import java.util.*;
import java.io.*;

public class Problem2 {

    //this method calls another one inside to make the binary search
    public static <AnyType extends Comparable<AnyType>>
    int binarySearch(AnyType[] a, AnyType x){

        return bSearch(0, a.length -1, a, x);

    }

    //recursuive binary search
    private static <AnyType extends Comparable<AnyType>>
    int bSearch(int s, int f, AnyType[] a, AnyType x) {

        //checks if the start and finish variables still haven't crossed
        if (s>f)
            return -1;

        //compares the element we're looking for to the middle
        //then calls itself accordingly or returns that index
        if (a[(s+f)/2].compareTo(x) == 0) {
            return (s+f)/2;
        } else if (a[(s+f)/2].compareTo(x) > 0 ) {
            return bSearch(s, (s+f)/2 - 1, a, x);
        } else {
            return bSearch((s+f)/2 +1, f, a, x);
        }


    }


    //this main method tests the binary search on a dictionary.txt file

    public static void main(String[] args) {

        File file = new File("dictionary.txt");

        ArrayList<String> arr = new ArrayList<>();

        try {

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String i = sc.next();
                arr.add(i);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Collections.sort(arr);

        String[] ar = new String[arr.size()];
        ar = arr.toArray(ar);

        //change "astronomy" with the String you're looking for
        int index = binarySearch(ar, "astronomy");

        System.out.println(index);
        System.out.println(ar[index]);

    }

}

