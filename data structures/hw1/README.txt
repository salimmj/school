//***********************
// README.txt
// Written by Salim M'jahad on 1/25/17
// msm2243
// DESCRIPTION
//***********************

>>> Rectangle.java

This class represents a rectangle by length and width. It implements
Comparable. It has a simple constructor and a method compareTo() that compares
the rectangles in addition to the method print() which prints out the info
about the rectangle.

>>> Problem1.java

This class has two static methods. A findMax() which takes as an argument any
array of comparable objects and finds the largest. A main() which creates an
array of 50 Rectangles of random sizes, prints their info, then finds the
largest by calling compareTo().

>>> Problem2.java

This class serves to binary search through arrays recursively and has three
static methods. The first method is binarySearch() which takes an array of
comparable objects and the object we're looking for. This method calls the
helper method bSearch() which takes two more arguments start and finish and
runs recursively. At last, there is a method main() which tests binarySearch().
It copies all the words from dictionary.txt to an ArrayList<String>, sorts it,
copies it into an Array then looks for the word "astronomy". This word can be
replaced by any other. It prints the index of the word then word in the index
to check that it is indeed the right index.

>>> Problem3.java

This class tests the running times of three algorithms. The last one is put in
a method foo() which includes a sleep(). In the main() method, three arrays are
created, in each the growing values of the time that it takes the algorithms to
run for n are stored. These are then printed into the text file values.txt.