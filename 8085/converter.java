
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Created by Omkar Dusane on 03-Sep-16.
 */

public class converter {


    public static void main(String[] pp) throws IOException {

        ArrayList<totalwala> totals = new ArrayList<>();
        ArrayList<row> rows = new ArrayList<>();

        rows.clear();
        totals.clear();

        System.out.println("STARTED");

        String excelFilePath = "p12.xlsx" ;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream) ;
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        boolean end = false;

        int k = -1;
        while (iterator.hasNext() && !end) {
            k++ ;
            Row nextRow = iterator.next();
            row r = new row() ;
            r.isFIve = false;
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            int i = 0;
            while (cellIterator.hasNext() && !end) {


                Cell cell = cellIterator.next();

                switch (cell.getCellType() ) {
                    case Cell.CELL_TYPE_STRING:
                      //  System.out.print(cell.getStringCellValue());
                        String cellval = cell.getStringCellValue() ;
                        if(cellval.contains("ENDOFFILE")){
                            end= true ;
                        }
                        if(i==0){
                            r.setName(cellval);
                        }
                        if(i==1){
                            r.setVattin(cellval);
                        }
                         break;
                    case Cell.CELL_TYPE_BOOLEAN:
                //        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        double cellDval = cell.getNumericCellValue() ;
                        if(i==2){
                            r.setCost(cellDval);
                            if(cellDval < 0 ){
                                r.setNegative(true);
                            }
                        }
                        if(i==3){
                            r.setVat(cellDval);
                        }
                        break;
                }
//                System.out.print(" - ");
                i++ ;
            }
            rows.add(k,r);
            addRow(r,totals);
                // one row read ends
        }
//


// for 5 percent

        excelFilePath = "p5.xlsx" ;
        inputStream = new FileInputStream(new File(excelFilePath));

        workbook = new XSSFWorkbook(inputStream) ;
        firstSheet = workbook.getSheetAt(0);
        iterator = firstSheet.iterator();

        end = false;

         k = -1;
        while (iterator.hasNext() && !end) {
            k++;
            Row nextRow = iterator.next();
            row r = new row();
            r.isFIve = true;
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            int i = 0;
            while (cellIterator.hasNext() && !end) {


                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        //  System.out.print(cell.getStringCellValue());
                        String cellval = cell.getStringCellValue();
                        if (cellval.contains("ENDOFFILE")) {
                            end = true;
                        }
                        if (i == 0) {
                            r.setName(cellval);
                        }
                        if (i == 1) {
                            r.setVattin(cellval);
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        //        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        double cellDval = cell.getNumericCellValue();
                        if (i == 2) {
                            r.setCost_five(cellDval);
                            if (cellDval < 0) {
                                r.setNegative_five(true);
                            }
                        }
                        if (i == 3) {
                            r.setVat_five(cellDval);
                        }
                        break;
                }
//                System.out.print(" - ");
                i++;
            }
            rows.add(k, r);
            addRow(r, totals);

        }



            // workbook.close();
        inputStream.close();
        System.out.println("\n ENDED with size : "+totals.size()+" Entries");

        System.out.println("\n NAME : COST : VAT : GTTL : NCOST : NVAT : GNTTL : TCOST " );


        // start writing :


        // write to new excel

        HSSFWorkbook workbookOUT = new HSSFWorkbook();
        HSSFSheet sheet = workbookOUT.createSheet("OUTPUT");

        int rn =0;

        Row row ;
        Cell cell;

        row = sheet.createRow(rn++);

        cell = row.createCell(0);
        cell.setCellValue("Number");
        cell = row.createCell(1);
        cell.setCellValue("Party NAme");
        cell = row.createCell(2);
        cell.setCellValue("Vat Tin");

        cell = row.createCell(3);
        cell.setCellValue("purchase 12.5% cost");
        cell = row.createCell(4);
        cell.setCellValue("purchase 12.5% vat");
        cell = row.createCell(5);
        cell.setCellValue("gross 12.5% ");

        cell = row.createCell(6);
        cell.setCellValue("purchase 12.5% cost return");
        cell = row.createCell(7);
        cell.setCellValue("purchase 12.5% vat return");
        cell = row.createCell(8);
        cell.setCellValue("gross purchase return 12.5%");

        cell = row.createCell(9);
        cell.setCellValue("NET PURCHASE 12.5% ");

        cell = row.createCell(10);
        cell.setCellValue("purchase 5% cost");
        cell = row.createCell(11);
        cell.setCellValue("purchase 5% vat");
        cell = row.createCell(12);
        cell.setCellValue("gross 5% ");

        cell = row.createCell(13);
        cell.setCellValue("purchase 5% cost return");
        cell = row.createCell(14);
        cell.setCellValue("purchase 5% vat return");
        cell = row.createCell(15);
        cell.setCellValue("gross purchase return 5%");

        cell = row.createCell(16);
        cell.setCellValue("NET PURCHASE 15% ");

        cell = row.createCell(17);
        cell.setCellValue("Grand final total");


        for ( totalwala tt :totals) {
            //row = sheet.createRow(rn++);
            System.out.println("\n "
                    +tt.name +" : "
                    +tt.vattin +" : "
                    // twelve
                    +tt.twelve_totalpositivecost +" : "
                    +tt.twelve_totalpositivevat +" : "
                    +tt.twelve_giveTotalPositive()+" : "
                    +tt.twelve_totalnegativecost +" : "
                    +tt.twelve_totalnegativevat +" : "
                    +tt.twelve_giveTotalNegative()+" : "
                    +tt.twelve_giveTotalOverAll()+" : "
                    // five
                    +tt.five_totalpositivecost +" : "
                    +tt.five_totalpositivevat +" : "
                    +tt.five_giveTotalPositive()+" : "
                    +tt.five_totalnegativecost +" : "
                    +tt.five_totalnegativevat +" : "
                    +tt.five_giveTotalNegative()+" : "
                    +tt.five_giveTotalOverAll()+" : "
                    // final
                    +"[ "+tt.givemeGRANDFINALFIVETWELVETOTAL()+" ]"
            );

            // write to excel

            row = sheet.createRow(rn++);

            cell = row.createCell(0);
            cell.setCellValue(rn );
            cell = row.createCell(1);
            cell.setCellValue(tt.name);
            cell = row.createCell(2);
            cell.setCellValue(tt.vattin);

            cell = row.createCell(3);
            cell.setCellValue(tt.twelve_totalpositivecost);
            cell = row.createCell(4);
            cell.setCellValue(tt.twelve_totalpositivevat );
            cell = row.createCell(5);
            cell.setCellValue(tt.twelve_giveTotalPositive());

            cell = row.createCell(6);
            cell.setCellValue(tt.twelve_totalnegativecost);
            cell = row.createCell(7);
            cell.setCellValue(tt.twelve_totalnegativevat);
            cell = row.createCell(8);
            cell.setCellValue(tt.twelve_giveTotalNegative() );

            cell = row.createCell(9);
            cell.setCellValue(tt.twelve_giveTotalOverAll() );

            cell = row.createCell(10);
            cell.setCellValue(tt.five_totalpositivecost );
            cell = row.createCell(11);
            cell.setCellValue(tt.five_totalnegativevat);
            cell = row.createCell(12);
            cell.setCellValue( tt.five_giveTotalPositive() );

            cell = row.createCell(13);
            cell.setCellValue(tt.five_totalnegativecost);
            cell = row.createCell(14);
            cell.setCellValue(tt.five_totalnegativevat);
            cell = row.createCell(15);
            cell.setCellValue(tt.five_giveTotalNegative());

            cell = row.createCell(16);
            cell.setCellValue(tt.five_giveTotalOverAll());

            cell = row.createCell(17);
            cell.setCellValue(tt.givemeGRANDFINALFIVETWELVETOTAL());

            // done looop
        }

        // write excel

        try {
            FileOutputStream out =
                    new FileOutputStream(new File("OUT.xls"));
            workbookOUT.write(out);
            out.close();
            System.out.println("Excel written successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addRow(row row,ArrayList<totalwala> t){
        int found =0;
        if(!row.isFIve)
        {
            for(int i=0;i<t.size() && found==0 ;i++)
            {
                if(   t.get(i).nameMatch(row.getName()) ){
                    if(!row.isNegative()){
                        t.get(i).twelve_addCostPOS(row.getCost());
                        t.get(i).twelve_addVatPOS(row.getVat());
                    }
                    else{
                        t.get(i).twelve_addCostNEG(row.getCost());
                        t.get(i).twelve_addVatNEG(row.getVat());
                    }
                    found ++;
                    break;
                }
            }
        }
        else
        {
            for(int i=0;i<t.size() && found==0 ;i++)
            {
                if(   t.get(i).nameMatch(row.getName()) ){
                    if(!row.isNegative_five()){
                        t.get(i).five_addCostPOS(row.getCost_five());
                        t.get(i).five_addVatPOS(row.getVat_five());
                    }
                    else{
                        t.get(i).five_addCostNEG(row.getCost_five());
                        t.get(i).five_addVatNEG(row.getVat_five());
                    }
                    found ++;
                    break;
                }
            }
        }
        if (found == 0) {
            t.add(new totalwala(row.name,row.vattin));
            addRow(row,t);
        }

    }





}
