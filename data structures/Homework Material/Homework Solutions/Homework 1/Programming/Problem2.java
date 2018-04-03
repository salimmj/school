/*
// 3134 HW1 Problem2
//
*/

import java.util.Arrays;

public class Problem2 {

  //Public binarySearch function that calls the recursive private binarySearch function
  public static <AnyType extends Comparable<AnyType>> int binarySearch(AnyType[] a, AnyType x) {
    return binarySearch(a, x, 0, a.length - 1);
  }

  private static <AnyType extends Comparable<AnyType>> int binarySearch(AnyType[] a, AnyType x, int low, int high) {

    //Object not found in array
    if (low > high) {
      return -1;
    }
    int mid = (low + high) / 2;

    if (a[mid].compareTo(x)==0) {
      //Object is in the mid index
      return mid;
    } 
    else if (a[mid].compareTo(x) < 0) {
      //Object is on the rigth side of mid
      return binarySearch(a, x, mid + 1, high);
    } 
    else {
      //Object is in the left side of mid
      return binarySearch(a, x, low, mid - 1);
    }
  }

  //Main method
  public static void main(String[] args) {
    int count = 100;

    Rectangle[] rectangles = new Rectangle[count];

    //Create multiple Rectangle objects
    for (int i = 0; i < count; i++) {
      rectangles[i] = new Rectangle(i, i + 100);
    }

    //Sort the array
    Arrays.sort(rectangles);

    //Should print 0
    System.out.println(binarySearch(rectangles, new Rectangle(0,100)));

    //Should print -1
    System.out.println(binarySearch(rectangles, new Rectangle(1, 100)));

  }
}
