/*
// 3134 HW1 Problem3
// 
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Problem3 {

  //Code from Written 3 part a
  public static void partA(int n) {
    int sum = 0;
    for (int i = 0; i < 23; i++)
      for (int j = 0; j < n; j++) {
        System.out.println(sum);
      }
  }

  //Code from Written 3 part b
  public static void partB(int n) {
    int sum = 0;
    for (int i = 0; i < n; i++)
      for (int k = i; k < n; k++) {
        System.out.println(sum);
      }
  }

  //Code from Written 3 part c
  public static int partC(int x, int k) {
    if (x <= k)
      return 1;
    else
      return partC(x / k, k) + 1;
  }

  //Main method
  public static void main(String[] args) {

    try {
      //Setting up to write to a file called Problem3.txt
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("Problem3.txt")));

      bufferedWriter.write("part,n,runtime\n");

      int minN = 0;
      int maxN = 2000;
      int increment = 100;
      int repetitions = 1;

      //Testing part a maxN times
      for (int n = minN; n < maxN; n += increment) {
        int startTime = (int) System.currentTimeMillis();
        for (int i = 0; i < repetitions; i++) {
          partA(n);
        }

        int endTime = (int) System.currentTimeMillis();
        int elapsed = endTime - startTime;
        bufferedWriter.write(String.format("partA,%d,%d\n", n, elapsed));
      }

      //Testing part b maxN times
      for (int n = minN; n < maxN; n += increment) {
        int startTime = (int) System.currentTimeMillis();
        for (int i = 0; i < repetitions; i++) {
          partB(n);
        }

        int endTime = (int) System.currentTimeMillis();
        int elapsed = endTime - startTime;
        bufferedWriter.write(String.format("partB,%d,%d\n", n, elapsed));
      }

      //Testing part c maxN times
      for (int n = minN; n < maxN; n += increment) {
        int startTime = (int) System.currentTimeMillis();
        for (int i = 0; i < repetitions; i++) {
          partC(n, 2);
        }
        int endTime = (int) System.currentTimeMillis();
        int elapsed = endTime - startTime;
        bufferedWriter.write(String.format("partC,%d,%d\n", n, elapsed));
      }

      //Close bufferedWriter
      bufferedWriter.close();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
