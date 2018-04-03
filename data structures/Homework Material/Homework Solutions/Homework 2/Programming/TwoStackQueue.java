/*
* 3134 Data Structures 
* HW2 Programming 2 Solution
* TwoStackQueue.java
* Implements MyQueue using two Stacks
*
*  - Modified from the original version
*/

public class TwoStackQueue<T> implements MyQueue<T> {
  private MyStack<T> inbox;
  private MyStack<T> outbox;
  private int size;

  public TwoStackQueue() {
    inbox = new MyStack<T>();
    outbox = new MyStack<T>();
    size = 0;
  }

  // O(1)
  public void enqueue(T item) {
    inbox.push(item);
    size++;
  }

  // O(1) when outbox is not empty
  // O(n) when outbox is empty. n == inbox.size
  public T dequeue() {
    if (outbox.isEmpty()) {
      while (!inbox.isEmpty()) {
        outbox.push(inbox.pop());
      }
    }

    size--;
    return outbox.pop();
  }

  public boolean isEmpty() {
	  return size == 0;
  }

  public int size() {
	  return size;
  }
}
