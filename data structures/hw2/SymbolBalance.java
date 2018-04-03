//***********************
// SymbolBalance.java
// Written by Salim M'jahad on 2/16/17
// msm2243
// This program checks whether a text file is well balanced
//***********************

import java.io.*;
import java.util.*;

public class SymbolBalance {


    public static void main(String[] args) throws IOException {

        //chars to detect
        String[] openings = {"{", "(", "["};
        String[] endings = {"}", ")", "]"};

        //reader for the file
        BufferedReader br = new BufferedReader(new FileReader(args[0]));

        //we read line by line
        String line;

        //these booleans detect the inside of literals and comments
        boolean inLiteral = false;
        boolean inComment = false;

        //we move through every line with this char
        char c;

        //we store our opened chars here in order to pop-check later
        MyStack<Character> stack = new MyStack<>();

        //this helps me detect only one problem and break out of all the process
        outerloop:
        while (true) {

            //read line by line
            while ((line = br.readLine()) != null) {

                //read char by char
                for (int i = 0; i < line.length(); i++) {
                    c = line.charAt(i);

                    //check for comments
                    if (c == '/' & i + 1 < line.length()) {
                        if (line.charAt(i + 1) == '*') {
                            inComment = true;
                        }
                    }

                    //if statements that define how we look at the string when
                    //we re not in a comment or literal if we are we just loop through
                    if (!inLiteral & !inComment) {

                        //if the char is an opening we push it
                        if (Arrays.asList(openings).contains(Character.toString(c)))
                            stack.push(c);

                        //if it is an ending we check whether it is appropriate
                        else if (Arrays.asList(endings).contains(Character.toString(c))) {
                            if (stack.size() != 0) {
                                char p = stack.pop();

                                //invalid ending so we break through
                                if (p != openings[Arrays.asList(endings).indexOf(Character.toString(c))].charAt(0)) {
                                    System.out.println("Unbalanced! Symbol " + c + " is mismatched.");
                                    break outerloop;
                                }
                            // this happens when we have an ending before an opening
                            } else {
                                System.out.println("Unbalanced! Symbol " + c + " is mismatched.");
                                break outerloop;
                            }
                        //this checks to see if we are entering literals
                        } else if (c == '\"') {
                            inLiteral = true;
                        }
                    } else if (inLiteral) {
                        if (c == '\"') {
                            inLiteral = false;
                        }
                    } else if (inComment) {
                        if (c == '*' & i + 1 < line.length()) {
                            if (line.charAt(i + 1) == '/') {
                                inComment = false;
                            }
                        }
                    }
                }
            }
            br.close();

            //we give priority to literals and comments to be effective
            //we break at the more prioritized problem, if everything goes well
            //we say the file works fine
            if (stack.size() != 0 || inLiteral || inComment) {
                if (inLiteral || inComment) {
                    if (inLiteral) {
                        System.out.println("Unbalanced! Literal left open.");
                        break outerloop;
                    }
                    if (inComment) {
                        System.out.println("Unbalanced! Comment left open.");
                        break outerloop;
                    }
                } else if (stack.size() != 0) {
                    System.out.println("Unbalanced! " + stack.pop() + " left open.");
                    break outerloop;
                }
            } else {
                System.out.println("The file works just fine!");
                break outerloop;
            }
        }
    }

}
