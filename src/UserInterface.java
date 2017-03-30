import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

public class UserInterface {
	private File aFile;

	private FileDialog aFileDialog;
	private Frame aFrame = new Frame("Emma's Frame");
	
	public UserInterface(){
		//aFile = openFile();
	}
	
	public File openFile(){
		aFileDialog = new FileDialog(aFrame, "Open File For Compression", FileDialog.LOAD);
		aFileDialog.setVisible(true);
		String aFileName = aFileDialog.getFile();
		File aFile = new File(aFileName);
		return aFile;
	}

	public void saveFile(){
		aFileDialog = new FileDialog(aFrame, "Save As", FileDialog.SAVE);
		aFileDialog.setVisible(true);
	}
	
	public File getaFile() {
		return aFile;
	}

	public void setaFile(File aFile) {
		this.aFile = aFile;
	}
	
	
}
