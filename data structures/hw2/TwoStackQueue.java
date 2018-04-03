//***********************
// TwoStackQueue.java
// Written by Salim M'jahad on 2/16/17
// msm2243
// This class defines a queue using two stacks
//***********************


public class TwoStackQueue<AnyType> implements MyQueue<AnyType> {

    /**
     * The way I did this is kind of weird. I even talked to Prof. Blaer about
     * it. So my flip(from, to) method goes both ways. Instead of only flipping
     * the input stack into the output stack only when the output stack is empty,
     * what I do is whenever I enqueue I flip in a direction and when I dequeue
     * I flip in the other direction.
     *
     * I can easily change it so that it only flips in one direction but I
     * I will have to change my discussion of the Big-Oh cost in Program2.txt
     * Which... I am too lazy to do tbh.
     *
     * This approach works too, now you know I know both approaches.
     * How cool is that?
     *
     * Have fun!
     * **/

    private MyStack<AnyType> stack1;
    private MyStack<AnyType> stack2;

    //constructor makes 2 stacks to store data
    public TwoStackQueue() {
        stack1 = new MyStack<AnyType>();
        stack2 = new MyStack<AnyType>();
    }


    //
    @Override
    public void enqueue(AnyType x) {
        flip(stack2, stack1);
        stack1.push(x);
    }

    public void flip(MyStack<AnyType> from, MyStack<AnyType> to) {
        while (from.size() != 0)
            to.push(from.pop());
    }

    @Override
    public AnyType dequeue() {
        AnyType obj;

        flip(stack1, stack2);
        obj = stack2.pop();
        return obj;
    }

    @Override
    public boolean isEmpty() {
        boolean one = stack1.size() == 0;
        boolean two = stack2.size() == 0;

        return one && two;
    }

    @Override
    public int size() {
        int size;
        if (stack1.size() >= stack2.size()) {
            size = stack1.size();
        } else {
            size = stack2.size();
        }

        return size;
    }
}
