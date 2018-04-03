/**
 * DebuggingExample class
 * <p>
 * Quick example with a purposeful bug for tracing and print statements.
 */

public class DebuggingExample {
  
  public static void main(String[] args){ 
    int[] numbers = new int[]{3, 4, 5};
    iteratePrint(numbers);
  }
  //Print out each element of an array
  private static void iteratePrint(int[] arr){
    for(int i = 0; i <= arr.length; i++){
      System.out.println(arr[i]);
    } 
  }
}
