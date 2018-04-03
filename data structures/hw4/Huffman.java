//***********************
// Huffman.java
// Written by Salim M'jahad on 4/13/17
// msm2243
// Solves Problem 2
//***********************

import java.io.*;
import java.util.*;

public class Huffman {

    public static int x = 0;
    public static HuffNode root;
    public static HashMap<String, Integer> map = new HashMap<>();


    private static class StringLengthComparator implements
            Comparator<HuffNode> {

        @Override
        public int compare(HuffNode x, HuffNode y)
        {
            if (x.frequency > y.frequency)
            {
                return 1;
            } else if (x.frequency < y.frequency)
            {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        File file = new File(args[0]);

        Scanner v = null;
        try {
            v = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HashMap<String, Integer> frequence = new HashMap<>();

        String line;
        while (v.hasNextLine()) {
            line = v.nextLine();
            for (int i = 0; i < line.length(); i++) {
                String character = Character.toString(line.charAt(i)).toLowerCase();
                if (frequence.containsKey(character)) {
                    frequence.put(character, frequence.get(character) + 1);
                } else {
                    frequence.put(character, 1);
                }
            }
        }


        Comparator<HuffNode> comparator = new StringLengthComparator();

        PriorityQueue<HuffNode> queue = new PriorityQueue<>(frequence.size() + 1,
                comparator);

        for (String key : frequence.keySet()) {
            HuffNode root = new HuffNode(key, frequence.get(key), null, null);
            queue.add(root);
        }

        System.out.println(String.format("%-2s   %-5s%13s", "Char",
                "Freq", "Total Bits"));
        for (HuffNode h : queue) {
            System.out.println(String.format("%-2s : %5s%13s", h.key,
                    h.frequency, 3 * h.frequency));
        }


        // queue to construct the tree
        int TNum = 1;
        while (queue.size() > 1) {
            HuffNode sml = queue.remove();
            HuffNode big = queue.remove();
            HuffNode root = new HuffNode("T" + TNum, big.frequency +
                    sml.frequency,
                    sml, big);
            queue.add(root);
            TNum++;
        }



        root = queue.remove();
        System.out.println("\n\n\n\nChar           Code           Freq           Total Bits");


        tvs(root, "");



        //takes input from the user to translate a binary code into string
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a binary code :   ");
        String code = s.next();

        String encoded = "";
        HuffNode node = root;

        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
                node = node.left;
            } else {
                node = node.right;
            }
            if (node.left == null && node.right == null) {
                encoded += node.key;
                node = root;
            }
        }
        if(node != root && !frequence.containsKey(node.key)) {
            System.out.println("ERROR");
        } else {
            System.out.println(encoded);
        }

        // encodes the string
        System.out.println("Enter a string:   ");
        code = s.next();

        String toString = "";
        for(int i = 0; i < code.length(); i++){
            toString += map.get(Character.toString(code.charAt(i)));
        }

        System.out.println(toString);
    }

    //Prints out and saves all the leaves
    private static void tvs(HuffNode node, String code){
        if(node != null){
            tvs(node.left, code + "0");
            tvs(node.right, code + "1");
            if(node.left == null && node.right == null){
                System.out.println(node.key + "           " +code +"           "+
                        node.frequency + "           "+ node.frequency *code.length());
                x += code.length()*node.frequency;
                map.put(node.key, Integer.parseInt(code));
            }
        }
    }

}