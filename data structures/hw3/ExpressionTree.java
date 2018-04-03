//***********************
// ExpressionTree.java
// Written by Salim M'jahad on 3/23/17
// msm2243
// Implementation of Expression Tree
//***********************

public class ExpressionTree {

    class ExpressionNode {
        public String data;
        public ExpressionNode leftChild;
        public ExpressionNode rightChild;

        public ExpressionNode(String d) {

            leftChild = null;
            rightChild = null;
            data = d;

        }
    }

    public ExpressionNode head;

    public ExpressionTree(String exp) {

        //split by any whitespace
        String[] values = exp.split("\\s+");

        MyStack<ExpressionNode> stack = new MyStack<>();

        for (int i = 0; i < values.length; i++) {

            if (values[i].equals("*") || values[i].equals("-") ||
                    values[i].equals("+") || values[i].equals("/")) {

                ExpressionNode n = new ExpressionNode(values[i]);
                n.rightChild = stack.pop();
                n.leftChild = stack.pop();
                stack.push(n);

            }
            else {

                ExpressionNode p = new ExpressionNode(values[i]);
                stack.push(p);
            }
        }

        head = stack.pop();
    }


    public int eval() {
        return eval(head);
    }

    private int eval(ExpressionNode root) {

        if (root.data.equals("*")) {
            return eval(root.leftChild) * eval(root.rightChild);
        } else if (root.data.equals("-")) {
            return eval(root.leftChild) - eval(root.rightChild);
        } else if (root.data.equals("+")) {
            return eval(root.leftChild) + eval(root.rightChild);
        } else if (root.data.equals("/")) {
            return eval(root.leftChild) / eval(root.rightChild);
        }

        return Integer.valueOf(root.data);
    }

    public String postfix() {
        return postfix(head);
    }

    private String postfix(ExpressionNode root) {

        if (root.leftChild == null && root.rightChild == null) {
            return root.data;
        }

        return postfix(root.leftChild) + " " +
                postfix(root.rightChild) + " " + root.data;
    }

    public String prefix() {

        return prefix(head);

    }

    private String prefix(ExpressionNode root) {

        if (root.leftChild == null && root.rightChild == null) {
            return root.data;
        }

        return root.data + " " + prefix(root.leftChild) +
                " " + prefix(root.rightChild);
    }

    public String infix() {

        return infix(head);
    }

    private String infix(ExpressionNode root) {

        if (root.leftChild == null && root.rightChild == null) {
            return root.data;
        }
        return "(" + infix(root.leftChild) + " " + root.data +
                " " + infix(root.rightChild) + ")";
    }


}

