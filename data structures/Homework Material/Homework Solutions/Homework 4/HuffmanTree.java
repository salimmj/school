import java.util.*;
import java.io.*;


public class HuffmanTree {
	
	public HuffmanTree(String message) {

		// Build the tree:
		// Compute Histogram first
		HashMap<Character,HuffmanNode> histogram = new HashMap<>();
		for(int i=0;i<message.length();i++) {
			Character c = message.charAt(i);
			HuffmanNode node = histogram.get(c);
			if(node==null)
				histogram.put(c,new HuffmanNode(c.toString(),1));
			else
				node.frequency++;
		}
		// Take each node in the histogram and insert into a priority queue.
		PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(histogram.values());

		// Keep going until there is only one node left on the pq.
		while(pq.size() != 1) {
			HuffmanNode one = pq.poll();
			HuffmanNode two = pq.poll();

			HuffmanNode temp = new HuffmanNode(null,one.frequency+two.frequency);

			temp.left = one;
			temp.right = two;
			pq.offer(temp);
		}
		// The last value on the pq is the root
		root = pq.poll();

		// create the encoder from the constructed tree
		encoder = new HashMap<Character,String>();
		buildEncoder(root,"");


	}

	private void buildEncoder(HuffmanNode t, String code) {

		// if a leaf add the code
		if(t.left == null && t.right == null) {
			encoder.put(t.value.charAt(0),code);
			return;
		}
			
		// left is a 0
		buildEncoder(t.left,code + "0");

		// right is a 1
		buildEncoder(t.right,code + "1");

	}

	// Encode the message and return it
	public String encode(String message) {
		
		String out = "";

		for(int i=0;i<message.length();i++) {
			out = out + encoder.get(message.charAt(i));
		}

		return out;

	}

	// Decode a given message using the tree.
	public String decode(String coded) {

		HuffmanNode t = root;
		String out = "";

		for(int i=0;i<coded.length();i++) {
			if(coded.charAt(i) == '0')
				t = t.left;
			else
				t = t.right;
			

			if(t.left == null  && t.right == null) {
				out += t.value;
				t = root;
			}
		}

		return out;

	}




	private static class HuffmanNode implements Comparable<HuffmanNode> {

		HuffmanNode left;
		HuffmanNode right;
		String value;
		int frequency;
		
		public HuffmanNode(String v, int f) {
			value = v;
			frequency = f;
		}

		// compare by frequency
		public int compareTo(HuffmanNode other) {
			return this.frequency - other.frequency;
		} 

	}
	

	HuffmanNode root;
	HashMap<Character,String> encoder;


	public static final void main(String[] args) {


		try {
		//String m = args[0];
		BufferedReader newFile = new BufferedReader(new FileReader(args[0]));
		String  message = "";
		String line;

			while( (line = newFile.readLine()) != null) {
			message +=  line;
			//System.out.println(line);
		}
		
		HuffmanTree t = new HuffmanTree(message);
		System.out.println("Here is the full set of characters and their Huffman codes.");
		System.out.println("Character              |            Huffman Code");
		System.out.println("------------------------------------------------");
		for (Map.Entry<Character,String> entry : t.encoder.entrySet()) {
    		Character key = entry.getKey();
    		String value = entry.getValue();

    		System.out.println(key + "                      |                  " + value);
  
		}
		Scanner s = new Scanner(System.in);
		System.out.println("Now please enter a sequence of 0s and 1s based off our generated Huffman Tree so we can decode!");
		System.out.println("Decoded result: " + t.decode(s.nextLine()));
		System.out.println("Now please enter a sequence of characters so we can encode!");
		System.out.println("Encoded result: " + t.encode(s.nextLine()));
		
		}
		
		catch(Exception e) {
			System.out.println("error");

		}

	}

}