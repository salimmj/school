//***********************
// MyStack.java
// Written by Salim M'jahad on 2/16/17
// msm2243
// Stack class I defined
//***********************

import java.util.ArrayList;


public class MyStack<T> {
    private ArrayList<T> list;

    //constructor
    public MyStack() {
        list = new ArrayList<>();
    }

    //pushs an item
    public void push(T item) {
        list.add(item);
    }

    //pops an item
    public T pop() {
        int size = list.size();
        T data = list.get(size-1);
        list.remove(size-1);
        return data;
    }

    //fetches the size
    public int size() {
        return list.size();
    }
}
