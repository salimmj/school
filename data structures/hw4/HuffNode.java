//***********************
// HuffNode.java
// Written by Salim M'jahad on 4/13/17
// msm2243
// Huffington tree node
//***********************

public class HuffNode {

    public String key;
    public int frequency;
    public HuffNode left;
    public HuffNode right;

    //node of the huffington tree
    public HuffNode(String key, int frequency, HuffNode left, HuffNode right){
        this.key = key;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}
