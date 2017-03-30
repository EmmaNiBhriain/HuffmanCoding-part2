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
	private Node buildingNode = new Node("start", 0, null,null);
	private Node currentNode = new Node("start", 0, null, null);
	private File decodedFile = new File("DecodedData.dat");
	
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
		
		//BufferedReader buffer = new BufferedReader(new FileReader("EncodedData.dat"));		
		//int charIndex;// = inDataStream.read();
		char nextCharacter = ' ';
		char decodedChar;
		currentNode = loader.getNode1();
		boolean message = false;
		boolean tree = false;
		try {
			while (inDataStream.available()>0) {
				nextCharacter = (char) inDataStream.read();
				if((nextCharacter != '/')&&(message==false)){
					if(nextCharacter == '0'){
						System.out.println("not leaf");
						Node markerNode = new Node("not leaf", 0, null, null);
						if((buildingNode.getLeftChild()==null)&&(buildingNode.getRightChild()==null)){
							buildingNode.setLeftChild(markerNode);
						}
						else if((buildingNode.getLeftChild()!=null)&&(buildingNode.getRightChild()==null)){
							buildingNode.setRightChild(markerNode);
						}
						else if((buildingNode.getLeftChild()==null)&&(buildingNode.getRightChild()!=null)){
							buildingNode.setLeftChild(markerNode);
						}
						
					}
					else{
						int temp = (int) inDataStream.read();
						Node node = new Node(Character.toString(nextCharacter), 1, null, null);
						System.out.println(node.toString());
					}
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
				
				if(nextCharacter == '/'){
					System.out.println("End of header");
					message = true;					
				}
			}
			System.out.println("File decoded successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void treeBuilder(){
		//create a tree 
		Node node = new Node("root",0,null,null);
		//if(nextCharacter == '0');
	}
	
	public void decodeMessage(){
		
	}
}
