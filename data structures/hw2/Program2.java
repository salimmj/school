//***********************
// Program2.java
// Written by Salim M'jahad on 2/16/17
// msm2243
// This class tests the class TwoStackQueue
//***********************

public class Program2 {

    /** If implemented correctly, this code should output:
     *  0
     *  Hey You Be 2 Kind To Me
     */

    public static final void main(String[] args) {


        TwoStackQueue<String> q = new TwoStackQueue<String>();
        System.out.println(q.size());

        q.enqueue("Hey ");
        q.enqueue("You ");
        q.enqueue("Be ");
        System.out.print(q.dequeue());
        System.out.print(q.dequeue());
        System.out.print(q.dequeue());
        q.enqueue(" Kind ");
        q.enqueue("To ");
        System.out.print(q.size());
        System.out.print(q.dequeue());
        q.enqueue("Me");

        while(!q.isEmpty())
            System.out.print(q.dequeue());


    }
}
