Written by me Salim M’jahad
msm2243

Problem is solved by the java files

Compile with: javac Display.java

Run with: java Dsiplay (or java -Xint Display if you want to test the time of the algs)


Discussion of the time of both algorithm for each n:

To be precise, I ran the program with -Xint

(nearest neighbor, brute force)

2 - (0,0)
3 - (0,0)
4 - (0,0)
5 - (0,1)
6 - (0,6)
7 - (0,47)
8 - (0,406)
9 - (1,3913)

Brute force is, thus, extremely inefficient as its cost is O(n!) while nearest neighbor is only O(n^2)
