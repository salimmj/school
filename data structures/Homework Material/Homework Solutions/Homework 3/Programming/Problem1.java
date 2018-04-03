
public class Problem1 {
	public static void main(String[] args) {
		try{
		ExpressionTree treeOne = new ExpressionTree("4");
		ExpressionTree treeTwo = new ExpressionTree("4 10 +");
		ExpressionTree treeThree = new ExpressionTree("2 3 + 8 4 / *");
		ExpressionTree treeFour = new ExpressionTree("34 2 - 5 *");		
System.out.println();
System.out.println("**************************************************");
System.out.println();


		System.out.println("This is the prefix form: ");
		System.out.println(treeOne.prefix());
		System.out.println("This is the infix form: ");
		System.out.println(treeOne.infix());
		System.out.println("This is the postfix form: ");
		System.out.println(treeOne.postfix());
		System.out.println("The expression evaluates to:");
		System.out.println(treeOne.eval());


System.out.println(); 
System.out.println("**************************************************"); 
System.out.println(); 
		
		System.out.println("This is the prefix form: ");
                System.out.println(treeTwo.prefix());
                System.out.println("This is the infix form: ");
                System.out.println(treeTwo.infix());
                System.out.println("This is the postfix form: ");
                System.out.println(treeTwo.postfix());
                System.out.println("The expression evaluates to:");
                System.out.println(treeTwo.eval());



System.out.println();
System.out.println("**************************************************"); 
System.out.println(); 

		System.out.println("This is the prefix form: ");
                System.out.println(treeThree.prefix());
                System.out.println("This is the infix form: ");
                System.out.println(treeThree.infix());
                System.out.println("This is the postfix form: ");
                System.out.println(treeThree.postfix());
                System.out.println("The expression evaluates to:");
                System.out.println(treeThree.eval());


System.out.println();
System.out.println("**************************************************"); 
System.out.println(); 

		System.out.println("This is the prefix form: ");
                System.out.println(treeFour.prefix());
                System.out.println("This is the infix form: ");
                System.out.println(treeFour.infix());
                System.out.println("This is the postfix form: ");
                System.out.println(treeFour.postfix());
                System.out.println("The expression evaluates to:");
                System.out.println(treeFour.eval());



		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
