//***********************
// Problem2.java
// Written by Salim M'jahad on 3/2/17
// msm2243
// Indexes words in a file and their occurrences (lines)
//***********************

import java.util.*;
import java.io.*;

public class Problem2 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid - enter file name cmd arg");
        }
        else {
            try {
                AvlTree avlt = new AvlTree();
                String s;
                File file = new File(args[0]);
                Scanner fin = null;
                fin = new Scanner(file);
                int line = 1;
                while (fin.hasNextLine()) {
                    s = fin.nextLine();
                    //s = s.replaceAll("[^a-zA-Z]", "").toLowerCase(); //stackoverflow source
                    String[] sa = s.split("[^a-zA-Z0-9]+");

                    for (int i = 0; i < sa.length; i++) {

                        sa[i] = sa[i].toLowerCase();
                        if (!sa[i].equals("") && !sa[i].equals("\n")) {
                            avlt.indexWord(sa[i], line);
                        }
                    }

                    line++;

                }

                avlt.printIndex();

            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }
    }
}


