import java.util.*;
import java.io.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class test {
	List<String[]> values = new ArrayList<String[]>();
	public void testFile() throws IOException{
		FileInputStream file = new FileInputStream(new File("TableBook.xlsx"));
		
		//Get the workbook instance for XLS file 
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(5);
		for(Row r : sheet) {
		   Cell c = r.getCell(0);
		   if(c != null) {
			      if(c.getCellType() == Cell.CELL_TYPE_STRING) {
			         String temp = c.getStringCellValue();
			         String[] temparray = temp.split(",");
			         values.add(temparray);
			    	  //System.out.println(c.getStringCellValue());
			      } 
		      }
		 }
		
		file.close();
		workbook.close();
		FileOutputStream out = new FileOutputStream(new File("TableBook.xslx"));
		//workbook.write(out);
		out.close();
	}
}
