import java.awt.Desktop;
import java.io.*;

public class pdfReader {
	String path;
	
	pdfReader(String path)
	{
		this.path = path;
	}

	public void open() 
	{
		if (Desktop.isDesktopSupported()) {
		    try {
		        File myFile = new File(this.path);
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		}
	}

}
