/* Homework 4 SpellChecker Solutions 
Written by Gregory Yap */

import java.util.*;
import java.io.*;


public class SpellChecker {

	
	public static final void main(String[] args){
		//take file names from command line argument
		System.out.println("Dictionary file name: " + args[0]);
		System.out.println("Spellcheck target file name: " + args[1]);
		File dict = new File(args[0]);
		File text = new File(args[1]);
		Scanner readDict = null;
		Scanner readText = null;
				
		// in case the user inputs an invalid file - try/catch for both files
		try {
			readDict = new Scanner(dict);
			System.out.println("Dictionary text file successfully read.");		
		}
		catch(FileNotFoundException x){
			System.out.println("Reading failed - dictionary file might"
					+ " not exist.");
			System.exit(1);
		}
		try {
			readText = new Scanner(text);
			System.out.println("Spellcheck target file successfully read.");
		}
		catch(FileNotFoundException x){
			System.out.println("Reading failed - spellcheck target"
					+ " file might not exist.");
			System.exit(1);
		}

		//now that we got that mess sorted...
		//read in entire dictionary into hashset
		HashSet dictset = new HashSet();
		while(readDict.hasNextLine()){
			// taking in one line at a time,...
			String new1 = readDict.nextLine();
			// decapitalizing...
			String new2 = new1.toLowerCase();
			//add to hashset.
			dictset.add(new2);
		}
			
		//now working on target file.
		int lineIndex = 1;
		while(readText.hasNextLine()){
			String nextLine = readText.nextLine();
			//check if line is empty. if it is, increment index & ignore
			if(nextLine.length()==0){
				lineIndex++;
				continue;
			}
			else{
				//line not empty. everything to lower case
				String line = nextLine.toLowerCase();
			    // parsing line into individual strings demarcated by spaces...
			    String[] lineA = line.split("\\s+");
			    for(String str : lineA){
					//next 2 lines adapted from Linan Q. off Piazza
					//essentially regex expressions to remove all lead/trail
			    	//spaces
					String str2 = str.replaceAll("[^A-z0-9]+$", "");
					String str3 = str2.replaceAll("^[^A-z0-9]+", "");
					//can we find str3 in dictionary? if no, perform the
					//three tests to suggest words if necessary.
					if(!dictset.contains(str3)){
						System.out.println("Misspelled word: '" +
						str3 + "', at line number: "+ lineIndex);
						//these hashsets are invoked in their respective
						//checks below in order to avoid duplicate suggestions
						HashSet addsugset = new HashSet();
						HashSet remsugset = new HashSet();
						
						//ADDITION CHECK
						for(int j=0; j<=str3.length(); j++){
							//ascii values for a~z are 97-122
							for(int k=97;k<123;k++){
								StringBuilder addStr = new StringBuilder();
								addStr.append(str3);
								//cast k to char and insert at position j
								//check for duplicates before printing
								addStr.insert(j, (char)k);
								String finalStrA = addStr.toString();
								if(!addsugset.contains(finalStrA)
								&& dictset.contains(finalStrA)){
									addsugset.add(finalStrA);
									System.out.println("If you add a "
									+ "character at position " + j 
									+ ", you can get a valid word '" + 
									finalStrA + "'.");									
								}
							}
						}
						
						//REMOVAL CHECK
						for(int n=0; n<str3.length(); n++){
							//removal by simply ignoring char at position n
							String remStr = str3.substring(0, n) + 
									str3.substring(n+1);
							//check for duplicates before printing
							if(dictset.contains(remStr)&&
							!remsugset.contains(remStr)){
								remsugset.add(remStr);
								System.out.println("If you remove the "
								+ "character at position " + n + ", you can "
								+ "get a valid word '" + remStr + "'.");
							}											
						}
						
						//ADJACENT CHARACTER SWOP CHECK
						for(int y=0;y<str3.length()-1;y++){
							StringBuilder excStr = new StringBuilder();
							excStr.append(str3);
							char prev = str3.charAt(y);
							char next = str3.charAt(y+1);
							//swop here
							excStr.setCharAt(y, next);
							excStr.setCharAt(y+1, prev);
							String finalStrE = excStr.toString();
							if(dictset.contains(finalStrE)){
								System.out.println("If you swap the chars "
								+ "at positions " +y+" and "+(y+1)+
								", you can get a valid word '" + finalStrE
								+ "'.");
							}							
						}
					}
			    }
			    lineIndex++;
			}
		}
	
	System.out.println("End of output.");
	}
}
