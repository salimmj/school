/*
* 3134 Data Structures 
* HW2 Written 3 Solution
* Implements two Stacks using one Array
*/

public class TwoStacks<T> {
  private T[] array;
  private int leftSize; // The size of Stack1, also a pointer to Stack1
  private int rightSize; // The size of Stack2, also a pointer to Stack2

  public static final int INITIAL_LENGTH = 256;

  @SuppressWarnings("unchecked")
  public TwoStacks(int length) {

    //Create an array of Objects, then cast it to a Generic array
    //This line will give us an unchecked warning if warnings are not suppressed
    array = (T[]) new Object[length]; 

    leftSize = 0;
    rightSize = 0;
  }

  public TwoStacks() {
    this(INITIAL_LENGTH);
  }

  //Checks if there is an overflow. 
  //The method that calls this method will decide what to do if there is an overflow.
  private boolean hasSpace() {
    return (leftSize + rightSize) < array.length;
  }

  public void pushLeft(T item) throws Exception {
    if (hasSpace()) {
      array[leftSize] = item;
      leftSize++;
    } else {
      throw new Exception();
    }
  }

  public void pushRight(T item) throws Exception {
    if (hasSpace()) {
      array[array.length - rightSize - 1] = item;
      rightSize++;
    } else {
      throw new Exception();
    }
  }

  public T popLeft() throws Exception {
    if (leftSize - 1 < 0) {
      throw new Exception();
    } else {
      return array[--leftSize];
    }
  }

  public T popRight() throws Exception {
    if (rightSize - 1 < 0) {
      throw new Exception();
    } else {
      return array[array.length - (--rightSize) - 1];
    }
  }

  public static void main(String[] args) throws Exception {
    TwoStacks<Integer> stacks = new TwoStacks<>();
    for (int i = 0; i < 128; i++) {
      stacks.pushLeft(i);
      stacks.pushRight(i);
    }

    for (int i = 0; i < 128; i++) {
      assert stacks.popLeft().equals(stacks.popRight());
    }
  }
}
