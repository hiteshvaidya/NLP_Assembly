//reads excel file
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

public class readExcel {
	
	ArrayList<String[]> thesauresSet = new ArrayList<String[]>();
	
	public void readXLSXFile() throws IOException{
		/*try{
			InputStream Excelfileip = new FileInputStream("TableBook.xlsx");    //pass file path of excel workbook
			XSSFWorkbook wb = new XSSFWorkbook(Excelfileip);					//create object of workbook
			XSSFSheet thesaures = wb.getSheetAt(5);									//create object of required sheet number
			Row row;														//create variable for row
			Cell cell;
			
			Iterator<Row> rowIterator = thesaures.iterator();
			row = rowIterator.next();										//to avoid first line which includes titles
			while(rowIterator.hasNext())
			{												//iterates through each row
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();							//cell iterator
				cell = cellIterator.next();									//cell number 0
				String temp = cell.getStringCellValue();		//add set of keywords to string array
				String[] commandWords = temp.split(",");
				thesauresSet.add(commandWords);
				
				wb.close();
				Excelfileip.close();
				OutputStream out = new FileOutputStream("TableBook.xlsx");
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
		try {
					
					FileInputStream file = new FileInputStream(new File("TableBook.xlsx"));
					
					//Get the workbook instance for XLS file 
					XSSFWorkbook workbook = new XSSFWorkbook(file);
		
					//Get first sheet from the workbook
					XSSFSheet sheet = workbook.getSheetAt(5);
					
					//Iterate through each rows from first sheet
					Iterator<Row> rowIterator = sheet.iterator();
					while(rowIterator.hasNext()) {
						Row row = rowIterator.next();
						
						//For each row, iterate through each columns
						Iterator<Cell> cellIterator = row.cellIterator();
						while(cellIterator.hasNext()) {
							
							Cell cell = cellIterator.next();
							if(cell.getColumnIndex()>0)
								break;
							
							switch(cell.getCellType()) {
								case Cell.CELL_TYPE_BLANK: return;
								case Cell.CELL_TYPE_STRING: System.out.print(cell.getStringCellValue() + "\t\t");
															break;
							}
						}
						System.out.println();
					}
					file.close();
					workbook.close();
					FileOutputStream out = new FileOutputStream(new File("TableBook.xslx"));
					//workbook.write(out);
					out.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
	
	public void checkSubSet(){
		
	}
}
