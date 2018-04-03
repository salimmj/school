/*
* 3134 Data Structures 
* HW3 Problem 1
* WordLinePair.java
* Holds the word and the linked list that holds the line numbers
*/

import java.util.LinkedList;

public class WordLinePair implements Comparable<WordLinePair> {

  public String word;
  public LinkedList<Integer> line;

  public WordLinePair(String word) {
    this.word = word;
    line = new LinkedList<>();
  }

  @Override
  public int compareTo(WordLinePair o) {
    return o.word.compareTo(word);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(word);
    sb.append(" ");
    sb.append(line);
    return sb.toString();
  }
}
