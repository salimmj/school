Info about each class:

#### MyStack.java

This class defines a generic stack implemented with an Arraylist.

## MyStack():
    Constructs an arraylist

## push(T item)
    Puts an element in the front of the list

## pop()
    Returns the element at the front and removes it from the list

## size()
    Accesses the size of the stack


#### SymbolBalance.java

This class has only one method: main()

First, while working on the problem I decided to include it in a big while loop
which I constructed by prioritizing some problems over the others in an appropriate
way. Once a problem is detected I break the loop.

To detect "{", "(", "[" I use a list to check if each char is one of them before
I store them in a stack. I also check if each char is one of "}", ")", "]", if so
I use "parallelism" of both lists openings and closing to see if that closing matches
its opening. If it doesn't then it considers it a mismatch.
It also detects comments and literal by booleans inComment and inLiteral
Now if everything goes well I check if the stack is empty or not.
In other words, since I pop the opening brackets from the stack if I find a match,
finding any element in the stack means there is a bracket left open.


#### Test.java

file to test on SymbolBalance


#### TwoStackQueue.java

This class defines a queue out of two stacks.

## TwoStackQueue():
    Constructs an two stacks and 2 instance

## flip(MyStack<AnyType> from, MyStack<AnyType> to)
    moves pops elements from from and pushes them to to

## enqueue()
    flip elements from output stack to input stack then pushes them to the latter

## dequeue()
    flip elements from input stack to output stack then pops them from the latter

## size()
    Accesses the size of the queue which is the max of sizes of the two stacks

## isEmpty()
    if both stacks are empty it returns true



#### Program2.java

Tests the TwoStackQueue class by enqueing dequeing and printing strings

