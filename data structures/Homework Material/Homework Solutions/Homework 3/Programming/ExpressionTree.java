/*
* 3134 Data Structures 
* HW3 Problem 1
* ExpressionTree.java
* Builds an Expression Tree using a postfix expression
*/
import java.util.LinkedList;

public class ExpressionTree {

  private String expression;
  private ExpressionNode expressionTree;

  public ExpressionTree(String expression) {
    this.expression = expression;
    expressionTree = null;
    buildTree();
  }

  private void buildTree() {
    String[] tokens = expression.split("\\s+");
    LinkedList<ExpressionNode> stack = new LinkedList<>();
    for (String token : tokens) {
      if (isNumeric(token)) {
        ExpressionNode node = new ExpressionNode(Integer.valueOf(token));
        stack.push(node);
      } else {
        ExpressionNode right = stack.pop();
        ExpressionNode left = stack.pop();
        ExpressionNode node = new ExpressionNode(token.charAt(0), left, right);
        stack.push(node);
      }
    }
    if (stack.size() != 1) {
      throw new InvalidExpressionException();
    } else {
      expressionTree = stack.pop();
    }
  }

  private boolean isNumeric(String token) {
    return token.matches("\\d+");
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    toString(expressionTree, sb, 0);
    return sb.toString().trim();
  }

  private void toString(ExpressionNode node, StringBuilder sb, int level) {
    if (node.left != null) {
      toString(node.left, sb, level + 1);
    }
    for (int i = 0; i < level; i++) {
      sb.append(" ");
    }
    sb.append(node);
    sb.append("\n");
    if (node.right != null) {
      toString(node.right, sb, level + 1);
    }
  }

  public int eval() {
    return (int) eval(expressionTree);
  }

  private double eval(ExpressionNode node) {
    if (node.isNumber()) {
      return node.number;
    } else {
      if (node.operator == '+') {
        return eval(node.left) + eval(node.right);
      }
      if (node.operator == '-') {
        return eval(node.left) - eval(node.right);
      }
      if (node.operator == '*') {
        return eval(node.left) * eval(node.right);
      }
      if (node.operator == '/') {
        return eval(node.left) / eval(node.right);
      }
      throw new InvalidExpressionException();
    }
  }

  public String postfix() {
    StringBuilder sb = new StringBuilder();
    postfix(expressionTree, sb);
    return sb.toString().trim();
  }

  private void postfix(ExpressionNode node, StringBuilder sb) {
    if (node.left != null) {
      postfix(node.left, sb);
    }
    if (node.right != null) {
      postfix(node.right, sb);
    }
    sb.append(node);
    sb.append(" ");
  }

  public String prefix() {
    StringBuilder sb = new StringBuilder();
    prefix(expressionTree, sb);
    return sb.toString().trim();
  }

  private void prefix(ExpressionNode node, StringBuilder sb) {
    sb.append(node);
    sb.append(" ");
    if (node.left != null) {
      prefix(node.left, sb);
    }
    if (node.right != null) {
      prefix(node.right, sb);
    }
  }

  public String infix() {
    StringBuilder sb = new StringBuilder();
    infix(expressionTree, sb);
    return sb.toString().trim();
  }

  private void infix(ExpressionNode node, StringBuilder sb) {
    if (!node.isNumber()) {
      sb.append("( ");
    }
    if (node.left != null) {
      infix(node.left, sb);
    }
    sb.append(node);
    sb.append(" ");
    if (node.right != null) {
      infix(node.right, sb);
    }
    if (!node.isNumber()) {
      sb.append(") ");
    }
  }

  public class ExpressionNode {

    public int number;
    public char operator;
    public boolean isNumber;
    public ExpressionNode left;
    public ExpressionNode right;

    public ExpressionNode(int number) {
      this.number = number;
      isNumber = true;
    }

    public ExpressionNode(char operator, ExpressionNode left, ExpressionNode right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
      isNumber = false;
    }

    public boolean isNumber() {
      return isNumber;
    }

    public String toString() {
      return isNumber ? Integer.toString(number) : Character.toString(operator);
    }
  }

  @SuppressWarnings("serial")
  public class InvalidExpressionException extends RuntimeException {
  }
}
