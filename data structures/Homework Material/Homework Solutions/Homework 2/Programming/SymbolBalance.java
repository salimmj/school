/*
 * Symbol Balance by Mert Ussakli
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SymbolBalance {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			String result = balanceSymbols(br);
			System.out.println(result);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("The file name you entered couldn't be found.");
		} 	
	}
	
	
	/** This function handles everything related to balancing symbols.
	 * @param br The BufferedReader to pass in. This should have the file.
	 * @return Returns a String containing the error or the success message.
	 */
	private static String balanceSymbols(BufferedReader br) {
		String line;
		ignoreHandler ignore = new ignoreHandler();
		MyStack<Character> stack = new MyStack<>();
		
		try {
			while((line = br.readLine()) != null)
			{
				char[] charArray = line.toCharArray();

					for(int i = 0; i < charArray.length;  i++) {
						char currentChar = charArray[i];
						if(!ignore.ignore) {
							if(currentChar == '/') { //this should handle single line comments
								if(charArray[i+1] == '/')
									i = charArray.length;
								
								else if(charArray[i+1] == '*') {
									ignore.codeBlockStart();
									i++;
								}
				
							}
							else if(currentChar == '"') { //this should handle string mode
								ignore.stringStart();
							}

							else if(currentChar == '*') { //this should handle right */ unmatched  
								if(charArray[i+1] == '/') //by a left */
									return "Unbalanced! */ without a matching /*";
							}
							
							else if(isParenthesis(currentChar)) {
								if(stack.isEmpty())	{
									if(isOpen(currentChar))
										stack.push(currentChar);
									else {
										br.close();
										return "Unbalanced! " + currentChar + "has no left brace!";
									}
								}
								//if closed parenthesis
								else if(isClosed(currentChar)) {
									if(isMatched(stack.peek(), currentChar)) {
											stack.pop();
										}
										else {
											br.close();
											return "Unbalanced! Symbol " + stack.peek() + "is matched with " + currentChar;
										}
								}
								//if open parenthesis, just push
								else {
									stack.push(currentChar);	
								}
							}
						}
						//ignore mode handling here.
						else {		
							if(ignore.ignoreCondition == 1) {
								if(currentChar == '"') {
									ignore.stringEnd();
								}
								else { 
									if(i == charArray.length - 1) { //end of line, string hasn't ended
										br.close();
										return "Unbalanced! Line ended before string quotation ended";
									}
								}
							}
							else if(ignore.ignoreCondition == 0)
								if(currentChar == '*') {
									if(i != (charArray.length - 1)) { //can't be * at the end of a line 
										if(charArray[i+1] == '/') {
											ignore.codeBlockEnd();
											i++;
										}
								}
							}
						}
							
						
					}
			}
			if(ignore.ignore == true) {
				if(ignore.ignoreCondition == 0)
					return "Unbalanced! Unmatched /*";
				if(ignore.ignoreCondition == 1)
					return "Unbalanced! Unmatched \"";
			}
			
			if(!stack.isEmpty())
				return "Unbalanced! " + stack.peek() + " is unmatched.";
			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Reading line from BufferedReader failed.");
			
		}

			try {
				br.close();
				return "Yay! No errors";
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("br.close() failed");
			}
		return "Something went terribly wrong.";
	}
	
	private static boolean isMatched(Character open, Character closed) {
		if(open.compareTo('(') == 0 && closed.compareTo(')') == 0)
			return true;
		if(open.compareTo('[') == 0 && closed.compareTo(']') == 0)
			return true;
		if(open.compareTo('{') == 0 && closed.compareTo('}') == 0)
			return true;
		return false;
	}

	private static Boolean isParenthesis(char c) {
		if(c == '{' || c == '}' || c == '(' || c == ')' ||
		   c == '[' || c== ']')
			return true;
		else
			return false;
	}
	
	private static Boolean isOpen(char c) {
		if(c == '{' || c== '(' || c=='[')
			return true;
		else
			return false;
	}
	
	private static Boolean isClosed(char c) {
		if(c == '}' || c== ')' || c==']')
			return true;
		else
			return false;
	}
}


	
	
