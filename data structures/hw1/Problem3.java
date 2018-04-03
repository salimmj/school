//***********************
// Problem3.java
// Written by Salim M'jahad on 1/25/17
// msm2243
// Calculates the running times of different algorithms
//***********************
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Problem3 {

    //method to test
    public static int foo(int n, int k) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(n<=k)
            return 1;
        else
            return foo(n/k,k) + 1;
    }

    public static void main(String[] args) {
        int n, z;
        int sum;

        long starTime, endTime;

        long[] a = new long[20];
        long[] b = new long[20];
        long[] c = new long[20];

        for (z=1; z<=20; z++) {

            n = z*100;
            starTime = System.nanoTime( );

            //piece of code to test
            sum = 0;
            for (int i = 0; i < 23; i++)
                for (int j = 0; j < n; j++)
                    sum = sum + 1;

            endTime = System.nanoTime( );

            //storing numbers in an array
            a[z-1] = endTime - starTime;
        }

        for (z=1; z<=20; z++) {

            n = z*100;
            starTime = System.nanoTime();

            //piece of code to test
            sum = 0;
            for (int i = 0; i < n; i++)
                for (int k = i; k < n; k++)
                    sum = sum + 1;

            endTime = System.nanoTime();

            //storing numbers in an array
            b[z-1] = endTime - starTime;
        }

        for (z=1; z<=20; z++) {

            n = z*100;

            starTime = System.currentTimeMillis();

            // call of method to test
            foo(n, 2);

            endTime = System.currentTimeMillis();

            //storing numbers in an array
            c[z-1] = endTime - starTime;
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new
                FileOutputStream("values.txt"), StandardCharsets.UTF_8))) {

            writer.write(" A \n");
            System.out.println(" A \n");
            for (int j = 0; j<20; j++ ) {

                writer.write(a[j] + "\n");
                System.out.println(a[j] + "\n");

            }

            writer.write("\n\n B  \n");
            System.out.println(" B \n");
            for (int j = 0; j<20; j++ ) {

                writer.write(b[j] + "\n");
                System.out.println(b[j] + "\n");

            }

            writer.write("\n\n C  \n");
            System.out.println(" C \n");
            for (int j = 0; j<20; j++ ) {

                writer.write(c[j] + "\n");
                System.out.println(c[j] + "\n");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
