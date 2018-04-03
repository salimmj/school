/*
*   3134 Data Structures in Java
*   Homework 2 Solutions
*/ 

public class Program2
{
	public static void main(String[] args)
	{
		
		TwoStackQueue<Integer> test = new TwoStackQueue<>();

		test.enqueue(1); // inbox = [1]				outbox = []
		test.enqueue(2); // inbox = [2, 1]			outbox = []
		test.enqueue(3); // inbox = [3, 2, 1]		outbox = []
		test.enqueue(4); // inbox = [4, 3, 2, 1]	outbox = []

		System.out.println(test.dequeue()); // inbox = []	outbox = [2, 3, 4]
		System.out.println(test.dequeue()); // inbox = []	outbox = [3, 4]

		test.enqueue(5); // inbox = [5]		outbox = [3, 4]
		test.enqueue(6); // inbox = [6, 5]	outbox = [3, 4]

		System.out.println(test.dequeue()); // inbox = [6, 5]	outbox = [4]
		System.out.println(test.dequeue()); // inbox = [6, 5]	outbox = []
		System.out.println(test.dequeue()); // inbox = []		outbox = [6]
		System.out.println(test.dequeue()); // inbox = []		outbox = []
		System.out.println(test.dequeue()); // null
		System.out.println(test.dequeue()); // null

	}
}