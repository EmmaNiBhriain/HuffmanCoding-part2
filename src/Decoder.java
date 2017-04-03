import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Decoder {
	//UserInterface gui = new UserInterface();
	Loader loader = new Loader();
	
	//FileWriter filewriter3 = new FileWriter();
	private Node currentNode = new Node(null, 0, null, null);
	private File decodedFile = new File("DecodedData.dat");
	
	private boolean leaf = false;
	
	private boolean DEBUGTREE = true;
	private boolean DEBUGNODE = false;

	
	public Decoder(){
		try {
			scanFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectFile(){
		//File file = gui.openFile();
	}

	public void scanFile() throws FileNotFoundException{
		//read each character in a file
		//split into header, tree, message
		//call treeBuilder
		File aFile = new File("EncodedData.dat");
		FileInputStream inByteStream = new FileInputStream(aFile);
		DataInputStream inDataStream = new DataInputStream(inByteStream);
		
		char nextCharacter = ' ';
		char decodedChar;
		boolean message = false;
		boolean tree = false;
		try {
			while (inDataStream.available()>0) {
				nextCharacter = (char) inDataStream.read();
				if((nextCharacter != '/')&&(message==false)){
					if(nextCharacter == '0'){
						Node markerNode = new Node("not leaf", 0, null, null);
						if(DEBUGNODE == true)System.out.println("not leaf");
						leaf = false;
						//traverse preorder
						currentNode = buildTree(currentNode, markerNode);	
						}
						
					else{
						int temp = (int) inDataStream.read();
						Node validNode = new Node(Character.toString(nextCharacter), 1, null, null);
						leaf = true;
						currentNode = buildTree(currentNode, validNode);
					}
				}
				
				if(nextCharacter == '/'){
					System.out.println("End of header");
					message = true;		
					if(DEBUGTREE == true) Node.print(currentNode, " ");
				}
				
				if(message == true){
					
					if((nextCharacter == '0')&&(currentNode.getLeftChild()!= null)){
						currentNode = currentNode.getLeftChild();
						System.out.println(currentNode.letter);
					}
					else if((nextCharacter == '0')&&(currentNode.getLeftChild()== null)){
						System.out.println(currentNode.letter);
						loader.filewriter3.writeToFile(decodedFile, currentNode.letter);

						currentNode = loader.getNode1().getLeftChild();
					}
					
					else if((nextCharacter == '1')&&(currentNode.getRightChild()!= null)){
						currentNode = currentNode.getRightChild();
						System.out.println(currentNode.letter);
					}
					else if((nextCharacter == '1')&&(currentNode.getRightChild()== null)){
						System.out.println(currentNode.letter);
						loader.filewriter3.writeToFile(decodedFile, currentNode.letter);

						currentNode = loader.getNode1().getRightChild();
					}
				}
				
				
				
			}
			System.out.println("File decoded successfully"); 
		}
	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Node buildTree(Node node, Node addedNode){
		System.out.println("adding a leaf");
		
		
		if(node.letter == null)
			node = addedNode;
		else{
			if((node.getLeftChild()!= null)&&(node.getRightChild()!=null)){
				if(node.getLeftChild().frequency != 1){
					buildTree(node.getLeftChild(), addedNode);
				}
				else if(node.getRightChild().frequency != 1){
					buildTree(node.getRightChild(), addedNode);
				}
			}
			if((node.getLeftChild()!= null)&&(node.getRightChild()==null))
				node.setRightChild(addedNode);
			
			if((node.getLeftChild()==null)&&(node.getRightChild()==null)){
				node.setLeftChild(addedNode);
			}
			if(node.getLeftChild()!= null){
				if(node.getLeftChild().frequency != 1)
					buildTree(node.getLeftChild(), addedNode);
			}
			
			
		}
		return node;

	}
	
	public void decodeMessage(){
		
	}
}
