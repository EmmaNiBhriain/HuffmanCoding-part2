import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Loader {
	private Map<Character, Integer> pairMap = new HashMap<>();
	private Queue<Node> pairQueue = new PriorityQueue<Node>();
	private Map<String, String> countMap = new HashMap<>();
	
	private boolean DEBUGSCAN = false;
	private boolean DEBUGMAP = false;
	private boolean DEBUGQUEUE = false;
	private boolean DEBUGTREE = false;
	private boolean DEBUGBINARY = true;
	private boolean DEBUGENCODE = true;
	private boolean DEBUGHEADER = false;
	
	FileWriter filewriter1 = new FileWriter();
	FileWriter filewriter2 = new FileWriter();
	FileWriter filewriter3 = new FileWriter();
	private StringBuffer total = new StringBuffer("");
	private File file = null;
	private Node node1 = new Node(null, 0, null, null);
	
	UserInterface gui = new UserInterface();

	public Loader() {
		try {
			scanString();
		} catch (IOException e) {
			System.out.println("unable to read from file" + e);
		}
		createTree();
		total = this.getBinaryValue(node1, total);
		
		encodeFile(file);
	}
	

	/**
	 * Read through each character in a String. If the character has not been
	 * encountered earlier in the string, add it to a HashMap with a frequency
	 * of 1. If the character has already appeared in the String, increment it's
	 * frequency by 1.
	 */
	public void scanString() throws IOException {
		char eof = '*';
		char nextCharacter;
		int frequency;
		file = new File("C:\\Users\\eob00\\Desktop\\dataTest.dat");//gui.openFile();
		//BufferedReader buffer = new BufferedReader(new FileReader(file.getAbsolutePath()));	
		//String fileAddress =file.getAbsolutePath();//"C:\\Users\\eob00\\Desktop\\dataTest.dat";
		//System.out.println(fileAddress);
		//FileInputStream inByteStream = new FileInputStream(gui.getaFile());
		//int fileSize = inByteStream.available();
		
		//DataInputStream inDataStream = new DataInputStream(inByteStream);
		BufferedReader buffer = new BufferedReader(new FileReader("C:\\Users\\eob00\\Desktop\\dataTest.dat"));		
//		int fileSize = inDataStream.available();
		int charIndex;// = inDataStream.read();

		while ((charIndex = buffer.read())!= -1) {
			nextCharacter = (char) charIndex;
			System.out.println(nextCharacter);

			if (pairMap.containsKey(nextCharacter)) {
				frequency = pairMap.get(nextCharacter);
				frequency++;
				pairMap.replace(nextCharacter, frequency);
				if (DEBUGSCAN == true)
					System.out.println(nextCharacter + " appears " + frequency + " times");
			} else {
				pairMap.put(nextCharacter, 1);
				if (DEBUGSCAN == true)
					System.out.println(nextCharacter + " appears once");
			}
			
		}
		//inDataStream.close();
		filewriter1.writeToFile(file, Character.toString(eof));
		pairMap.put(eof, 1);
		filewriter1.close1();
	}

	/**
	 * Create a Pair object from each key, value pair in the map. Add these to a
	 * priority queue - The pairs will be ordered from most frequent to least
	 * frequent Create a tree by adding the two lowest frequency pairs to create
	 * a new node
	 */
	public void createTree() {
		Iterator<Map.Entry<Character, Integer>> entries = pairMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Character, Integer> entry = entries.next();
			Node node = new Node(Character.toString(entry.getKey()), entry.getValue(), null, null);
			pairQueue.add(node);
			
			if (DEBUGMAP == true)
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}

		/**
		 * print out the order of the elements in the priority queue.
		 * Check that they are ordered correctly
		 */
		if (DEBUGQUEUE == true){
			System.out.println("Priority queue contents in order");
			while (pairQueue.size() > 0) {
				Node x = (Node) pairQueue.remove();
				System.out.println(x.letter + " " + x.frequency);

			}
		}

		// choose the two trees with the smallest frequencies
		// last two values of the pairQueue

		while (pairQueue.size() > 2) {
			Node smallest1 = pairQueue.poll();
			Node smallest2 = pairQueue.poll();
			int newFreq = smallest1.frequency + smallest2.frequency;
			String newLetters = smallest1.letter + smallest2.letter;
			Node newNode = new Node(newLetters, newFreq, smallest1, smallest2);

			pairQueue.add(newNode);
			System.out.println("Queue size: " + pairQueue.size());
		}
		
		if (pairQueue.size() == 2) {
			Node smallest1 = pairQueue.poll();
			Node smallest2 = pairQueue.poll();
			int newFreq = smallest1.frequency + smallest2.frequency;
			String newLetters = smallest1.letter + smallest2.letter;
			Node newNode = new Node(newLetters, newFreq, smallest1, smallest2);
			node1.add(newNode);
			System.out.println("Queue size: " + pairQueue.size());
			if (DEBUGTREE == true)
				Node.print(node1, " ");
		}

	}


	/**
	 * Calculate the binary string value of each character
	 * 
	 * @param node
	 * @param totalCount
	 */
	public StringBuffer getBinaryValue(Node node, StringBuffer totalCount) {
		String leftCount = "0";
		String rightCount = "1";

		if (node == null) {
			System.out.println("Error getting binary value");
		}
		if ((node.getLeftChild() != null) && (node.getRightChild() != null)) {
			Node nextNode = node.getRightChild();
			totalCount = totalCount.append(leftCount);
			
			getBinaryValue(node.getLeftChild(), totalCount);
			totalCount.setLength(totalCount.length() - 1);
			totalCount.append(rightCount);
			
			getBinaryValue(nextNode, totalCount);
			totalCount.setLength(totalCount.length() - 1);
		}

		if ((node.getLeftChild() != null) && (node.getRightChild() == null)) {
			totalCount = totalCount.append(rightCount);
			getBinaryValue(node.getLeftChild().getLeftChild(), totalCount);
		}
		if ((node.getRightChild() != null) && (node.getLeftChild() == null)) {
			totalCount = totalCount.append(rightCount);

			getBinaryValue(node.getRightChild().getRightChild(), totalCount);
		}

		// Leaf node
		else if ((node.getLeftChild() == null) && (node.getRightChild() == null)) {
			String character = node.letter;
			String count = totalCount.toString();
			countMap.put(character, count);
			if(DEBUGBINARY == true) System.out.println(" " + character + "\t" + totalCount);

			return totalCount;
		}

		if (DEBUGBINARY == true){
			System.out.println("Hashmap entry");
			Iterator<Map.Entry<String, String>> entries = countMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String> entry = entries.next();
				if (DEBUGBINARY == true)
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
		}
		return totalCount;

	}

	/**
	 * Write to the encoded file. Include id, header, divider and the binary values for the characters.
	 * @param file2
	 */
	public void encodeFile(File file2) {	
		String divider = "/";
		String id = "Emma";
		
		//filewriter2.write(id);
		StringBuffer tempHeader = new StringBuffer("");
		StringBuffer header = buildHeader(node1, tempHeader);
		
		if(DEBUGHEADER == true) System.out.println(header.toString());
		filewriter2.write(header.toString());
		
		filewriter2.write(divider);
		
		//Write the character code for each letter
		int charCount = 0;
		String nextCharacter;
		String replacedChar;
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			while ((charCount = buffer.read()) != -1) {
				nextCharacter = Character.toString((char) charCount);
				if (countMap.containsKey(nextCharacter)) {
					replacedChar = countMap.get(nextCharacter);
					filewriter2.write(replacedChar);
					if (DEBUGENCODE == true)
						System.out.println(replacedChar);
				} else {
					System.out.println("error retrieving huffman code");
				}
			}
		} catch (IOException e) {
			System.out.println("Error retrieving huffman");
		}
		
		filewriter2.close();
		//node1 = Node.clearTree(node1);
	}

	/**
	 * Create the header
	 * Traverse the tree (pre-order) and write each node visited. 
	 * If it's a non-leaf, write 0
	 * If it's a leaf, write 1 and the character
	 * @param node
	 * @param tree
	 * @return
	 */
	public StringBuffer buildHeader(Node node, StringBuffer tree){
		String leafValue = "1";
		String nonleafVal = "0";
		if(node == null)
			return tree;
		else{
			if((node.getLeftChild()==null)&&(node.getRightChild()==null)){
				String leafChars = node.letter + leafValue; 
				tree.append(leafChars);
			}
			else if ((node.getLeftChild()!= null)|(node.getRightChild()!= null)){
				tree.append(nonleafVal);
			}
			if(node.getLeftChild()!= null)
				buildHeader(node.getLeftChild(), tree);
			if(node.getRightChild()!= null)
				buildHeader(node.getRightChild(), tree);
		}
		
		return tree;
	}


	public Map<String, String> getCountMap() {
		return countMap;
	}


	public Node getNode1() {
		return node1;
	}


	public void setNode1(Node node1) {
		this.node1 = node1;
	}

}
