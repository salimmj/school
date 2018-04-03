/*
* 3134 Data Structures 
* HW3 Problem 2
* Tester for Problem 2
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Problem2 {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java Problem2 <filename>");
    }
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
      String line;
      int lineCount = 1;

      AvlTree<WordLinePair> tree = new AvlTree<WordLinePair>();

      while ((line = bufferedReader.readLine()) != null) {
        // replace all characters that are not alphanumeric/spaces
        line = line.replaceAll("[^A-z0-9\\s+]", "").toLowerCase();
        
        // split by space
        String[] words = line.split("\\s+");
        
        // use Hashset to get a unique set of words (removes duplicates)
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        
        for (String uniqueWord : uniqueWords) {
          WordLinePair pair = new WordLinePair(uniqueWord);
          WordLinePair existingPair = tree.get(pair);
          if (existingPair == null) {
            pair.line.add(lineCount);
            tree.insert(pair);
          } else {
            existingPair.line.add(lineCount);
          }
        }
        lineCount++;
      }
      bufferedReader.close();

      for (WordLinePair pair : tree) {
        System.out.println(pair);
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
