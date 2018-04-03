//***********************
// AvlTree.java
// Written by Salim M'jahad on 3/2/17
// msm2243
// AVL Tree implemented with methods to solve problem 2
//***********************

import java.util.*;


/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AvlTree
{
    /**
     * Construct the tree.
     */
    public AvlTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree
     */
    public void indexWord( String x, int lineNum )
    {
        root = indexWord( x, lineNum, root);
    }

    /**
     * Recursive
     */
    private AvlNode indexWord( String x, int line, AvlNode t)
    {
        if( t == null )
            return new AvlNode( x, null, null, line );

        int c = x.compareTo(t.word);

        if( c < 0 )
            t.left = indexWord( x, line,  t.left);
        else if( c > 0 )
            t.right = indexWord( x, line , t.right);
        else {
            if (line != (int) t.lines.peekLast()){
                t.lines.add(line);
            }
        }
        return balance( t );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( String x )
    {
        root = remove( x, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode remove( String x, AvlNode t )
    {
        if( t == null )
            return t;   // Item not found; do nothing

        int compareResult = x.compareTo( t.word );

        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.word = findMin( t.right ).word;
            t.right = remove( t.word, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return balance( t );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public String findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).word;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public String findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).word;
    }

    /**
     * Find word then return number of lines
     */

    public List getLinesForWord( String x )
    {
        return getLinesForWord( x, root );
    }


    /**
     * Recursive
     */
    public List getLinesForWord( String x, AvlNode t )
    {

        if( t != null )
        {
            int c = x.compareTo( t.word );

            if( c < 0 )
                return getLinesForWord(x, t.left);
            else if( c > 0 )
                return getLinesForWord(x, t.right);
            else {
                return t.lines;
            }
        } else {
            return new LinkedList<String>(); //no occurence
        }
    }
    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printIndex()
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printIndex( root );
    }


    /**
     * Recursive
     */
    private void printIndex( AvlNode t )
    {
        if( t != null )
        {
            printIndex( t.left );
            System.out.println( t.word + ": ");

            try {
                for (int i = 0; i < t.lines.size(); i++) {
                    System.out.print(" "+t.lines.get(i));
                }
            } catch (UnderflowException e) {

                System.out.println(e);
            }

            System.out.print("\n");

            printIndex( t.right );
        }
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode balance( AvlNode t )
    {
        if( t == null )
            return t;

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode t )
    {
        if( t == null )
            return -1;

        if( t != null )
        {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOOOOOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode findMin( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( String x, AvlNode t )
    {
        while( t != null )
        {
            int compareResult = x.compareTo( t.word );

            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.word );
            printTree( t.right );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode t )
    {
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode rotateWithLeftChild( AvlNode k2 )
    {
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode rotateWithRightChild( AvlNode k1 )
    {
        AvlNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode doubleWithLeftChild( AvlNode k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode doubleWithRightChild( AvlNode k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    private static class AvlNode<AnyType>
    {
        AvlNode( String theElement, int lineNum )
        {
            this( theElement, null, null, lineNum );
        }

        AvlNode( String word, AvlNode<AnyType> lt, AvlNode<AnyType> rt, int line)
        {
            this.word  = word;
            left     = lt;
            right    = rt;
            lines    = new LinkedList<>();
            lines.add(line);
            height   = 0;
        }

        String           word;      // The data in the node
        LinkedList<Integer> lines;
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode root;


}