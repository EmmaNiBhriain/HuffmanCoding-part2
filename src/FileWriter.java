import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter{
	
	File aFile = new File ("EncodedData.dat");
	DataOutputStream outDataStream = null;
	DataOutputStream outDataStream1 = null;
	FileOutputStream outByteStream = null;
	FileOutputStream outByteStream1 = null;
	public FileWriter(){
		try{
			outByteStream = new FileOutputStream(aFile);
			outDataStream = new DataOutputStream(outByteStream);
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	
	
	public  void write(String string){
		try {
			String newString = string.toString();
			outDataStream.writeBytes(newString);
		} catch (IOException e) {
			System.out.println("Error writing string");
		}
		
	}
	
	public void writeToFile(File file, String string){
		
		String newString = string;
		try {
			//boolean value means append instead of replace
			outByteStream1 = new FileOutputStream(file, true);
			outDataStream1 = new DataOutputStream(outByteStream1);

			outDataStream1.writeBytes(newString);
		} catch (IOException e) {
			System.out.println("error writitng to file");
		}
	}
	
	public void close(){
		try {
			outDataStream.close();
		} catch (IOException e) {
			System.out.println("Error closing stream");
		}
	}
	
	public void close1(){
		try {
			outDataStream1.close();
		} catch (IOException e) {
			System.out.println("Error closing stream");
		}
	}
}
