//reads input english sentences
import java.util.*;
import java.io.*;

public class ReadFile {
	public Scanner scr;
	String[] words;
	public void openFile(){							//opens the input text file
		try{
			scr = new Scanner(System.in);
			System.out.println("Enter the name of input file");
			String str = scr.nextLine();
			scr = new Scanner(new File(str));
		}
		catch(Exception e){
			System.out.println("File not found");
		}
	}
	
	public void readFile(){							//reads the input text file
			String line = scr.nextLine();
			words = line.split(" ");
			/*for(int i=0;i<words.length;i++)
				System.out.print(words[i]+" ");
			System.out.println("-------read---------");
		*/
	}
	
	public void closeFile(){						//close the input text file
		scr.close();
	}
}
