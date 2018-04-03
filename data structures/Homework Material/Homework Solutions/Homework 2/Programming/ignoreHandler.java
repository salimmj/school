/*
 * ignoreHandler by Mert Ussakli
 * Used for SymbolBalance.java
 */

public class ignoreHandler {
		Boolean ignore; //ignore or not
		int ignoreCondition;  /*string or code block. 0 for code block, 1 for string
								-1 for no ignore */ 
							
		public ignoreHandler() {
			ignore = false;
			ignoreCondition = -1; 
		}
		
		public void codeBlockStart() {
			this.ignore = true;
			ignoreCondition = 0;
		}
		
		public void codeBlockEnd() {
			this.ignore = false;
			ignoreCondition = -1;
		}
		
		public void stringStart() {
			this.ignore = true;
			ignoreCondition = 1;
		}
		
		public void stringEnd() {
			this.ignore = false;
			ignoreCondition = -1;
		}
	}