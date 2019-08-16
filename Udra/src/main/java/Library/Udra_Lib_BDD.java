package Library;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import udra.Udra;

public class Udra_Lib_BDD {

	//identique ï¿½ ReplaceRequest cette mï¿½thode formate en plus les donnï¿½es spï¿½cialement pour les requï¿½tes SQL
	//On lui passe en paramï¿½tre le numï¿½ro de la ligne
	//et la mï¿½thode retourne cette ligne formatï¿½e
	public static String replaceRequestForSQL( Udra udra_in , String Request, int row) throws Exception
	{

		//dï¿½coupe la requï¿½te initial suivant [ ] reprï¿½sentant le nom des colonnes
		String [] TabReq = Request.split("\\[|\\]");
		
		//initialise la String de rï¿½sultat
		String RequestReformed = ""; 
		
		//pour chaque bloque de la requï¿½te initial
		for (int i = 0 ; i < TabReq.length ; i++ )
		{
			
			//si le numï¿½ro de bloc est pair, il s'agit d'un bloc inactif
			if (i%2 == 0)
				RequestReformed = RequestReformed + TabReq[i];
			else //sinon c'est un titre de colonne
			{
				if (udra_in.get( TabReq[i] , row) != null) //si la data n'est pas null, on la formate et on l'ajoute au rï¿½sultat
					RequestReformed = RequestReformed + udra_in.get( TabReq[i] , row).toString().replace("'", "\'") ;
			}
		}
		return RequestReformed;
	}
	
	
	
	

	
	
	

	
	public static Udra createFromSQLDatabase( String HostName , String Base , String User , String PassWord)
	{
		/* connection ï¿½ la base de donnï¿½es */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		Udra result = new Udra("Tables");
	
			try {
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );

		    
		    /* Crï¿½ation de l'objet gï¿½rant les requï¿½tes */
		    Statement statement = connection.createStatement();

		    ResultSet resultQuery = statement.executeQuery( "SHOW tables;" );

		    //recupï¿½re le nom des tables
		    while ( resultQuery.next() ) {
		        String Table = resultQuery.getString(1);
		        Udra newUdra = new Udra();
		        
		        newUdra.setName(Table);
		        newUdra.createFromSQLTable(HostName, Base, User, PassWord, Table);
		        
		        
		        result.insertALine(newUdra);
		        
		    }
		    
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    		    
		    
		return result;
	}
	
	
	

	
	
	public static Udra createFromSQLTable( Udra udra_in ,String HostName , String Base , String User , String PassWord , String Table)
	{
		return createFromSQLTable( udra_in , HostName ,  Base ,  User ,  PassWord ,  Table,  null, null);
	}


	public static Udra createFromSQLTable( Udra udra_in ,String HostName , String Base , String User , String PassWord , String Table, String orderBy)
	{
		return createFromSQLTable( udra_in , HostName ,  Base ,  User ,  PassWord ,  Table,  orderBy, "ASC");
	}
	
	public static Udra createFromSQLTable( Udra udra_in ,String HostName , String Base , String User , String PassWord , String Table, String orderBy , String sensOrder)
	{
		/* connection ï¿½ la base de donnï¿½es */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		try {
			
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );
		    
		    
		    /* Crï¿½ation de l'objet gï¿½rant les requï¿½tes */
		    Statement statement = connection.createStatement();

		    ResultSet result = statement.executeQuery( "SHOW COLUMNS FROM  `" + Table + "`" );

		    while ( result.next() ) {
		        String Colonne = result.getString( "Field" );
		        udra_in.getTitle().add(Colonne);
		    }
		    		    
		    String requeteOderBy = "";
		    if ( sensOrder != null)
		    	requeteOderBy = " ORDER BY " + orderBy + " ASC";
		    
		    ResultSet ResultContentTable = statement.executeQuery( "SELECT * FROM  `" + Table + "`"  + requeteOderBy);

		    while ( ResultContentTable.next() ) {
		    	
		    	ArrayList<String> Line = new ArrayList<String>(); 
		    	
		    	for (int i = 0 ; i < udra_in.getTitle().size() ; i++)
			    {
			        String Case = ResultContentTable.getString( udra_in.getTitle().get(i) );
			        Line.add(Case);
			    }
		    	try {
					udra_in.insertAnArrayList(Line);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    
		} catch ( SQLException e ) { 
			System.out.print(e);
		}
		return udra_in;
	}
	
	
	
	
	
	
	public  static String formatDB( String in)
	{
		return in.replaceAll("([^a-zA-Z0-9_\\s])","");
	}
	
	
	
	
	
	
	public static void clearDatabase( String HostName , String Base , String User , String PassWord)
	{
		//rï¿½cupï¿½re le nom des tables
		/* connection ï¿½ la base de donnï¿½es */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		ArrayList<Udra> result = new ArrayList<Udra>();
	
			try {
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );

		    
		    /* Crï¿½ation de l'objet gï¿½rant les requï¿½tes */
		    Statement statement = connection.createStatement();

		    ResultSet resultQuery = statement.executeQuery( "SHOW tables;" );

		    //recupï¿½re le nom des tables
		    while ( resultQuery.next() ) {
		        String Table = resultQuery.getString(1);
		        //supprimme toutes les tables une ï¿½ une
		        simpleQueryToSQLDatabase ("DROP TABLE IF EXISTS " + Table +";",  HostName ,  Base ,  User ,  PassWord );
				
		    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	
	

	public static boolean saveUdraInDataBase( Udra udra_in ,String HostName , String Base , String User , String PassWord )
	{
		return saveUdraInDataBase( udra_in , HostName ,  Base ,  User ,  PassWord ,  formatDB(udra_in.getName()));
	}
	
	
	
	
	

	public static boolean saveUdraInDataBase(  Udra udra_in ,String HostName , String Base , String User , String PassWord , String table)
	{
		return saveUdraInDataBase(  udra_in , HostName , Base , User , PassWord , table , true);
	}
	
	
	/**
	 * 
	 * Permet d'enregistrer un tableau Udra en base
	 * 
	 * @param udra_in
	 * @param HostName
	 * @param Base
	 * @param User
	 * @param PassWord
	 * @param table
	 * @param dropOldTable
	 * @return
	 */
	public static boolean saveUdraInDataBase(  Udra udra_in ,String HostName , String Base , String User , String PassWord , String table , boolean dropOldTable)
	{
		
		
		//if the table already exist in database, first we drop the old table
		if (dropOldTable)
		{
			simpleQueryToSQLDatabase ("DROP TABLE IF EXISTS " + table +";",  HostName ,  Base ,  User ,  PassWord );
		}
		
		//On envois une requete qui cré la table si elle n'existait pas et ne fait rien si elle existe
		String createRequest = "CREATE TABLE IF NOT EXISTS " + table + " (  `id` int(11) NOT NULL AUTO_INCREMENT,   ";
		for (int i = 0 ; i < udra_in.sizeColumn() ; i++)
		{
			//si la colonne id est présente dans l'udra on ne la recré pas 
			if ( ! udra_in.getTitle().get(i).equalsIgnoreCase("id"))
			{
					createRequest = createRequest + formatDB(udra_in.getTitle().get(i)) + " varchar(1024) ";
				if(i < udra_in.sizeColumn() - 1)
					createRequest = createRequest + " , ";
			}
		}
		createRequest = createRequest + " , PRIMARY KEY (`id`),  UNIQUE KEY `id` (`id`) );" ;
		simpleQueryToSQLDatabase (createRequest,  HostName ,  Base ,  User ,  PassWord );
		
		
		//And last we put all the udra into the database
		String ReqNameofColumn = "";
		
		for (int i = 0 ; i < udra_in.sizeColumn() ; i++)
		{
			ReqNameofColumn = ReqNameofColumn + "`" + formatDB(udra_in.getTitle().get(i)) + "`";
		if(i < udra_in.sizeColumn() - 1)
			ReqNameofColumn = ReqNameofColumn + " , ";
		}
		
		//on recupere le nom des colonnes pour former une requï¿½te qui sera interprï¿½tï¿½e par QueryFromUdraToSQLDatabase
		String ReqDataofColumn = "";
		
		for (int i = 0 ; i < udra_in.sizeColumn() ; i++)
		{
			ReqDataofColumn = ReqDataofColumn + "'[" + udra_in.getTitle().get(i) + "]'";
		if(i < udra_in.sizeColumn() - 1)
			ReqDataofColumn = ReqDataofColumn + " , ";
		}
		
		
		
		queryFromUdraToSQLDatabase( udra_in , "INSERT INTO `" + table + "` (" + ReqNameofColumn + ") VALUES (" + ReqDataofColumn + ");",  HostName ,  Base ,  User ,  PassWord );
		
		
		return true;
	}
	
	
	
	
	
	
	
	
	

	
	public static boolean simpleQueryToSQLDatabase ( String Request, String HostName , String Base , String User , String PassWord )  //send a request to a database
	{
		/* connection ï¿½ la base de donnï¿½es */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		try {

			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );
		    
		    
		    /* Crï¿½ation de l'objet gï¿½rant les requï¿½tes */
		    Statement statement = connection.createStatement();

		    statement.execute(Request );

		} catch ( Exception e ) { 
			System.out.print(e);
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	public static Udra showRequestSended( Udra udra_in , String Request)
	{
		Udra result = new Udra("request");
		   
					for (int line = 0 ; line < udra_in.sizeRow()  ; line ++ )
					{
						String RequestReformed = "";
						try
						{
							//on forme la requete
							RequestReformed = replaceRequestForSQL( udra_in , Request , line);
							
						 result.insertALine(RequestReformed);
						 
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return result;
	}
	
	
	
	
	
	
	
	public static boolean queryFromUdraToSQLDatabase ( Udra udra_in , String Request, String HostName , String Base , String User , String PassWord )  //send a request to a database
	{
		/* connection ï¿½ la base de donnï¿½es */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
		 boolean result = false;
	
		 java.sql.Connection connection;
		 
		try {

			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection( url, User, PassWord );
		    
		    
		    /* Crï¿½ation de l'objet gï¿½rant les requï¿½tes */
		    Statement statement = connection.createStatement();

		   

			String RequestReformed = "";
		    
			for (int line = 0 ; line < udra_in.sizeRow()  ; line ++ )
			{
				try
				{
					//on forme la requete
					RequestReformed = replaceRequestForSQL( udra_in , Request , line);

					result = statement.execute(RequestReformed );
				}catch (Exception e) {		e.printStackTrace();		}
				
			}
		    
		} catch ( Exception e ) { System.out.print(e);return false;}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
}
