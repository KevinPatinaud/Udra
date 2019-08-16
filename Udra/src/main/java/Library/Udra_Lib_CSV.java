package Library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import udra.Udra;

public class Udra_Lib_CSV {

	
	public static boolean saveAsCSV( Udra udra_in , String URLFile , boolean deletePreviousFile , String ... defaultValue)
	{
		boolean FileExist;
		
		if ((new File(URLFile)).exists())
			FileExist = true;
		else
			FileExist = false;
		
		//If we want erease the previous file
		if (deletePreviousFile && FileExist)
		{
			(new File(URLFile)).delete();
			FileExist = false;
		}
		
		
		FileWriter writer = null;
		
		try{
	
			  writer = new FileWriter(URLFile, true); 
			
				//if the file doesn't exist we will create him and write the title
				if (!FileExist)
				{
					 for (int i = 0 ; i < udra_in.getTitle().size() ; i++) //print the title of each column
					 {
						 writer.write(udra_in.getTitle().get(i) + ";");
					 }
					 writer.write("\n");
				}
				
				
				
		//write the document
				 for (int row = 0 ; row < udra_in.sizeRow() ; row++) //print the content of table
				 {
					 for (int col = 0 ; col < udra_in.sizeColumn() ; col++)
					 {
						 if( udra_in.get(col, row) != null)
							 writer.write( udra_in.get(col, row).toString() );
						 else if (defaultValue.length > 0)
							 writer.write(defaultValue[0]);
						 
						 writer.write(";");
					 }
					 writer.write("\n");
				 }
				
				
				
				
				writer.close();	
					
		}catch(IOException ex){	}
		

		
		return true;
	}
	
	
	
	
	public static boolean createFromCSVFile(  Udra udra_in , String FileName )
	{
		BufferedReader Buffer = null;
	    String line;

	    ArrayList<String> textCSV = new ArrayList<String>();
	    
	    try
	      {
	    	Buffer = new BufferedReader(new FileReader(FileName));
	
	    while ((line = Buffer.readLine()) != null)
	    	textCSV.add(line);
	    Buffer.close();

	    
	      }
		    catch(FileNotFoundException exc)
		      {
		    	return false;
		      } catch (IOException e) {
				return false;
			}
		    
	    String[] na = FileName.split("\\\\");
	    String[] ext = na[(int)(na.length-1)].split(".csv");
	    
		return createFromCSV_ArrayString( udra_in ,textCSV , ext[0]);
	}
	
	
	
	
	
	public static boolean createFromCSV_ArrayString(  Udra udra_in , ArrayList<String> textCSV , String ... udraName)
	{
		//reset the array
		udra_in.clear();
		
		if ( udraName.length > 0 )
			udra_in.setName( udraName[0] );
		
		for (int i = 0 ; i < textCSV.size() ; i++) //read the array line after line
		{
			String text[] = textCSV.get(i).split(";"); //split the each case with ;
			
			ArrayList<String> Line = new ArrayList<String>(); //create a new line
			
			for (int j = 0 ; j < text.length ; j++) //add each new new case to the current line
			{
				Line.add(text[j].trim());
			}
			
			if ( udra_in.getTitle().isEmpty()) //add the first line to the titles
				udra_in.setTitle( Line );
			else
			{
				//if the current line have less case of numbers of column we add new case
				for (int k = Line.size() ; k < udra_in.getTitle().size() ; k++)
					Line.add("");
				try
				{
					udra_in.insertAnArrayList( Line );  //add the rest of line to the array
				}catch( Exception e){ }
			}
		}
		
		
		
		return true;	
	}
		
	
	
	public static void create_Multi_udra_from_CSV_Folder(  Udra udra_in , String Folder)
	{
		udra_in.insertAColumn("list");
		
		File Directory = new File(Folder);
		
		
		String [] listefichiers = Directory.list(); 
		
		
		
		for(int i=0;i<listefichiers.length;i++){ 	
			Udra Tempo = new Udra();
			//system Windows
			if ( Directory.getAbsolutePath().contains("\\"))
				Tempo.createFromCSVFile(Directory.getAbsolutePath() + "\\" + listefichiers[i]);
			
			//Linux system
			else if ( Directory.getAbsolutePath().contains("/"))
				Tempo.createFromCSVFile(Directory.getAbsolutePath() + "/" + listefichiers[i]);
			
			udra_in.insertALine(Tempo);
			
		}
			
	}
	
	public static void createFromCSVFolder(  Udra udra_in , String Folder) //create from CSV , this can content multiple CSV with different format
	{
		
		File Directory = new File(Folder);
		
		
		String [] listefichiers = Directory.list(); 
		
		
		
		for(int i=0;i<listefichiers.length;i++){ 	
			Udra Tempo = new Udra();
			
			if ( Directory.getAbsolutePath().contains("/") ) //LInux
				Tempo.createFromCSVFile(Directory.getAbsolutePath() + "/" + listefichiers[i]);
			else
				Tempo.createFromCSVFile(Directory.getAbsolutePath() + "\\" + listefichiers[i]);
			
			
		for (int k = 0 ; k < Tempo.sizeRow() ; k++)
		{

			udra_in.insertALine();
			
			for (int j = 0 ; j < Tempo.sizeColumn() ; j++)
			{

				if (!udra_in.getTitle().contains(Tempo.getTitle().get(j))) //test if a column with this name are already present
				{	
					//if it's false we add a new collumn
					udra_in.insertAColumn(Tempo.getTitle().get(j));
				}
				//we add the value
				udra_in.setAvalue(Tempo.getTitle().get(j), udra_in.sizeRow() - 1, Tempo.get(j, k));
				
				
			}
		}
		
		
		} 	
	
		
	}
	

	

	public static void createFromCSVFolderInDistinctUdra(  Udra udra_in , String Folder) //create from CSV , this can content multiple CSV with different format
	{

		udra_in.insertAColumn("File");
		
		File Directory = new File(Folder);
		
		
		String [] listefichiers = Directory.list(); 
		
		
		
		for(int i=0;i<listefichiers.length;i++){ 	
			Udra Tempo = new Udra();
			
			if ( Directory.getAbsolutePath().contains("/") ) //LInux
				Tempo.createFromCSVFile(Directory.getAbsolutePath() + "/" + listefichiers[i]);
			else
				Tempo.createFromCSVFile(Directory.getAbsolutePath() + "\\" + listefichiers[i]);
			
			
			Tempo.setName(listefichiers[i]);
			udra_in.insertALine(Tempo);
		
			
		} 	
	
		
	}




	public static void createFromCSV_HTTP_URL(Udra udra_in, String url) {
		try {
			

			createFromCSV_ArrayString(udra_in, udra_in.getData_from_Web_URL_ArrayListString(url), url);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
