import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class FileHelper {
	private String filePathUser, filePathPDF, filePathComment;
	
	FileHelper()
	{
		filePathUser = "C:\\Users\\Asus\\Quyet Chi\\4th year - first semester\\Java\\Digital Library\\DigitalLibrary\\ListReadedBooks\\";
		filePathPDF = "C:\\Users\\Asus\\Quyet Chi\\4th year - first semester\\Java\\Digital Library\\DigitalLibrary\\LinkPDF\\";
		filePathComment = "C:\\Users\\Asus\\Quyet Chi\\4th year - first semester\\Java\\Digital Library\\DigitalLibrary\\ListComments\\";
	}
	
	public boolean createUserFile(String filename)
	{
		filePathUser = filePathUser + filename;
		
		try{
			File file = new File(filePathUser);
			if(file.createNewFile())
			{
				System.out.println(filePathUser+" File Created");
				return true;
			}
			else return false;		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createCommentFile(String fileName)
	{		
		try{
			File file = new File(filePathComment + fileName);
			if(file.createNewFile())
			{
				System.out.println(filePathComment+" File Created");
				return true;
			}
			else return false;
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void fileToTextArea(String src, JTextArea ta)
	{
		try
        {
            FileReader reader = new FileReader(src);
            BufferedReader br = new BufferedReader(reader);
            ta.read( br, null );
            br.close();
            ta.requestFocus();
        }
        catch(Exception e) { 
        	e.printStackTrace();
        }
	}
	
	public boolean appendToFile(String src, String data)
	{
		try {
			File file = new File(src);
			data = data + "\n";
			// true = append file
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(data);
			bw.close();
			fw.close();
			System.out.println(data);
			System.out.println("wrote to " + src);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteFile(String src)
	{
		try {
			File file = new File(src);
			
			if(file.delete())
			{
				System.out.println("file deleted: " + src);
				return true;
			}
			else {
				System.out.println("delete fail!" + src);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getUserPath()
	{
		return filePathUser;
	}
	
	public String getBookPath()
	{
		return filePathPDF;
	}
	
	public String getCommentPath()
	{
		return filePathComment;
	}
	
	public void openPDF(String path) 
	{
		if (Desktop.isDesktopSupported()) {
		    try {
		        File myFile = new File(path);
		        Desktop.getDesktop().open(myFile);
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		}
	}
	
//	public static void main(String[] args) {
//		FileHelper fh = new FileHelper();
//		fh.deleteFile("C:\\Users\\Asus\\Quyet Chi\\4th year - first semester\\Java\\Digital Library\\DigitalLibrary\\ListComments\\test.txt");
//	}
	
}
