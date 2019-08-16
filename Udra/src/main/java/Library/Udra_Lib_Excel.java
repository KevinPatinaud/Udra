package Library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.DateUtil;

import udra.Udra;

public class Udra_Lib_Excel {


	
	public static boolean saveAsExcel( Udra in , String URLFile , boolean color , String ... defaultValue)
	{
		if (!color)
			return saveAsExcel( in ,  URLFile  ,  (short) 78 ,   (short) 78  , defaultValue);
		else
			return saveAsExcel( in , URLFile ,  (short) 53 ,   (short) 47  , defaultValue);
	}
	
	
	
	public static boolean saveAsExcel( Udra in  , String URLFile , short ColorHeader , short ColorBody , String ... defaultValue)
	{
		boolean FileExist;
		
		
		//erease the previous file
		
		if ((new File(URLFile)).exists())
		{
			(new File(URLFile)).delete();
			FileExist = false;
		}
		
	

		try {
		
		// create a new file
		FileOutputStream out = new FileOutputStream(URLFile);
	
		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		
		
		
		// create a new sheet
		Sheet s = wb.createSheet();
		
		// declare a row object reference
		Row r = null;
		
		// declare a cell object reference
		Cell c = null;
		

	    // create a row
	    r = s.createRow(0);
		
		 for (int i = 0 ; i < in.getTitle().size() ; i++) //print the title of each column
		 {
			   // create a numeric cell
		        c = r.createCell(i);
		        c.setCellValue(in.getTitle().get(i) );

				CellStyle cs = wb.createCellStyle();
				cs.setFillPattern((short) CellStyle.BORDER_THIN);
				cs.setFillForegroundColor(ColorHeader);
		        c.setCellStyle(cs);
		 }
		
		//write the document
		 for (int rownum = 0 ; rownum < in.sizeRow() ; rownum++) //print the content of table
		 {
			    r = s.createRow(rownum + 1);
			 for (int cellnum = 0 ; cellnum < in.sizeColumn() ; cellnum++)
			 {

			        c = r.createCell(cellnum);
			        
			        if (rownum % 2 == 0)
			        {
						CellStyle cs = wb.createCellStyle();
						cs.setFillPattern((short) CellStyle.BORDER_THIN);
						cs.setFillForegroundColor(ColorBody);
				        c.setCellStyle(cs);
			        }
				 
				 if( in.get(cellnum, rownum)  != null)
				    c.setCellValue(in.get(cellnum, rownum).toString()); 
				 
				 else if (defaultValue.length > 0) // if the value is null
					c.setCellValue(defaultValue[0]);
				
			 }
		 }


			wb.write(out);
			out.close();
		
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	
	
	

	
	public  static void create_Multi_udra_from_XLS_Folder(  Udra in , String Folder, int firstRow, int firstCell, int lastRow, int lastCell)
	{
		in.insertAColumn("list");
		
		File Directory = new File(Folder);
		
		
		String [] listefichiers = Directory.list(); 
		
		
		
		for(int i=0;i<listefichiers.length;i++){ 	
			Udra Tempo = new Udra();
			Tempo.createFromXLS(Directory.getAbsolutePath() + "\\" + listefichiers[i] , firstRow , firstCell , lastRow , lastCell );
			in.insertALine(Tempo);
			
		}
			
	}
	

	
	
	public static  void createFromXLS(  Udra in , String fileURL )
	{
		in.clear();
		createFromXLS( in , fileURL , 0 , 0 , -1 , -1 );
	}	
	
	
	public static void createFromXLS( Udra in , String fileURL , Object defaultValue)
	{
		createFromXLS( in , fileURL , 0 , 0 , -1 , -1 , defaultValue);
	}
	
	
	
	public static void createFromXLS( Udra udra_in , String fileURL , int FirstRow , int FirstCell , int LastRow , int LastCell , Object ... defaultValue)
	{
		
		try {

				 Workbook wb = WorkbookFactory.create(new File(fileURL));
				 
				for (int k = 0; k < wb.getNumberOfSheets(); k++) {
					Sheet sheet = wb.getSheetAt(k);
					
					
					int rows = sheet.getPhysicalNumberOfRows() + 1;
					
					if (LastRow > 0 && LastRow <= rows)
						rows = LastRow;
					
					
					for (int r = FirstRow; r < rows; r++) {
						Row row = sheet.getRow(r);
						
						if (row == null) 
							continue;
						
						int cells = row.getPhysicalNumberOfCells();
						

						ArrayList<Object> Line = new ArrayList<Object>(); //create a new line
						
						if (LastCell > 0 && LastCell <= cells)
							cells = LastCell;
						
						for (int c = FirstCell ; c < cells + FirstCell; c++) {
							
							Cell cell = row.getCell(c);
							Object value = null;

							if(cell == null)
							{
								if (defaultValue.length > 0)
									Line.add(defaultValue[0]);
								else
									Line.add(null);
							}
							else
							{
								switch (cell.getCellType()) {
	
									case HSSFCell.CELL_TYPE_FORMULA:
										value = cell.getCellFormula();
										break;
	
									case HSSFCell.CELL_TYPE_NUMERIC:
										value = cell.getNumericCellValue();
										//cas des dates
										if (HSSFDateUtil.isCellDateFormatted(cell)) {
											DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
											Date date = cell.getDateCellValue();
											 value = dateFormat.format(date); 
										}
										break;
	
									case HSSFCell.CELL_TYPE_STRING:
										value = cell.getStringCellValue();
										break;
	
									default:
								}


								
								
								if (value!= "")
									Line.add(value);
								else
									Line.add(null);
							}
							
						
						
							
						}
						

						if (udra_in.getTitle().isEmpty()) //add the first line to the titles
						{
							
							udra_in.setTitle( new ArrayList<String>() );
							
							for (int itTitle = 0 ; itTitle < Line.size() ; itTitle++ )
							{
								udra_in.getTitle().add(String.valueOf(Line.get(itTitle)));
							}
						}
							
							
						else
						{
							//if the current line have less case of numbers of column we add new case
							for (int NumCaseOnRow = Line.size() ; NumCaseOnRow < udra_in.getTitle().size() ; NumCaseOnRow++)
								Line.add(null);
							
							boolean LineFull = false;
							
							for (int itLine = 0 ; itLine < Line.size() ; itLine++)
							{
								if (Line.get(itLine) != null)
									LineFull = true;
							}
							
							
							if(LineFull)
								udra_in.insertAnArrayList( Line );  //add the rest of line to the array
						}
						
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
	
}
