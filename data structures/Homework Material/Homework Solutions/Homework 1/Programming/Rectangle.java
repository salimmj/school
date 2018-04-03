/*
// 3134 HW1 Problem1
// Rectangle.java
// Implementation of Rectangle
*/

public class Rectangle implements Comparable<Rectangle> {

  private int length;
  private int width;

  //Constructor
  public Rectangle(int length, int width) {
    this.length = length;
    this.width = width;
  }

  //Returns the width
  public int getWidth(){
    return width;
  }

  //Returns the length
  public int getLength(){
    return length;
  }

  //Compares two Rectangle objects
  @Override
  public int compareTo(Rectangle o) {
    int perimeter = 2 * length + 2 * width;
    int perimeterOther = 2 * o.length + 2 * o.width;
    return perimeter - perimeterOther;
  }

  //toString() method to print Rectangle objects to the console
  public String toString() {
    return String.format("Length:%d Width: %d", length, width);
  }
}
