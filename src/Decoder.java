import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Decoder {
	//UserInterface gui = new UserInterface();
	Loader loader = new Loader();
	
	//FileWriter filewriter3 = new FileWriter();
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
		BufferedReader buffer = new BufferedReader(new FileReader("EncodedData.dat"));		
		int charIndex;// = inDataStream.read();
		char nextCharacter;
		char decodedChar;
		currentNode = loader.getNode1();
		boolean tree = false;
		try {
			while ((charIndex = buffer.read())!= -1) {
				nextCharacter = (char) charIndex;
				if((nextCharacter == '0')&&(currentNode.getLeftChild()!= null)){
					currentNode = currentNode.getLeftChild();
					//System.out.println(currentNode.letter);
				}
				else if((nextCharacter == '0')&&(currentNode.getLeftChild()== null)){
					System.out.println(currentNode.letter);
					loader.filewriter3.writeToFile(decodedFile, currentNode.letter);

					currentNode = loader.getNode1().getLeftChild();
				}
				
				else if((nextCharacter == '1')&&(currentNode.getRightChild()!= null)){
					currentNode = currentNode.getRightChild();
					//System.out.println(currentNode.letter);
				}
				else if((nextCharacter == '1')&&(currentNode.getRightChild()== null)){
					System.out.println(currentNode.letter);
					loader.filewriter3.writeToFile(decodedFile, currentNode.letter);

					currentNode = loader.getNode1().getRightChild();
				}
				/*else if((currentNode.getRightChild() == null)&&(currentNode.getLeftChild()==null)){
					System.out.println(currentNode.letter);
					currentNode = loader.getNode1();
				}*/
				
				
				/*if()//after the header){
					//treeBuilder();
					{Node node = new Node("root",0,null,null);
					if(nextCharacter == '0');
						node.setLeftChild(new Node("left", 0, nu));
				}*/
				
				/*if(nextCharacter == '/'){
					tree = true;
				}
				*/
				//does not reach this code
				/*while(tree == true){
					currentNode = loader.getNode1();
					if (nextCharacter == '0'){
						currentNode = currentNode.getLeftChild();
					}
					else if(nextCharacter == '1'){
						currentNode = currentNode.getRightChild();
					}
					else{
						System.out.println(nextCharacter);
						
					}
						
				}*/
			}
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
