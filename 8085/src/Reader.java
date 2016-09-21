//Main class

import java.util.*;
import java.io.*;

public class Reader {
	public static void main(String[] args) throws IOException{ 	//throws IOException
		ReadFile rf = new ReadFile();			//creates object to read input text file
		rf.openFile();
		
		while(rf.scr.hasNextLine()){        	//iterates for each line i.e. scans each line in 1 iteration
			rf.readFile();
			String[] mnemonics = rf.words;		//takes words of sentence from readFile() of rf object
			if(mnemonics[0].equalsIgnoreCase("terminate") || mnemonics[0].equalsIgnoreCase("end"))
				break;
			/*for(int i=0;i<mnemonics.length;i++)
				System.out.print(mnemonics[i]+" ");
			System.out.println("-----calling------");
			*/
			/*readExcel re = new readExcel();
			re.readXLSXFile();*/
			test tst = new test();
			tst.testFile();
			for(int i=0;i<tst.values.size();i++)
				System.out.println(Arrays.toString(tst.values.get(i)));
		}
		rf.closeFile();
		
	}
}
