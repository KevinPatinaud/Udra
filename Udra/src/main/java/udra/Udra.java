package udra;
import java.awt.Color;
import java.io.PrintWriter;
import java.util.ArrayList;

import BMP.FrameBMP;
import BMP.Udra_BMP;
import Library.Udra_Lib_BDD;
import Library.Udra_Lib_CSV;
import Library.Udra_Lib_Display;
import Library.Udra_Lib_Display_modif;
import Library.Udra_Lib_Excel;
import Library.Udra_Lib_Internet;
import Library.Udra_Lib_JSON;
import Library.Udra_Lib_Mail;
import Library.Udra_Lib_SOAP;
import Library.Udra_Lib_XML;
import graphique.Udra_Pie_Panel;
import graphique.Udra_Square_Panel;
import graphique.Drawing_Line_Udra;
import graphique.Drawing_Pie_Udra;
import graphique.Drawing_Relief_Map;
import graphique.Drawing_Square_Udra;


/**
 * @author Kevin Patinaud
 *
 * Signification :
 * UDRA pour Unique Data Representation & Analyse fait rï¿½fï¿½rence au type de gestion commune des donnï¿½es.
 * Udra signifie ï¿½galement ï¿½loutreï¿½ en lituanien
 *
 */
public class Udra {

	/**
	 * 
	 */
	private ArrayList<String> Title;
	/**
	 * 
	 */
	private ArrayList<ArrayList> contentTable;
	/**
	 * 
	 */
	private String Name = "";
	
	/**
	 * create an empty Udra
	 */
	public Udra() //default constructor
	{
		Title = new ArrayList<String>();
		contentTable = new ArrayList<ArrayList> ();
	}
	
	/**
	 * Give a way to copy only the title of an Udra without copying each data 
	 * @param titleArray
	 */
	public Udra(ArrayList<String> titleArray)
	{
		Title = new ArrayList<String>();
		
		for (String tit : titleArray)
			Title.add(tit);
		
		contentTable = new ArrayList<ArrayList> ();
	}

	/**
	 * @param TitleArray set Title of each column into an ArrayList
	 */
	public Udra(String ... TitleArray) //This constructeur take titles in attribute
	{
		Title = new ArrayList<String>();
		
		for (String Tit : TitleArray)
			Title.add(Tit);
	
		contentTable = new ArrayList<ArrayList> ();
	}
	

	/**
	 * @param width set the number of column
	 * @param height set the number of row
	 */
	public Udra(int width , int height) //This constructeur take titles in attribute
	{
		Title = new ArrayList<String>();
		
		for (int i = 0 ; i < width ; i++)
			Title.add(String.valueOf(i));
	
		contentTable = new ArrayList<ArrayList> ();
		
		for (int i = 0 ; i < height ; i++)
			this.insertALine();
		
	}
	
	
    /**
     * @param NmbOfColumn set the number of column
     */
    public Udra(int NmbOfColumn) //This constructeur take titles in attribute
    {
        Title = new ArrayList<String>();
        
        for (int i = 0 ; i < NmbOfColumn ; i++)
            Title.add( String.valueOf(i));
    
        contentTable = new ArrayList<ArrayList> ();
    }

	
	
	
	
	

	


	
	
	
	

	
	
////////////////////////////////////////////// constructor /////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * @param Input set the Udra to copy
	 * @return the new Udra ( perfect copy of the old )
	 */
	public static Udra copyFrom(Udra Input)
	{
		
		Udra newUdra = new Udra();
		newUdra.setName(Input.getName());
		
		//copy title
		for (int i = 0 ; i < Input.sizeColumn() ; i++)
		{
			newUdra.Title.add(Input.getTitle().get(i) );
		}
		
		//copy values
		for (int line = 0 ; line < Input.sizeRow() ; line++)
		{
			newUdra.contentTable.add(new ArrayList<ArrayList>());
			for (int column = 0 ; column < Input.sizeColumn() ; column++)
			{
			//	System.out.println(Input.get(column ,line).getClass().toString());
				if (Udra.class.isInstance(Input.get(column ,line)))
				{
			//		System.out.println("copie udra");
					newUdra.contentTable.get(line).add(Udra.copyFrom((Udra) Input.get(column ,line)));
				}
				else	
					newUdra.contentTable.get(line).add(Input.get(column ,line));
			}
		}
		
		return newUdra;
	}
	
	
	
	
	///////////////////////////////////////////////// General Function /////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * @param title set the title of each column
	 */
	public void setTitle( ArrayList<String> title)
	{
		Title = title;
	}
	
	/**
	 * @param name set the name of the Udra
	 */
	public void setName(String name)
	{
		Name = name;
	}
	
	/**
	 * @return the name of the Udra
	 */
	public String getName()
	{
		return Name;
	}
	

/**
 * Reverse all row ( first row become the last ) 
 */
public void reverse()
{
	Udra copy = Udra.copyFrom(this);
	
	
	for (int i = copy.sizeRow() - 1 ; i >= 0 ; i--)
	{
		for (int j = 0 ;j < copy.sizeColumn() ; j++)
		{
			this.setAvalue( j,copy.sizeRow() - i - 1, copy.get(j, i) ); 
		}
	}
	
}
	
/**
 * @param column select the column to search
 * @param last set the old value to replace
 * @param new_object set the new value
 * Replace all selected value by the new in the column
 */
public void replace_all_value_in_column( String column , Object last , Object new_object)
{
	replace_all_value_in_column(get_the_index_of_title_from_his_Name(column), last, new_object);
}


/**
 * @param column select the column to search
 * @param last set the old value to replace
 * @param new_object set the new value
 * Replace all selected value by the new in the column
 */
public void replace_all_value_in_column( int column , Object last , Object new_object)
{
	
	for (int i = 0  ;  i < sizeRow() ; i++)
	{
		if ( get(column, i) == null && last == null )
		{
			setAvalue(column, i, new_object);
		}
		
		if ( get(column, i) != null && get(column, i).toString().equals(last.toString()))
		{
			setAvalue(column, i, new_object);
		}
	}
	
}

//permet de copier une ligne dans une npuvel Udra (la laisse quand meme dans l'Udra d'origine)	
public Udra copyLine(int line)
{
	Udra result = new Udra(getTitle());
	result.insertALine();
	
	for (int i = 0; i < sizeColumn() ; i ++ )
	{
		result.setAvalue(i, 0, get(i , line));
	}
	
	
	return result;
}

	
	// permet d'envoyer des requï¿½tes sur les titre des colonnes.
	// exemple :
	// INSERT INTO (...) Values('[Titre1]' , '[Titre2]')
	// donnera
	// INSERT INTO (...) Values('a' , 'a')
	// INSERT INTO (...) Values('b' , 'b')
	// Cette mï¿½thode ne nettoye pas les donnï¿½es
	// Une seconde plus adaptï¿½es aux requï¿½te SQL (avec formatage des donnï¿½es) se trouve dans la section SQL
	/**
	 * Send request to format data on column for each line
	 * They symbols [ and ] indicate the name of the column to use
	 * example :
	 * INSERT INTO (...) Values('[Titre1]' , '[Titre2]')
	 * will do
	 * INSERT INTO (...) Values('a' , 'a')
	 * INSERT INTO (...) Values('b' , 'b')
	 * This method doesn't change data
	 * A next method (with formating of data ) is used for SQL request
	 * 
	 * @param Request request to format data
	 * @param row the number of the line to convert
	 * @return The formated request
	 */
	public String replaceRequest(String Request, int row) 
	{

		
		String [] TabReq = Request.split("\\[|\\]");
		
		
		String RequestReformed = ""; 
		for (int i = 0 ; i < TabReq.length ; i++ )
		{
			
			if (i%2 == 0)
				RequestReformed = RequestReformed + TabReq[i];
			else
			{
				if (get( TabReq[i] , row) != null)
					RequestReformed = RequestReformed + get( TabReq[i] , row).toString() ;
			}
				
			
		}
		
		
		return RequestReformed;
	}
	
	
	
	
	
	
	///////////////////////////////////// Function of udra ////////////////////////////////////////////////////////////////////////////////////////

	// basical action
	
	/**
	 * Clear the udra
	 */
	public void clear()
	{
		//reinitialise all
		Title = new ArrayList<String>();
		contentTable = new ArrayList<ArrayList> ();
		Name = "";
	}
	

	//test if the data can be convert in double
	/**
	 * @param ColumnName Name of the column to test
	 * @param RowNumber number of the row to test
	 * @return true if the object present in this case is a number or false if it isn't
	 */
	public boolean isNumber(String ColumnName , int RowNumber)
	{
		return isNumber(get_the_index_of_title_from_his_Name( ColumnName ) , RowNumber );
	}
	

	//test if the data can be convert in double
	/**
	 * @param ColumnName Number of the column to test
	 * @param RowNumber number of the row to test
	 * @return true if the object present in this case is a number or false if it isn't
	 */
	public boolean isNumber(int ColumnName , int RowNumber)
	{
		return thisObjectIsANumber(get(ColumnName , RowNumber));
	}
	
	
	
	
	/**
	 * Test if the object given in parameter is a number
	 * @param test is the value to test
	 * @return true if is a number or false
	 */
	private boolean thisObjectIsANumber(Object test)
	{
		try
		{
			double a = (Double) test;
			return true;
		}catch(Exception e) {	}
		
		boolean result = false;
		if(test != null)
		{
			try{
				Double.valueOf(test.toString().replace(",", ".").replace(" ", ""));
				 result = true;
			 } catch (NumberFormatException nfe) { }
		}
		return result;
	}
	
	
		
		
	
	/**
	 * Get the value present value in the specified case like a number
	 * @param ColumnName the name of the column to test
	 * @param RowNumber The number of the line to test
	 * @return a Double Object
	 */
	public Double getDbl(String ColumnName , int RowNumber)
	{
		return getDbl(get_the_index_of_title_from_his_Name(ColumnName) , RowNumber);
	}
	
	
	/**
	 * Get the value present value in the specified case like a number
	 * @param ColumnName The number of the column to test
	 * @param RowNumber The number of the line to test
	 * @return a Double Object
	 */
	public Double getDbl(int ColumnName , int RowNumber)
	{
		try 
		{
			return (Double) get(ColumnName , RowNumber);
		}catch(Exception e) {}
		
		if(isNumber(ColumnName , RowNumber))
			if(get(ColumnName , RowNumber) != null)
				return Double.valueOf(get(ColumnName , RowNumber).toString().replace(",", ".").replace(" ", "")) ;
		return null;
	}
	
	
	
	public String getString(String ColumnName , int RowNumber)
	{
		return getString(get_the_index_of_title_from_his_Name(ColumnName) , RowNumber);
	}
	
	
	
	public String getString(int ColumnName , int RowNumber)
	{
		if (get(ColumnName , RowNumber) == null)
			return null;
		return get(ColumnName , RowNumber).toString();
	}


	
	
	
	public Boolean getBoolean(String ColumnName , int RowNumber)
	{
		return getBoolean(get_the_index_of_title_from_his_Name(ColumnName) , RowNumber);
	}
	
	
	
	public Boolean getBoolean(int ColumnName , int RowNumber)
	{
		
		if ( get(ColumnName , RowNumber) == null)
			return null;
		
		//essais de retourner directement un boolean
		try
		{
			return (boolean) get(ColumnName , RowNumber);
		}catch(Exception e) {}
		try
		{
			return (Boolean) get(ColumnName , RowNumber);
		}catch(Exception e) {}
		
		//sinon essais de récuperer a partir d'un string
		if (getString(ColumnName , RowNumber) == null)
			return null;
		
		String val = getString(ColumnName , RowNumber).toUpperCase();
		
		Boolean result = null;
		if (val.equalsIgnoreCase("TRUE"))
			result = true;
		if  (val.equalsIgnoreCase("FALSE"))
			result = false;
		
		return  result;
	}

	
	
		
		//test if the data is a multi number
		/**
		 * @param ColumnName
		 * @param RowNumber
		 * @return
		 */
		public boolean isMultiNumber(String  ColumnName , int RowNumber)
		{
			return isMultiNumber( get_the_index_of_title_from_his_Name(ColumnName) , RowNumber );
		}
		
		/**
		 * @param ColumnName
		 * @param RowNumber
		 * @return
		 */
		public boolean isMultiNumber(int ColumnName , int RowNumber)
		{
			if (get(ColumnName , RowNumber) == null)
				return false;
			
			String str = get(ColumnName , RowNumber).toString();
			return str.contains(";");
			
		}

		 
	
		/**
		 * @param ColumnNumber
		 * @param RowNumber
		 * @param numberSelected
		 * @return
		 */
		public Double getDblFromMulti(String ColumnNumber , int RowNumber , int numberSelected)
		{	 
			return getDblFromMulti(get_the_index_of_title_from_his_Name(ColumnNumber) , RowNumber , numberSelected);
		}
	
		
		/**
		 * @param ColumnNumber
		 * @param RowNumber
		 * @param numberSelected
		 * @return
		 */
		public Double getDblFromMulti(int ColumnNumber , int RowNumber , int numberSelected)
		{
			Double result = null;
		
				try
				{
					result = Double.valueOf(get(ColumnNumber , RowNumber).toString().split(";")[numberSelected].replaceAll(",", "."));
				}
				catch(Exception e){}
			
			return result;
		
		}
	
	

		
		
		
		/**
		 * @param ColumnNumber
		 * @param RowNumber
		 * @return
		 */
		public Udra getUdra(int ColumnNumber , int RowNumber) //select a case with her number
		{
			return (Udra) contentTable.get(RowNumber).get(ColumnNumber);
		}
		
		
		
		/**
		 * @param ColumnName
		 * @param RowNumber
		 * @return
		 */
		public Udra getUdra(String ColumnName , int RowNumber)  //select a case
		{
			int NumColumn = -1;
			
			for (int i = 0 ; i < Title.size() ; i++  )
				if (ColumnName.equals(Title.get(i)))
					NumColumn = i;
			
			if (NumColumn == -1) //check the result
			{
					try {
						throw new Exception("The colomn \"" + ColumnName + "\" doesn't exist");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return (Udra) contentTable.get(RowNumber).get(NumColumn);
		}

		
		
		
		/**
		 * @param ColumnNumber
		 * @param RowNumber
		 * @return
		 */
		public Object get(int ColumnNumber , int RowNumber) //select a case with her number
		{
			return contentTable.get(RowNumber).get(ColumnNumber);
		}
		
		
		
		/**
		 * @param ColumnName
		 * @param RowNumber
		 * @return
		 */
		public Object get(String ColumnName , int RowNumber)  //select a case
		{
			int NumColumn = -1;
			
			for (int i = 0 ; i < Title.size() ; i++  )
				if (ColumnName.equals(Title.get(i)))
					NumColumn = i;
			
			if (NumColumn == -1) //check the result
			{
					try {
						throw new Exception("The colomn \"" + ColumnName + "\" doesn't exist");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return contentTable.get(RowNumber).get(NumColumn);
		}
		
	

	/**
	 * @param ColumnNumber
	 * @param RowNumber
	 * @param value
	 */
	public void setAvalue(int ColumnNumber , int RowNumber , Object value) //set a value to a case
	{
		contentTable.get(RowNumber).set(ColumnNumber, value);
		
	}
	
	
	
	/**
	 * @param ColumnName
	 * @param RowNumber
	 * @param value
	 */
	public void setAvalue(String ColumnName , int RowNumber , Object value) //set a value to a case
	{
		int NumColumn = -1;
		
		for (int i = 0 ; i < Title.size() ; i++  )
			if (ColumnName.equals(Title.get(i)))
				NumColumn = i;

		contentTable.get(RowNumber).set(NumColumn, value);
		
	}
	

	
	/**
	 * @return
	 */
	public int sizeRow()
	{
		return contentTable.size();
	}
	
	
	
	/**
	 * @return
	 */
	public int sizeColumn()
	{
		return Title.size();
	}
	
	
	
	/**
	 * @return
	 */
	public ArrayList<String> getTitle ()
	{
		return Title;
	}
	
	
	
	/**
	 * @param Original
	 * @param New
	 */
	public void changeTitle (String Original , String New)
	{
		for (int i = 0 ; i < sizeColumn() ; i++)
		{
			if (Title.get(i).equals(Original))
				Title.set(i, New);
		}
		
	}
	
	
	/**
	 * 
	 */
	public void deleteEmptyRow()
	{
		for (int i = 0 ; i < sizeRow() ; i++)
		{
			boolean empty = true;
			for (int j = 0 ; j < sizeColumn() ; j++)
			{
				if (get( j , i) != null)
					if (!get( j , i).toString().equals(""))
						empty = false;
			}	
			
			if (empty)
				delete_row(i--);
		}
		
	}
	
	
	public void dropLinesPrct( double prct)
	{
		//jusqu'à 50% de suppression on compte le nombre de ligne à garder
		if ( prct < 0.5)
		{
			int nmbLineGarde = (int) ( 1 / prct);
			
			for ( int i = sizeRow() - 1; i >= 0; i--)
			{
				if(i % nmbLineGarde == 1)
					delete_row(i);
			}
		}
		
		//au dela de 50% de suppression on compte les lignes à supprimer
		else if ( prct <= 1)
		{
			int nbrLineSuppr = (int) (1 / ( 1 - prct));
			
			for ( int i = sizeRow() - 1; i >= 0; i--)
			{
				if(i % nbrLineSuppr != 1)
					delete_row(i);
			}
		}
	}
	
	

	/**
	 * @param idTitre
	 */
	public void delete_duplicate_row(String ... idTitre )
	{
		if(idTitre.length > 0)
			delete_duplicate_row(false  ,  idTitre );
		else
			delete_duplicate_row(false);
	}
	
	
	
	/**
	 * @param RespectCasse
	 * @param Titre
	 */
	public void delete_duplicate_row(boolean RespectCasse , String ... Titre )
	{
		//On determine le parcour en largeur des colonnes
		Integer[] weidth = null; //si aucun titre n'est passer en paramï¿½tre on prend le udra dans toutes sa largeur
		if (Titre.length == 0)
		{
			weidth = new Integer[sizeColumn()];
			
			for (int i = 0 ; i < sizeColumn() ; i++)
				weidth[i] = i;
		}
		else //si les titres sont dï¿½finis on indique le numï¿½ro des colonnes ï¿½ prendre
		{
			weidth = new Integer[Titre.length];
			
			for (int i = 0 ; i < Titre.length ; i++)
			{
				weidth[i] = Title.indexOf(Titre[i]);
			}
		}
		
		
		for (int i = 0 ; i < contentTable.size()  ; i ++)
		{
			//control if the line i isn't null
			boolean line_i_is_null = true;
			for (int itCol = 0 ; itCol < sizeColumn() ; itCol++ )
			{
				if(get(itCol , i) != null)
					line_i_is_null = false;
			}
			if (line_i_is_null == true) //if the line is null we delete them
			{
				delete_row(i);
				i--;
				continue;
			}
				
			
			
			
			for (int j = i + 1 ; j < contentTable.size() ; j++)
			{
				//by default we considerate the array havn't duplicate row
				boolean AllDataIsTheSame = true;
				
				
				for (int itCol = 0 ; itCol < weidth.length ; itCol++)
				{

					if(get(weidth[itCol] , i) != null && get(weidth[itCol] , j) != null)
					{
						if(!RespectCasse )
						{
							if (! get(weidth[itCol] , i).toString().toUpperCase().equals(get(weidth[itCol], j).toString().toUpperCase()))
							{
								//if a non dupplicate data is found, we change the status of the response
								AllDataIsTheSame = false;
							}
						}
						else
						{
							if (! get(weidth[itCol] , i).equals(get(weidth[itCol], j)))
							{
								AllDataIsTheSame = false;
							}
						}
					}
					if(get(weidth[itCol] , i) == null && get(weidth[itCol] , j) != null)
						AllDataIsTheSame = false;
					if(get(weidth[itCol] , i) != null && get(weidth[itCol] , j) == null)
						AllDataIsTheSame = false;
					
				}
				
			//if all data is the same we delete the j line
				if (AllDataIsTheSame )
				{
					delete_row(j);
					j--;
				}
			}
			
				
		}
	
	}
	
	
	/**
	 * @param line_1
	 * @param line_2
	 */
	public void switch_line(int line_1 , int line_2)
	{
		ArrayList<Object> line1 = contentTable.get(line_1);
		contentTable.set(line_1, contentTable.get(line_2));
		contentTable.set(line_2, line1);
	}
	
	/**
	 * @param Name
	 * @return
	 */
	public int get_the_index_of_title_from_his_Name(String Name)
	{
		return Title.indexOf(Name);
	}
	


	//Work only with numeric value
	/**
	 * @param col
	 */
	public void orderAscBy(String col)
	{
		orderAscBy(get_the_index_of_title_from_his_Name(col));
	}
	
	
	
	/**
	 * @param col
	 */
	public void orderAscBy(   int col)
	{
		quickSort( 0 , sizeRow() - 1, col );
	}
	
	
	
	
	
	//Work only with numeric value
	/**
	 * @param lowerIndex
	 * @param higherIndex
	 * @param col
	 */
	public void quickSort( int lowerIndex , int higherIndex ,  int col)
	{
	        int i = lowerIndex;
	        int j = higherIndex;
	        
	        // calculate pivot number, I am taking pivot as middle index number
	        double pivot = getDbl(col, lowerIndex+(higherIndex-lowerIndex)/2 );
	        
	        // Divide into two arrays
	        while (i <= j) {
	            /**
	             * In each iteration, we will identify a number from left side which
	             * is greater then the pivot value, and also we will identify a number
	             * from right side which is less then the pivot value. Once the search
	             * is done, then we exchange both numbers.
	             */
	            while ( getDbl( col , i) < pivot) {
	                i++;
	            }
	            while ( getDbl( col , j) > pivot) {
	                j--;
	            }
	            if (i <= j) {
	            	switch_line(i, j);
	                //move index to next position on both sides
	                i++;
	                j--;
	            }
	        }
	        // call quickSort() method recursively
	        if (lowerIndex < j)
	            quickSort(lowerIndex, j , col);
	        if (i < higherIndex)
	            quickSort(i, higherIndex , col);
	
		
	}

	

	//Work only with numeric value
	/**
	 * @param col
	 */
	public void orderDescBy(String col)
	{
		orderDescBy(get_the_index_of_title_from_his_Name(col));
	}
	
	
	
	//Work only with numeric value
	/**
	 * @param col
	 */
	public void orderDescBy(int col)
	{
		quickSort( 0 , sizeRow() - 1, col );
		reverse();
	}

	

	
	/**
	 * @param newVal
	 */
	public void replaceAllNullValueBy(Object newVal)
	{
		for (int i = 0 ; i < sizeColumn() ; i++)
			for (int j = 0 ; j < sizeRow() ;j++)
				if(get(i, j) == null)
					setAvalue(i, j, newVal);
	}
	

	
	/**
	 * @param oldValue
	 * @param newVal
	 */
	public void replaceAllValueBy(Number oldValue , Object newVal)
	{
		for (int i = 0 ; i < sizeColumn() ; i++)
			for (int j = 0 ; j < sizeRow() ;j++)
				if(isNumber(i, j))
					if(getDbl(i, j) == oldValue.floatValue())
						setAvalue(i, j, newVal);
	}
	
	/**
	 * @param oldValue
	 * @param newVal
	 */
	public void replaceAllValueBy(String oldValue , Object newVal)
	{
		for (int i = 0 ; i < sizeColumn() ; i++)
			for (int j = 0 ; j < sizeRow() ;j++)
				if(get(i, j).toString().equals(oldValue))
					setAvalue(i, j, newVal);
	}
	

	
	/**
	 * @param oldValue
	 * @param newVal
	 */
	public void replaceAllValueBy(Object oldValue , Object newVal)
	{
		for (int i = 0 ; i < sizeColumn() ; i++)
			for (int j = 0 ; j < sizeRow() ;j++)
				if(get(i, j) == oldValue)
					setAvalue(i, j, newVal);
	}
	
	
	
	
	/**
	 * @param indexColumn
	 * @param Value
	 */
	public void selectWhere(String indexColumn , Object Value)
	{
		selectWhere(this.get_the_index_of_title_from_his_Name(indexColumn) , Value);
	}
	
	
	
	/**
	 * @param indexColumn
	 * @param Value
	 */
	public void selectWhere(int indexColumn , Object Value)
	{
		int i = 0;
		while ( i < sizeRow())
		{
			if(get(indexColumn, i).equals(Value))
				i++;
			else
				delete_row(i);
		}
				
	
	}
	


	//count the number of each occurence in a column
	/**
	 * @param index_column
	 * @return
	 */
	public Udra countOccurence( String index_column )
	{
		return countOccurence( get_the_index_of_title_from_his_Name(index_column) );
	}
	

    /**
     * @param index_column
     * @return
     */
    public Udra countOccurence( int index_column )
    {
          Udra result = new Udra ( "occurence" , "number" , "frequency in %");
          result.setName("Occurence of " + getTitle().get(index_column) );
         
          for (int it = 0 ; it < sizeRow() ; it++)
          {
                 int index_occurence = result.find ( "occurence" , get(index_column , it) );
                 if ( index_occurence == -1 )
                 {
                        result.insertALine(get(index_column , it) , 1 , 0);
                 }
                 else
                 {
                        result.setAvalue("number" , index_occurence, result.getDbl("number", index_occurence) + 1);
                 }
                
                
          }
         
         
         
          double total_of_occurence = sizeRow();
         
          for (int i = 0 ; i < result.sizeRow() ; i++)
          {
                 result.setAvalue("frequency in %" , i, result.getDbl("number", i)  / total_of_occurence);
          }
         
         
         
          return result;
    }
   

	
	
	/**
	 * Retrouve le position d'un objet (String, int, ...) passe en parametre sinon retourne -1
	 * @param index_column
	 * @param value
	 * @return
	 */
	public int find ( String index_column , Object value)
	{
		return find( get_the_index_of_title_from_his_Name(index_column) , value );
	}
		
	/**
	 *  Retrouve le position d'un objet (String, int, ...) passe en parametre sinon retourne -1
	 * @param index_column
	 * @param value
	 * @return
	 */
	public int find ( int index_column , Object value)
	{
		
		for (int i = 0 ; i < sizeRow() ; i++)
		{
			if ( get( index_column , i ).toString().equals(value) )
				return i;

			
		}


		return -1;
	}
	
	

	/**
	 * @param index_column
	 * @return
	 */
	public boolean column_Contains_Only_Number( String index_column )
	{
		return column_Contains_Only_Number( get_the_index_of_title_from_his_Name(index_column));
	}
	
	/**
	 * @param index_column
	 * @return
	 */
	public boolean column_Contains_Only_Number( int index_column )
	{
		boolean res = true;
		
		for (int i = 0 ; i < sizeRow() ; i++)
			if ( ! isNumber(index_column, i))
				return false;
		
		return res;
		
	}
	
	
	// *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH *** MATH ***
	/**
	 * @param colonne
	 * @return
	 */
	public double getMedian( String colonne )
	{
		return getMedian( get_the_index_of_title_from_his_Name(colonne));
	}
	
	/**
	 * @param colonne
	 * @return
	 */
	public double getMedian( int colonne )
	{
		Udra calcul = copyFrom(this);
		double result = 0;
		calcul.orderDescBy( colonne );
		
		
		//si le udra a un nombre de ligne impaire
		if ( calcul.sizeRow() % 2 == 1)
		{
			result = calcul.getDbl(colonne, (int) (calcul.sizeRow() / 2));
		}
		else
		{
			double nb1 = calcul.getDbl(colonne, (int) (calcul.sizeRow() / 2 ));
			double nb2= calcul.getDbl(colonne, (int) (calcul.sizeRow() / 2 ) - 1);
			result = (nb1 + nb2) / 2;
		}
		
		
		
		return result;
	}
	/**
	 * @param colonne
	 * @return
	 */
	public double getEcartType( String colonne )
	{
		return getEcartType( get_the_index_of_title_from_his_Name(colonne));
	}
	
	/**
	 * @param colonne
	 * @return
	 */
	public double getEcartType( int colonne )
	{
		Udra calcul = copyFrom(this);
		double result = 0;

		double moyenne= calcul.getMoyenne(colonne);
		
		double variance = 0;
		for (int line = 0 ; line < calcul.sizeRow(); line++)
		{
			variance += (calcul.getDbl(colonne, line) - moyenne) * (calcul.getDbl(colonne, line) - moyenne);  
		}
		
		variance = variance /  calcul.sizeRow();
		
		return Math.sqrt(variance);
	}
	

	
	/**
	 * @param colonne
	 * @return
	 */
	public double getMoyenne( String colonne )
	{
		return getMoyenne( get_the_index_of_title_from_his_Name(colonne));
	}
	
	/**
	 * @param colonne
	 * @return
	 */
	public double getMoyenne( int colonne )
	{
		double result = 0;
		for (int i = 0 ; i < this.sizeRow() ; i++)
			result += this.getDbl(colonne, i);
		
		result = result / this.sizeRow();
		
		return result;
	}
	

	
	/**
	 * @param colonne , lignes oï¿½ faire la moyenne, en comptant ï¿½ partir du bas du tableau par dï¿½faut
	 * @return
	 */
	
	public double getMoyenne( String colonne , int periode)
	{
		return getMoyenne( get_the_index_of_title_from_his_Name(colonne)  , periode , true);
	}
	
	/**
	 * @param colonne
	 * @return
	 */
	public double getMoyenne( int colonne  , int periode )
	{
		return getMoyenne( colonne  , periode , true);
	}
	
	/**
	 * @param colonne , lignes oï¿½ faire la moyenne, en comptant ï¿½ partir du bas du tableau ?
	 * @return
	 */
	
	public double getMoyenne( String colonne , int periode , boolean fin)
	{
		return getMoyenne( get_the_index_of_title_from_his_Name(colonne)  , periode , fin);
	}
	
	/**
	 * @param colonne
	 * @return
	 */
	public double getMoyenne( int colonne  , int periode , boolean fin )
	{
		double result = 0;
		if ( fin )
		{
			for (int i = this.sizeRow() - periode ; i < this.sizeRow() ; i++)
				result += this.getDbl(colonne, i);
		}
		else
		{
			for (int i = 0 ; i < periode ; i++)
				result += this.getDbl(colonne, i);
		}
		
		result = result / periode;
		
		return result;
	}
	
	
	

	/**
	 * @param colonne , lignes oï¿½ faire la moyenne, en comptant ï¿½ partir du bas du tableau ?
	 * @return
	 */
	
	public double getMoyenne( String colonne ,  double first_line , double last_line)
	{
		return getMoyenne( get_the_index_of_title_from_his_Name(colonne)  , first_line , last_line);
	}
	
	/**
	 * @param colonne
	 * @return
	 */
	public double getMoyenne( int colonne  , double first_line , double last_line )
	{
		double result = 0;

		double f_line = ( first_line < last_line ) ? first_line : last_line;
		double l_line = ( first_line > last_line ) ? first_line : last_line;
		
		
			for (double i = f_line ; i < l_line ; i++)
				result += this.getDbl(colonne, (int)i);
		
		result = result / (l_line - f_line);
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////// Action on multiple udra ///////////////////////////////////////////
	
	
	//used to merge two udra
	/**
	 * @param second
	 * @param OptionofMerge
	 * @param CoupleOfColumn
	 * @throws Exception
	 */
	public void merge(Udra second , int OptionofMerge, String ... CoupleOfColumn) throws Exception
	{
		//Information : 
		//The optionofMerge allows the fuse of diffirent way, see the next list of value for more details 
		// 1 : merge all data if they can be fused they will are but if they they will stay all one into the list
		// 2 : Keep all data of current list but only the data that could be fused to the second list
		//3 : Keep only fused data
		//each couple need to be write under this format "title current udra|title second udra"  
		
		

		//add colomn of other udra
		for (int i = 0 ;  i < second.sizeColumn() ; i++)
			this.insertAColumn(second.getTitle().get(i));
		
		
		// analyse
		
		//cut they condition and use the number column to be faster
	ArrayList<ArrayList<Integer>> cond = new ArrayList();
		
		for (int i = 0 ; i < CoupleOfColumn.length ; i++)
		{

			ArrayList couple = new ArrayList();		
			String [] Condition = CoupleOfColumn[i].split("\\|");
			
			couple.add(this.getTitle().indexOf(Condition[0]));
			couple.add(second.getTitle().indexOf(Condition[1]));
		cond.add(couple);
		}
		
		

		//Join
	
		
		
		for (int i = 0 ; i < this.sizeRow() ; i++)
		{
			for (int j = 0 ; j < second.sizeRow() ; j++)
			{
				boolean valid = true;
				
				for (int k = 0 ; k < cond.size() ; k++)
				{
					
					
					//if the couple of value are different they line can't be merge
						if(!this.get(cond.get(k).get(0), i).equals(second.get(cond.get(k).get(1), j)))
							valid = false;

				}
				
				//check the validity of couple
				if (valid)
				{
					for (int col = 0 ; col < second.sizeColumn() ; col++)
					{
						this.setAvalue(second.getTitle().get(col), i , second.get(col, j));
					}
				}
				
			}
			
		}
		
		
		
		

		//Check uniq
		
		
		
		//if we need to copy all values also they doesn't present into they two document 
		if (OptionofMerge == 1)
		{
		
			
			
			
			//copy 
			for (int i = 0 ; i < second.sizeRow() ; i++)
			{
				boolean present = false;
				
				for (int j = 0 ; j < this.sizeRow() ; j++)
				{
					
					boolean valid = true;
					
					for (int k = 0 ; k < CoupleOfColumn.length ; k++)
					{
						if(!this.get(cond.get(k).get(0), j).equals(second.get(cond.get(k).get(1), i)))
								valid = false;
	
					}
					
					//check the validity of couple
					if (valid)
					{
						present = true;
					}
					
				}
				
				
				if (!present)
				{
					this.insertALine();
					
					
					for (int k = 0 ; k < CoupleOfColumn.length ; k++)
					{
						this.setAvalue(cond.get(k).get(0), this.sizeRow()-1, second.get(cond.get(k).get(1), i));
					}
					
					
					for (int col = 0 ; col < second.sizeColumn() ; col++)
					{
						this.setAvalue(second.getTitle().get(col), this.sizeRow()-1 , second.get(col, i));
					}
				}
			}
				

	}
		else if (OptionofMerge == 2)
		{
		
		
		}
		//Else if we don't want to copy value we will destroy the unique value
	else if(OptionofMerge == 3)
	{
		//stay to debug
		//This part is unstable
		/*
			for (int i = 0 ; i < this.SizeRow() ; i++)
			{
				boolean Present_Into_They_Two_Document = false;
				
				for (int j = 0 ; j < second.SizeColumn() ; j++)
				{
					if ((! (this.get(second.GetTitle().get(j) , i ) == null)) && (! ((String)(this.get(second.GetTitle().get(j) , i ))).equals("") ) )
						Present_Into_They_Two_Document = true;
					
				}
				
				if (!Present_Into_They_Two_Document)
					this.delete_row(i);
				
				
			}
			
			
		*/	
		}
	
		
		
		//cut the column used like referential of second udra to don't duplicate value
		for (int it = 0 ; it < CoupleOfColumn.length ; it++)
		{
			this.deleteColumn(CoupleOfColumn[it].split("\\|")[1]);
		}
	
	
		
	}
	
	
	
	
	

	

	

	
	/**
	 * @param seconds
	 * @param CoupleOfColumn
	 * @return
	 */
	public Udra difference(Udra seconds, String ... CoupleOfColumn)
	{
		return difference(seconds , true , CoupleOfColumn);
	}
	
	
	/**
	 * @param seconds
	 * @param IgnoreCast
	 * @param CoupleOfColumn
	 * @return
	 */
	public Udra difference(Udra seconds, boolean IgnoreCast , String ... CoupleOfColumn) //delete they line in the udra which are present also into the second udra 
	{
		//Information : 
		//each couple need to be write under this format "title current udra|title second udra"  
		// this udra = every line in this udra less line present in the two udra
		// return 			
			
		//En francais :
		//On supprime dans le udra actuel ( this ) les lignes qui sont en double dans le udra actuel et dans le second en fonction des colonnes matchï¿½es 
		//Le udra retournï¿½ par la fonction correspond au udra actuel aprï¿½s la fonction + le second udra
			
		Udra second = copyFrom(seconds);
		
			Udra Result = copyFrom(this);
			
			
		//analyse
		
		//cut they condition and use the number column to be faster
	ArrayList<ArrayList<Integer>> cond = new ArrayList();
		
	
	if (CoupleOfColumn.length > 0)
	{
		for (int i = 0 ; i < CoupleOfColumn.length ; i++)
		{

			ArrayList couple = new ArrayList();		
			String [] Condition = CoupleOfColumn[i].split("\\|");
			
			couple.add(this.getTitle().indexOf(Condition[0]));
			couple.add(second.getTitle().indexOf(Condition[1]));
		cond.add(couple);
		}
		
	}
	else //when they couple aren't define we take the default value
	{
		for (int i = 0 ; i < this.getTitle().size() ; i++)
		{
			ArrayList<Integer> arr = new ArrayList<Integer>();
			arr.add(i);
			arr.add(i);
			cond.add(arr);
		}
	}
		
// Join
	
		
		
		for (int i = 0 ; i < this.sizeRow() ; i++)
		{
			boolean ifind = false;
			for (int j = 0 ; j < second.sizeRow() ; j++)
			{
				boolean valid = true;
				
				for (int k = 0 ; k < cond.size() ; k++)
				{

					
					//if the couple of value are different they line can't be merge
					if(IgnoreCast)
					{
						if(!String.valueOf(this.get(cond.get(k).get(0), i)).equalsIgnoreCase(String.valueOf( second.get(cond.get(k).get(1), j) )))
							valid = false;
					}
					else
					{
						if(!this.get(cond.get(k).get(0), i).equals(second.get(cond.get(k).get(1), j)))
							valid = false;
					}
				}
					//check the validity of couple
					if (valid)
					{
							ifind = true;
							second.delete_row(j);
							j--;
						
					}
					
				}
				if (ifind == true)
				{
					this.delete_row(i);
					Result.delete_row(i);  
					i --;
				}
			}
			
	
			Result.fuse(second);
			return Result;
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//It's easyer to use to Merge but have less option
	/**
	 * @param second
	 */
	public void fuse(Udra second)
	{
		int it = 0;
		while(  this.sizeColumn() - second.sizeColumn() < 0 )
		{

				this.insertAColumn(String.valueOf( it));

			it ++;
		}
		
		
		
		for (int i = 0 ; i < second.sizeRow() ; i++)
		{
			try {
				this.insertALine();
			} catch (Exception e) {			}
			for (int j = 0 ; j < second.sizeColumn() ; j++)
			{
				this.setAvalue(j, this.sizeRow()-1, second.get(j, i));
			}
		}
			
			
	}
	
	
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////// Action on row////////////////////////////////////////////////////////
	
	/**
	 * @param newArray
	 * @throws Exception
	 */
	public void insertAnArrayList(ArrayList newArray) throws Exception //add a new line to the table
	{
		if (Title.size() == newArray.size())
		contentTable.add(newArray);
		else
			throw new Exception("Size of the new line is different of size of the Array");
			
	}
		
	
	/**
	 * @param index
	 * @return
	 */
	public ArrayList<Object> getALine(int index)
	{
		return contentTable.get(index);
	}
	
	
	/**
	 * @param Values
	 */
	public void insertALine(Object ... Values ) //insert attribute and the function while create new line
	{
		
		if (Values.length == Title.size())
		{
		contentTable.add(new ArrayList());
			for (Object obj : Values)
				contentTable.get(contentTable.size()-1).add(obj);
		}
		else if (Values.length == 0)
		{
			contentTable.add(new ArrayList());
			for (int i = 0 ; i < this.sizeColumn()  ; i++)
			{
				contentTable.get(this.sizeRow()-1).add("");
			}
		} else
			try {
				throw new Exception("Number of value are different of number of column");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

	/**
	 * @param RowNum
	 */
	public void delete_row(int RowNum)
	{
		contentTable.remove(RowNum);
	}
	
	//////////////////////////////////////////////////// Action on column ///////////////////////////////////////////////////

	/**
	 * @param Titles_To_Destroy
	 */
	public void deleteColumn ( String ... Titles_To_Destroy) // destroy the speculate column 
	{
		
		//if nothing is declare it's signifi destroy all column
		if (Titles_To_Destroy.length == 0)
		{	
			//delete all the udra
			clear();
		}
		
		else
		{
			ArrayList<String> Titles_To_Dest = new ArrayList<String>();
				
				for (String TitnotDest : Titles_To_Destroy)
					Titles_To_Dest.add(TitnotDest);
				
				int i = 0;
				
				while ( i < Title.size() )
				{			
					if (Titles_To_Dest.contains(Title.get(i)))
					 {
						Title.remove(i);
						for (int j = 0 ; j < contentTable.size() ; j++)
							if (i < contentTable.get(j).size())
								contentTable.get(j).remove(i);
					 }				
					else
					{
						i++;
					}
				}
		}
	}
	
	/**
	 * @param Titles_To_Destroy
	 */
	public void deleteColumn ( int indexOfColumnToDestroy) // destroy the speculate column 
	{
		
		Title.remove(indexOfColumnToDestroy);
		for (int j = 0 ; j < contentTable.size() ; j++)
			if (indexOfColumnToDestroy < contentTable.get(j).size())
				contentTable.get(j).remove(indexOfColumnToDestroy);
	}
	
	
	
	/**
	 * @param NameofNewColumn
	 * @param DefaultValue
	 */
	public void insertAColumn(String NameofNewColumn , Object ... DefaultValue)// insert a new column into the array
	{
		if ( ! Title.contains(NameofNewColumn) )
			Title.add(NameofNewColumn);
		else
			Title.add(NameofNewColumn + " - " + Title.lastIndexOf(NameofNewColumn));
			
		
		
		for (int i = 0 ; i < contentTable.size() ; i++)
		{
			if (DefaultValue != null)
			{
				if (DefaultValue.length > 0 )
					contentTable.get(i).add(DefaultValue[0]);
				else
					contentTable.get(i).add(null);
			}
			else
				contentTable.get(i).add(null);
		}
	}
	
	
	
	/**
	 * @param Titles_To_Not_Destroy
	 * @return
	 */
	public Udra keepOnly (String... Titles_To_Not_Destroy) // delete all the array without the speculate collumn, with undifined number of collumn
	{
		int[] Titles_To_Not_Dest_List = new int[Titles_To_Not_Destroy.length];
		
		for ( int i = 0 ; i < Titles_To_Not_Destroy.length ; i++)
			Titles_To_Not_Dest_List[i] = Title.indexOf(Titles_To_Not_Destroy[i]);

		return keepOnly(Titles_To_Not_Dest_List);
	}
	
	
	
	//reorganise the new udra to take a good form, the difference with the DrawingUdra's TakeOnly function is this function delet the original column
	/**
	 * @param NumColumn
	 * @return
	 */
	public Udra keepOnly(int ... NumColumn)
	{
		Udra UdraIn = Udra.copyFrom(this);
		this.deleteColumn();
		
		int ColumnActu = 0;
		for (int column : NumColumn)
		{
			try {
			if (column < 0 || column > UdraIn.getTitle().size())
				throw new Exception("A name or a number of column is invalid, column " + String.valueOf(column));
			
			this.insertAColumn(UdraIn.getTitle().get(column) );
			
			if (ColumnActu == 0)
			{
				for (int i = 0 ; i < UdraIn.sizeRow() ; i++)
					this.insertALine(UdraIn.get(column, i));
			}
			else
			{
				for (int i = 0 ; i < UdraIn.sizeRow() ; i++)
					this.setAvalue(ColumnActu, i, UdraIn.get(column, i));
			}
			
			ColumnActu ++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return this;
	}


	
	
	
	
	
	
	

	//***********************************************************************************************************************************************************************
	//* GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *** GRAPHIC *
	//***********************************************************************************************************************************************************************
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return Udra_Lib_Display.toString(this);
	}
	
	public String getContentString()
	{
		return Udra_Lib_Display.getContentString(this);
	}
	
	
	
	/**
	 * 
	 */
	public void print() // display the array to the console
	{
		Udra_Lib_Display.print(this);
	}
	
	
	
	/**
	 * 
	 */
	public void display()
	{
		Udra_Lib_Display.display(this);
	}
	
	
	
	
	/**
	 * @param TitleFrame
	 */
	public void display(final String TitleFrame) // display the array to a graphical user interface, it's possible to give a title to the array
	{
		Udra_Lib_Display.display(this, TitleFrame);
    }

	
	
	

	
	/**
	 * @param TitleFrame
	 */
	public void displayDynamic(final String TitleFrame) // display the array to a graphical user interface, it's possible to give a title to the array
	{
		Udra_Lib_Display.displayDynamic(this, TitleFrame);
        
    }
	
	

    public static int display_With_Modification_Ability_Get_id_NO_BUTTON(){ return Udra_Lib_Display_modif.getId_NO_BUTTON(); }
    public static int display_With_Modification_Ability_Get_id_ALL_BUTTON(){ return Udra_Lib_Display_modif.getId_ALL_BUTTON(); }
    public static int display_With_Modification_Ability_Get_id_Button_ADD_A_NEW_LINE() { return Udra_Lib_Display_modif.getIdBtn_ADD_A_NEW_LINE();}
    public static int display_With_Modification_Ability_Get_id_Button_ADD_A_NEW_COLUMN() { return Udra_Lib_Display_modif.getIdBtn_ADD_A_NEW_COLUMN();}
    

    
    /**
     * 
     */
    public void display_With_Modification_Ability() // display the Udra to a graphical user interface, to manually modified it (the program was stopped until the closed of the window)
    {
    	display_With_Modification_Ability(null);
    }

    
    /**
     * @param TitleFrame
     */
    public void display_With_Modification_Ability(String TitleFrame) // see below, add a title to the GUI
    {
    	display_With_Modification_Ability( TitleFrame, display_With_Modification_Ability_Get_id_ALL_BUTTON());
    }
    
    

    /**
     * @param TitleFrame
     */
    public void display_With_Modification_Ability(String TitleFrame, int buttonToDisplay) // see below, set which button should be display (all by defaults)
    {
    	display_With_Modification_Ability(TitleFrame, buttonToDisplay, 0);
    }
    
    
    

    /**
     * @param TitleFrame
     */
    public void display_With_Modification_Ability(String TitleFrame, int buttonToDisplay , int nmbColumnToFixe) // see below, set specifics column should be fixed (-1 : none by defaults)
    {
    	Udra_Lib_Display_modif.display_With_Modification_Ability(this, TitleFrame, buttonToDisplay, nmbColumnToFixe );
    }
    
	
		

		//***********************************************************************************************************************************************************************
	    //* DRAWING RELIEF MAP *** DRAWING RELIEF MAP *** DRAWING RELIEF MAP *** DRAWING RELIEF MAP *** DRAWING RELIEF MAP *** DRAWING RELIEF MAP *** DRAWING RELIEF MAP ***
		//***********************************************************************************************************************************************************************

	    /**
	     * The first column contains the name of each line others contains values
	     * @return
	     */
	    public Drawing_Relief_Map draw_relief_map()
	    {
	    	return draw_relief_map("#000000" , "#FFFFFF");
	    }

	    /**
	     * The first column contains the name of each line others contains values
	     * @return
	     */
	    public Drawing_Relief_Map draw_relief_map(String colorValueMin , String colorValueMax)
	    {
	    	Drawing_Relief_Map map = new Drawing_Relief_Map( copyFrom( this ) , colorValueMin , colorValueMax);
	    	return map;
	    }


		

		//***********************************************************************************************************************************************************************
	    //* DRAWING LIKE LINE *** DRAWING LIKE LINE *** DRAWING LIKE LINE *** DRAWING LIKE LINE *** DRAWING LIKE LINE *** DRAWING LIKE LINE *** DRAWING LIKE LINE *
		//***********************************************************************************************************************************************************************

	    /**
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line()
	    {
	    	Drawing_Line_Udra dl = new Drawing_Line_Udra();
	    	dl.add(this);
	    	return dl;
	    }

	    /**
	     * @param colInfo_complement
	     * @param colValue
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line( String colValeur)
	    {
	    	return draw_like_line( get_the_index_of_title_from_his_Name(colValeur) , get_the_index_of_title_from_his_Name(colValeur));
	    }

	    /**
	     * @param colInfo_complement
	     * @param colValeur
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line( int colValeur)
	    {
	    	Drawing_Line_Udra dl = new Drawing_Line_Udra();
	    	dl.add(this , colValeur , colValeur );
	    	return dl;
	    }
	    /**
	     * @param colInfo_complement
	     * @param colValue
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line( String colInfo_complement , String colValeur)
	    {
	    	return draw_like_line( get_the_index_of_title_from_his_Name(colInfo_complement) , get_the_index_of_title_from_his_Name(colValeur));
	    }

	    /**
	     * @param colInfo_complement
	     * @param colValeur
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line( int colInfo_complement , int colValeur)
	    {
	    	Drawing_Line_Udra dl = new Drawing_Line_Udra();
	    	dl.add(this , colInfo_complement , colValeur );
	    	return dl;
	    }

	    /**
	     * @param colInfo_complement
	     * @param colValeur
	     * @param colColor
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line( String colInfo_complement , String colValeur , String colColor)
	    {
	    	return draw_like_line( get_the_index_of_title_from_his_Name(colInfo_complement) , get_the_index_of_title_from_his_Name(colValeur) , get_the_index_of_title_from_his_Name(colColor));
	    }

	    /**
	     * @param colInfo_complement
	     * @param colValeur
	     * @param colColor
	     * @return
	     */
	    public Drawing_Line_Udra draw_like_line( int colInfo_complement , int colValeur , int colColor)
	    {
	    	Drawing_Line_Udra dl = new Drawing_Line_Udra();
	    	dl.add(this , colInfo_complement , colValeur , colColor);
	    	return dl;
	    }

		
	  //***********************************************************************************************************************************************************************

        //* DRAWING LIKE SQUARE *** DRAWING LIKE SQUARE *** DRAWING LIKE SQUARE *** DRAWING LIKE SQUARE *** DRAWING LIKE SQUARE *** DRAWING LIKE SQUARE *** DRAWING LIKE SQUARE *

    //***********************************************************************************************************************************************************************





              /**

               * @return

               */

              public Drawing_Square_Udra draw_like_square( )
              {

                 if ( sizeColumn() < 3)
                       return draw_like_square( Udra_Square_Panel.ColData , Udra_Square_Panel.ColValue  );

                 else
                       return draw_like_square( Udra_Square_Panel.ColData , Udra_Square_Panel.ColValue , Udra_Square_Panel.ColColor );


              }





             

              /**
               * @param col_data
               * @param col_val
               * @return
               */

              public Drawing_Square_Udra draw_like_square( String col_data , String col_val )
              {
                 return draw_like_square( get_the_index_of_title_from_his_Name(col_data)  , get_the_index_of_title_from_his_Name(col_val) );
              }

             



              /**
               * @param col_data
               * @param col_val
               * @return
               */

              public Drawing_Square_Udra draw_like_square( int col_data , int col_val )
              { 
                 return draw_like_square( col_data , col_val , null );
              }

             

              

              /**
               * @param col_data
               * @param col_val
               * @param col_color
               * @return
               */

              public Drawing_Square_Udra draw_like_square( String col_data , String col_val , String col_color)
              {
                 return draw_like_square( get_the_index_of_title_from_his_Name(col_data)  , get_the_index_of_title_from_his_Name(col_val)  , get_the_index_of_title_from_his_Name(col_color) );
              }

             
              

              /**
               * @param col_data
               * @param col_val
               * @param col_color
               * @return
               */

              public Drawing_Square_Udra draw_like_square( int col_data , int col_val , Integer col_color)
              {

                 //resultat
                 Drawing_Square_Udra draw = new Drawing_Square_Udra();



                 //active la couleur

                 if (draw_in_multi_color )
                 {
                       insertAColumn("Color");

                       for (int i = 0 ; i < sizeRow() ; i++)

                              setAvalue(sizeColumn() - 1, i, new Color( (int) ( Math.random() * 255 ), (int) ( Math.random() * 255 ), (int) ( Math.random() * 255 ) ) );

                       col_color = sizeColumn() - 1;

                 }


                 //udra servant ï¿½ l'animation

                 Udra tempo = Udra.copyFrom(this);


                 //efface les valeur du udra tempo

                 for (int i = 0 ; i < tempo.sizeRow() ; i++)

                       tempo.setAvalue(col_val, i, 0);


                 //recherche la valeur max

                 double valMax = 0;

                 for (int i = 0 ; i < sizeRow() ; i++)

                       if ( Math.abs( getDbl(col_val, i) ) > valMax)

                              valMax = Math.abs(getDbl(col_val, i));

                

                 draw.set_valMaxY(valMax);

                
                 //finit par envoyer les bonnes valeurs

                 if ( col_color != null )
                       draw.setUdra(this , col_data , col_val , col_color);

                 else
                       draw.setUdra(this , col_data , col_val);

                 if (draw_in_multi_color )
                 {

                       deleteColumn( getTitle().get( getTitle().size() - 1 ) );
                 }
                        return draw;

              }

             

              

              

              /**
               *
               */

              private boolean draw_in_multi_color = false;

             

              //fill the column with random color

              /**
               * @param active_color
               */

              public void set_draw_in_multi_Color( boolean active_color )
              {

                 draw_in_multi_color = active_color;

              }
		    
		    

		//***********************************************************************************************************************************************************************
	    //* DRAWING LIKE PIE *** DRAWING LIKE PIE *** DRAWING LIKE PIE *** DRAWING LIKE PIE *** DRAWING LIKE PIE *** DRAWING LIKE PIE *** DRAWING LIKE PIE *** DRAWING LIKE PIE *
		//***********************************************************************************************************************************************************************

		    
		    
		    /**
		     * @param column_data
		     * @param column_value
		     * @return
		     */
		    public Drawing_Pie_Udra draw_like_pie( String column_data , String column_value)
		    {
				return draw_like_pie( get_the_index_of_title_from_his_Name(column_data) , get_the_index_of_title_from_his_Name(column_value));
		    }
		    
		    
		    /**
		     * @param column_data
		     * @param column_value
		     * @return
		     */
		    public Drawing_Pie_Udra draw_like_pie( int column_data , int column_value)
		    {
				Drawing_Pie_Udra dp = new Drawing_Pie_Udra();
				dp.setUdra(this , column_data , column_value );
				return dp;
		    }

		    
		    
		    /**
		     * @param column_data
		     * @param column_value
		     * @param column_color
		     * @return
		     */
		    public Drawing_Pie_Udra draw_like_pie( String column_data , String column_value , String column_color)
		    {
				return draw_like_pie(get_the_index_of_title_from_his_Name(column_data) , get_the_index_of_title_from_his_Name(column_value) , get_the_index_of_title_from_his_Name(column_color));
		    }
		    
		    
		    /**
		     * @param column_data
		     * @param column_value
		     * @param column_color
		     * @return
		     */
		    public Drawing_Pie_Udra draw_like_pie( int column_data , int column_value , int column_color)
		    {
				Drawing_Pie_Udra dp = new Drawing_Pie_Udra();
				dp.setUdra(this , column_data , column_value , column_color);
				return dp;
		    }
		    
		    
		    
		    
		    /**
		     * @return
		     */
		    public Drawing_Pie_Udra draw_like_pie()
		    {
		    	
		    	if ( sizeColumn() == 2)
		    	{
		    		return draw_like_pie( Udra_Pie_Panel.ColData ,  Udra_Pie_Panel.ColValue);
		    	}
		    	
				Drawing_Pie_Udra dp = new Drawing_Pie_Udra();
				dp.setUdra(this);
				return dp;
		    }
		    
		    

	 //*********************************************************************************************************************************************************************
	 //***MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL****MAIL******
	 //*********************************************************************************************************************************************************************   
		    
			@Deprecated
			public void sendMail_GMail( String colEmetteur, String colPassword, String colDestinataire, String colObjet, String colMessage)
			{
				Udra_Lib_Mail.sendMail_GMail(this,colEmetteur ,  colPassword, colDestinataire, colObjet, colMessage);
			}  
			
			@Deprecated
			public void sendMail_GMail( int colEmetteur, int colPassword, int colDestinataire, int colObjet, int colMessage)
			{
				Udra_Lib_Mail.sendMail_GMail(this, colEmetteur, colPassword, colDestinataire, colObjet, colMessage);
			}
	
	 //*********************************************************************************************************************************************************************
	 //*INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET**INTERNET******
	 //*********************************************************************************************************************************************************************   
	    
   /**
 * @param URL
 * @return
 */
public String getData_from_Web_URL(String URL)
   {
	  return Udra_Lib_Internet.getData_from_Web_URL(URL);
   }
	    

/**
 * Recupere le contenu de la page passe en parametre dans un arrayList
* @param URL
* @return
*/
public ArrayList<String> getData_from_Web_URL_ArrayListString(String URL)
{
	return Udra_Lib_Internet.getData_from_Web_URL_ArrayListString(URL);
}

/**
 * @param Table
 * @throws Exception
 */
public String convertToHTMLTable () 
{
	return Udra_Lib_Internet.convertToHTMLTable(this);
}

	
	
	
	/**
	 * @param Request
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> request_to_Web_URL (String Request) throws Exception 
	{
		return Udra_Lib_Internet.request_to_Web_URL(this, Request);
	}
	
	
	
	

	//****************************************************************************************************************************************************************************
	//* DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *** DATABASE *****
	//****************************************************************************************************************************************************************************
	

	//identique a ReplaceRequest cette m�thode formate en plus les donnees specialement pour les requetes SQL
	//On lui passe en param�tre le num�ro de la ligne
	//et la methode retourne cette ligne format�e
	/**
	 * @param Request
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public String replaceRequestForSQL(String Request, int row) throws Exception
	{
		return Udra_Lib_BDD.replaceRequestForSQL(this, Request, row);
	}
	
	

	
	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @return
	 */
	public static Udra createFromSQLDatabase(String HostName , String Base , String User , String PassWord)
	{
		return Udra_Lib_BDD.createFromSQLDatabase( HostName, Base, User, PassWord);
	}
	
	
	
	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @param table
	 * @return
	 */
	public Udra createFromSQLTable(String HostName , String Base , String User , String PassWord , String table)
	{
		return Udra_Lib_BDD.createFromSQLTable( this , HostName ,  Base ,  User ,  PassWord ,  table);
	}
	
	public Udra createFromSQLTable(String HostName , String Base , String User , String PassWord , String table, String orderBy)
	{
		return Udra_Lib_BDD.createFromSQLTable( this , HostName ,  Base ,  User ,  PassWord ,  table, orderBy);
	}
	
	public Udra createFromSQLTable(String HostName , String Base , String User , String PassWord , String table, String orderBy, String sensOrder)
	{
		return Udra_Lib_BDD.createFromSQLTable( this , null, HostName ,  Base ,  User ,  PassWord ,  table, orderBy, sensOrder);
	}

	public Udra createFromSQLTableConfConnector(String confConnector , String User , String PassWord , String table)
	{
		return Udra_Lib_BDD.createFromSQLTable( this , confConnector, null ,  null ,  User ,  PassWord ,  table, null, null);
	}
	
	public Udra createFromSQLTableConfConnector(String confConnector , String User , String PassWord , String table, String orderBy, String sensOrder)
	{
		return Udra_Lib_BDD.createFromSQLTable( this , confConnector, null ,  null ,  User ,  PassWord ,  table, orderBy, sensOrder);
	}
	
	
	
	public Udra listeTablesOracle(String confConnector , String User , String PassWord )
	{
		return Udra_Lib_BDD.listeTablesOracle(this , confConnector, null ,  null ,  User ,  PassWord );
	}
	
	
	
	
	
	
	/**
	 * @param in
	 * @return
	 */
	private String formatDB(String in)
	{
		return Udra_Lib_BDD.formatDB(in);
	}
	
	
	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 */
	public static void clearDatabase(String HostName , String Base , String User , String PassWord)
	{
		Udra_Lib_BDD.clearDatabase( HostName, Base, User, PassWord);
	}
	
	

	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @return
	 */
	public boolean saveUdraInDataBase(String HostName , String Base , String User , String PassWord )
	{
		return Udra_Lib_BDD.saveUdraInDataBase(this, HostName, Base, User, PassWord );
	}
	
	
	
	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @param table
	 * @return
	 */
	public boolean saveUdraInDataBase(String HostName , String Base , String User , String PassWord , String table)
	{
		return Udra_Lib_BDD.saveUdraInDataBase(this, HostName, Base, User, PassWord , table);
	}	
	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @param table
	 * @return
	 */
	public boolean saveUdraInDataBase(String HostName , String Base , String User , String PassWord , String table , boolean dropOldTable)
	{
		return Udra_Lib_BDD.saveUdraInDataBase(this, HostName, Base, User, PassWord , table , dropOldTable);
	}
	

	/**
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @param table
	 * @return
	 */
	public boolean saveUdraInDataBase(String HostName , String Base , String User , String PassWord , String table , boolean dropOldTable, String idColonne )
	{
		return Udra_Lib_BDD.saveUdraInDataBase(this, HostName, Base, User, PassWord , table , dropOldTable, idColonne);
	}
	
	

	
	/**
	 * @param Request
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @return
	 */
	public boolean simpleQueryToSQLDatabase (String Request, String HostName , String Base , String User , String PassWord )  //send a request to a database
	{
		return Udra_Lib_BDD.simpleQueryToSQLDatabase(Request, HostName, Base, User, PassWord);
	}
	

	
	/**
	 * @param Request
	 * @return
	 */
	public Udra showRequestSended(String Request)
	{
		return Udra_Lib_BDD.showRequestSended(this, Request);
	}
	
	
	
	/**
	 * @param Request
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @return
	 */
	public boolean queryFromUdraToSQLDatabase (String Request, String HostName , String Base , String User , String PassWord )  //send a request to a database
	{
		return Udra_Lib_BDD.queryFromUdraToSQLDatabase(this, Request, HostName, Base, User, PassWord);
	}
	
	
	

	//***************************************************************************************************************************************************************************************************
	//*** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML *** XML ***
	//***************************************************************************************************************************************************************************************************
	
	public Udra createFromXML(String xml)
	{
		this.clear();
		return Udra_Lib_XML.createFromXML(this, xml);
	}
	

	
	//************************************************************************************************************************************************************************************************
	//*** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP *** SOAP ***
	//************************************************************************************************************************************************************************************************
	
	public Udra createFromSOAP(String url, String soapAction, String body) {
		this.clear();
		return Udra_Lib_SOAP.createFromSOAP(this, url, soapAction, body);
	}
	
	

	
	//******************************************************************************************************************************************************************************************************
	//*JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON**JSON*
	//******************************************************************************************************************************************************************************************************

	/**
	 * @param File Path
	 */
	public void createFromJSON_File ( String file_path )
	{
		Udra_Lib_JSON.createFromJSON_File(this, file_path);
	}

	/**
	 * @param URL
	 */
	public void createFromJSON_URL ( String URL )
	{
		Udra_Lib_JSON.createFromJSON_URL(this, URL);
	}
	
	
	/**
	 * @param JSON_String
	 */
	public void createFromJSON_String(String JSON_String)
	{
		Udra_Lib_JSON.createFromJSON_String(this, JSON_String);
	}
	
	public void saveAsJSon(String URLFile)
	{		
		try{
		    PrintWriter writer = new PrintWriter(URLFile, "UTF-8");
		    writer.println(toJSon());
		    writer.close();
		} catch (Exception e) {		}
	}

	public String toJSon()
	{
		String name = getName();
		if ( name.equals("") ) name = "Data";  
		
		String result = "{ \"" + name + "\" : [ \n";
		
			for (int line =  0 ; line < sizeRow() ; line++)
			{

				if (line > 0 )
					result += " , \n";
				
				result += "\t{\n";
				for (int col = 0 ; col < sizeColumn() ; col++)
				{
					if (col > 0 )
						result += " , \n";
						

					result += "\t\t";
					
					try
					{
						//Si le champ est un Udra on recupÃ¨re son contenu JSon
						 Udra val = Udra.copyFrom( (Udra) get( col , line) );
						 val.setName(getTitle().get(col));
						 String JSon =  val.toJSon();
						JSon = JSon.substring(1 , JSon.length() - 1);
						 result += JSon;
						 
					}catch (Exception e)
					{
						//si le champ n'est pas un chart on le convertit directement en String dans le Flux JSON
						result += "\"" +  getTitle().get(col) +  "\" : \"" + get( col , line) + "\"";
					}
					
					
				}
				result += "\n\t}";
			}
		
		result += "\n]}";
		return result;
	}
	
	
	

	//****************************************************************************************************************************************************************************************************
	//*CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV**CSV*
	//***************************************************************************************************************************************************************************************************
	
	
	/**
	 * @param URLFile
	 * @param deletePreviousFile
	 * @param defaultValue
	 * @return
	 */
	public boolean saveAsCSV(String URLFile , boolean deletePreviousFile , String ... defaultValue)
	{
		return Udra_Lib_CSV.saveAsCSV(this, URLFile, deletePreviousFile, defaultValue);
	}

	
	
	/**
	 * @param FileName
	 * @return
	 */
	public boolean createFromCSVFile(String FileName )
	{
		return Udra_Lib_CSV.createFromCSVFile(this, FileName);
	}
	
	
	/**
	 * @param FileName
	 * @return
	 */
	public void createFromCSV_HTTP_URL(String url )
	{
		Udra_Lib_CSV.createFromCSV_HTTP_URL(this, url);
	}
	
	
	
	/**
	 * @param textCSV
	 * @param udraName
	 * @return
	 */
	public boolean createFromCSV_ArrayString(ArrayList<String> textCSV , String ... udraName)
	{
		return Udra_Lib_CSV.createFromCSV_ArrayString(this, textCSV, udraName);
	}
		
	
	
	/**
	 * @param Folder
	 */
	public void create_Multi_udra_from_CSV_Folder(String Folder)
	{
		Udra_Lib_CSV.create_Multi_udra_from_CSV_Folder(this, Folder);
	}

	/**
	 * @param Folder
	 */
	public void createFromCSVFolder(String Folder) //create from CSV , this can content multiple CSV with different format
	{
		Udra_Lib_CSV.createFromCSVFolder(this, Folder);
	}

	/**
	 * @param Folder
	 */
	public void createFromCSVFolderInDistinctUdra(String Folder) //create from CSV , this can content multiple CSV with different format
	{
		Udra_Lib_CSV.createFromCSVFolderInDistinctUdra(this, Folder);
	}
	

	
	
	

	//****************************************************************************************************************************************************************************************************
	//*Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel**Excel*
	//****************************************************************************************************************************************************************************************************

	
	
	/**
	 * @param URLFile
	 * @param color
	 * @param defaultValue
	 * @return
	 */
	public boolean saveAsExcel(String URLFile , boolean color , String ... defaultValue)
	{
		return Udra_Lib_Excel.saveAsExcel(this, URLFile, color, defaultValue);
	}
	
	
	/**
	 * @param URLFile
	 * @param ColorHeader
	 * @param ColorBody
	 * @param defaultValue
	 * @return
	 */
	public boolean saveAsExcel(String URLFile , short ColorHeader , short ColorBody , String ... defaultValue)
	{
		
		return Udra_Lib_Excel.saveAsExcel(this, URLFile, ColorHeader, ColorBody, defaultValue);	
	}
	
	
	/**
	 * @param Folder
	 * @param firstRow
	 * @param firstCell
	 * @param lastRow
	 * @param lastCell
	 */
	public void create_Multi_udra_from_XLS_Folder(String Folder, int firstRow, int firstCell, int lastRow, int lastCell)
	{
		Udra_Lib_Excel.create_Multi_udra_from_XLS_Folder(this, Folder, firstRow, firstCell, lastRow, lastCell);	
	}
	
	
	/**
	 * @param fileURL
	 */
	public void createFromXLS(String fileURL )
	{
		Udra_Lib_Excel.createFromXLS(this, fileURL);
	}	
	
	
	/**
	 * @param fileURL
	 * @param defaultValue
	 */
	public void createFromXLS(String fileURL , Object defaultValue)
	{
		Udra_Lib_Excel.createFromXLS(this , fileURL , 0 , 0 , -1 , -1 , defaultValue);
	}
	
	
	/**
	 * @param fileURL
	 * @param FirstRow
	 * @param FirstCell
	 * @param LastRow
	 * @param LastCell
	 * @param defaultValue
	 */
	public void createFromXLS(String fileURL , int FirstRow , int FirstCell , int LastRow , int LastCell , Object ... defaultValue)
	{
		Udra_Lib_Excel.createFromXLS( this , fileURL, FirstRow, FirstCell, LastRow, LastCell, defaultValue);
	}




	//****************************************************************************************************************************************************************************************************
	//*BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP**BMP*
	//****************************************************************************************************************************************************************************************************

	public Udra convertToBlancExtra()
	{
		return Udra_BMP.convertToBlancExtra(this);
	}
	
	public Udra convertToNoiretBlanc()
	{
		return Udra_BMP.convertToNoiretBlanc(this);
	}
	

	public Udra convertToColorExtra()
	{
		return Udra_BMP.convertToColorExtra(this);
	}
	
	
	public Udra bmpLoadScreen()
	{
		Udra_BMP.loadScreen(this);
		return this;
	}

	public Udra bmpLoadFile(String sourceFile)
	{
		Udra_BMP.loadFile(this, sourceFile);
		return this;
	}

	public void bmpWriteFile(String destinationFile)
	{
		Udra_BMP.writeFile( this , destinationFile);
	}
	
	
	public FrameBMP bmpShow()
	{
		return new FrameBMP(this);
	}
	
	
	public FrameBMP bmpShow(boolean editMode)
	{
		return new FrameBMP(this , editMode);
	}

	
	public void bmpCompress(int compressLevel)
	{
		Udra_BMP.bmpCompress( this , compressLevel);
	}

	
	public Udra bmpSearchOn(Udra bigPicture)
	{
		return Udra_BMP.searchOn( this , bigPicture , 0 , 0 , bigPicture.sizeColumn() , bigPicture.sizeRow());
	}


	
	public Udra bmpSearchOn(Udra bigPicture , int xMin, int yMin, int larg , int haut )
	{
		return Udra_BMP.searchOn( this , bigPicture ,  xMin, yMin, larg , haut );
	}


	
	public Udra bmpSearchOnApproximation(Udra bigPicture , double tauxCorrespondanceNecessaire)
	{
		return Udra_BMP.searchOnApproximation( this , bigPicture , tauxCorrespondanceNecessaire , 0 , 0 , bigPicture.sizeColumn() , bigPicture.sizeRow());
	}


	
	public Udra bmpSearchOnApproximation(Udra bigPicture , double tauxCorrespondanceNecessaire ,  int xMin, int yMin, int larg , int haut )
	{
		return Udra_BMP.searchOnApproximation( this , bigPicture , tauxCorrespondanceNecessaire ,  xMin, yMin, larg , haut );
	}




	public Udra bmpRogner(int xmin, int ymin, int xmax, int ymax) {
		return Udra_BMP.rogner( this , xmin, ymin, xmax, ymax );
	}


	

	public void bmpDrawRect(  int x1 , int y1 , int x2 , int y2  )
	{
		Udra_BMP.drawRect( this , x1 , y1 , x2 , y2 , Color.black , 1);
	}
	

	public void bmpDrawRect(  int x1 , int y1 , int x2 , int y2 , Color color )
	{
		Udra_BMP.drawRect( this , x1 , y1 , x2 , y2 , color , 1);
	}
	

	public void bmpDrawRect(  int x1 , int y1 , int x2 , int y2 , Color color , int borderSize )
	{
		Udra_BMP.drawRect( this , x1 , y1 , x2 , y2 , color , borderSize);
	}
	
	
	public boolean bmpImageIdentique( Udra image2)
	{
		
		
		if ( sizeRow() != image2.sizeRow())
			return false;
		
		if ( sizeColumn() != image2.sizeColumn() )
			return false;
		
		for (int x = 0 ; x <  sizeColumn() ;x++)
		{
			for (int y = 0 ; y < sizeRow() ; y++) {
				
				if ( 	(( Color) get(x , y) ).getBlue() !=  (( Color) image2.get(x , y) ).getBlue() 
					|| 	(( Color) get(x , y) ).getGreen() !=  (( Color) image2.get(x , y) ).getGreen()
					||	(( Color) get(x , y) ).getRed() !=  (( Color) image2.get(x , y) ).getRed())

				return false;
				
			}
		}
		
		return true;
	}
	
}



