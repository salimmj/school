//***********************
// Problem1.java
// Written by Salim M'jahad on 3/2/17
// msm2243
// Hard coded example of postfix expression tree
//***********************

public class Problem1 {

    public static void main(String[] args) {

        String s = "5 9 * 3 + 2 1 + /";

        ExpressionTree t = new ExpressionTree(s);

        System.out.println("eval: " + t.eval());
        System.out.println("postfix: " + t.postfix());
        System.out.println("prefix: " + t.prefix());
        System.out.println("infix: " + t.infix());

    }
}
