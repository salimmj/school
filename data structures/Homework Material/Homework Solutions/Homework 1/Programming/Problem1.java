/*
// 3134 HW1 Problem1
//
*/
public class Problem1 {

  //findMax()
  public static <AnyType extends Comparable<AnyType>> AnyType findMax(AnyType[] arr) {
    int maxIndex = 0;

    for (int i = 1; i < arr.length; i++)
      if (arr[i].compareTo(arr[maxIndex]) > 0){
        maxIndex = i;
      }

    return arr[maxIndex];
  }

  //Main method
  public static void main(String[] args) {
    int count = 100;

    Rectangle[] rectangles = new Rectangle[count];

    for (int i = 0; i < count; i++) {
      rectangles[i] = new Rectangle(i, i + 100);
    }

    //Compare the result of findMax with the biggest Rectangle in the array
    //Should print true
    System.out.println(findMax(rectangles).equals(rectangles[count - 1]));
  }
}
