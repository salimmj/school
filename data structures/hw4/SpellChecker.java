//***********************
// SpellChecker.java
// Written by Salim M'jahad on 4/13/17
// msm2243
// This class checks the spelling of the words in a text file
//***********************

import java.util.*;
import java.io.*;



public class SpellChecker {

    public static void main(String args[]) {
        // HashSet declaration
        HashSet<String> dictionary = new HashSet<String>();

        //reads the dictionary and the file
        try {
            BufferedReader dic = new BufferedReader(new FileReader(args[0]));

            String word;

            while( (word = dic.readLine()) != null) {
                dictionary.add(word);
            }

            dic.close();

            File file = new File(args[1]);
            Scanner in = new Scanner(file);

            String[] subs;
            int line = 0;
            while(in.hasNextLine()) {
                String l = in.nextLine();
                String[] words = l.toLowerCase().split(" ");
                for (int i = 0; i < words.length; i++) {
                    word = words[i].replaceAll("[^a-z0-9]+$", "");
                    if (!words[i].equals("") && !dictionary.contains(word)) {

                        System.out.println("Misspelled word in line " + line + ": "
                                + word);

                        subs = getValids(word, dictionary);

                        System.out.println("Valid subs: " + Arrays.toString(subs));

                    }

                }

                line++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //method to get possible corrections for misspelled word
    private static String[] getValids(String word, HashSet<String> dict) {

        ArrayList<String> arL = new ArrayList<>();

        // add a character
        for (char c : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            String added = word + c;
            if (dict.contains(added)) {
                arL.add(added);
            }
        }


        // remove a character
        if (word.length() > 1) {
            for (int i = 0; i < word.length(); i++) {
                String minus = word.substring(0, i) + word.substring(i + 1, word.length());
                if (dict.contains(minus)) {
                    arL.add(minus);
                }
            }
        }

        //exchange adjacent characters
        if (!(word.length() < 2)) {
            for (int i = 0; i < word.length() - 1; i++) {
                String exc = word.substring(0, i) + word.charAt(i + 1) + word.charAt(i)
                        + word.substring(i + 2);

                if (dict.contains(exc)) {
                    arL.add(exc);
                }
            }
        }

        return arL.toArray(new String[arL.size()]);
    }


}
