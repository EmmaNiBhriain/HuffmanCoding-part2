import java.io.IOException;

public class Node implements Comparable<Node>{
	String letter;
	int frequency;
	private Node leftChild;
	private Node rightChild;
	private Boolean DEBUG=false;
	
	/**
	 * Constructor for the Node
	 * @param letter
	 * @param frequency
	 * @param leftChild
	 * @param rightChild
	 */
	public Node(String letter, int frequency, Node leftChild, Node rightChild){
		this.letter = letter;
		this.frequency = frequency;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	
	/**
	 * Add a new Node.
	 * If new Entry is bigger than the Node and there is no right child, add to the right tree.
	 * If the Node has a right child, consider the right child as a root and call method again. 
	 * 
	 * If the new entry is less than the root and there is no left child, add to the left tree.
	 * If the Node has a left child, consider the left child as a root.
	 * 
	 * If the new entry is equal to the root, examine the root's left and right child respectively.
	 * If null, add new entry here
	 * @param newEntry
	 */
	public void add(Node newEntry){
		if(this.letter == null){
			this.frequency = newEntry.frequency;
			this.letter = newEntry.letter;
			this.leftChild = newEntry.leftChild;
			this.rightChild = newEntry.rightChild;
			if(DEBUG == true)System.out.println("New root added");
		}

		else if(newEntry.compareTo(this)<0){
			if(this.rightChild == null){
				this.rightChild = newEntry;
				if(DEBUG == true)System.out.println(newEntry.letter + "added to right tree");
			}
			else{
				rightChild.add(newEntry);
			}
		}
		else if(newEntry.compareTo(this)>0){
			if(leftChild == null){
				this.leftChild = newEntry;
				if(DEBUG == true)System.out.println(newEntry.letter + newEntry.frequency + " added to left tree");
			}
			else{
				leftChild.add(newEntry);
			}
		}
		//Account for the fact that it could have the same frequency
		else if(newEntry.compareTo(this) == 0){
			if(this.leftChild == null){
				this.leftChild =  newEntry;
			}
			else if((this.leftChild != null)&&(this.rightChild ==null))
				this.rightChild = newEntry;
		}
		
	}
	
	/**
	 * Perform a post order traversal of the tree and remove each of its nodes
	 * @param args
	 * @throws IOException
	 */
	public static Node clearTree(Node node){
		if(node == null){
			return node;
		}
		clearTree(node.getLeftChild());
		clearTree(node.getRightChild());
		System.out.println(node.letter);
		node = null;
					
		return node;
	}
	
	/**
	 * Perform a Breadth First traversal of the tree and print each of the Nodes
	 * @param root
	 * @param indent
	 */
	public static void print(Node root, String indent) {
		if (root == null)
			return;
		System.out.println(" " + indent + root.letter);
		if ((root.getLeftChild() != null) && (root.getRightChild() == null))
			print(root.getLeftChild(), indent + " ");
		else if ((root.getLeftChild() != null) && (root.getRightChild() != null)) {
			Node right = root.getRightChild();
			print(root.getLeftChild(), indent + " ");
			print(right, indent + " ");
		}

		else if ((root.getRightChild() != null) && (root.getLeftChild() == null))
			print(root.getRightChild(), indent = " ");

		else if ((root.getRightChild() != null) && (root.getLeftChild() != null)) {
			Node left = root.getLeftChild();
			print(root.getRightChild(), indent = " ");
			print(left, indent + " ");
		}

	}	
	
	/**
	 * Getters and setters for the Node
	 */
	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	
	public String toString(){
		String statement = "This node's character is: " + letter;
		return statement;
	}


	/**
	 * Compares Nodes based on their frequency.
	 */
	@Override
	public int compareTo(Node node) {
		if (frequency > node.frequency)
			return 1;
		else if (frequency < node.frequency)
			return -1;
		else
			return 0;
	}

}
