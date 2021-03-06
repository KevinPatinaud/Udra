package Library;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import oracle.jdbc.connector.*;

import udra.Udra;

public class Udra_Lib_BDD {

	//identique a ReplaceRequest cette methode formate en plus les donnees specialement pour les requetes SQL
	//On lui passe en parametre le numero de la ligne
	//et la methode retourne cette ligne formatee
	public static String replaceRequestForSQL( Udra udra_in , String Request, int row) throws Exception
	{

		//decoupe la requete initial suivant [ ] representant le nom des colonnes
		String [] TabReq = Request.split("\\[|\\]");
		
		//initialise la String de resultat
		String RequestReformed = ""; 
		
		//pour chaque bloque de la requete initial
		for (int i = 0 ; i < TabReq.length ; i++ )
		{
			
			//si le numero de bloc est pair, il s'agit d'un bloc inactif
			if (i%2 == 0)
				RequestReformed = RequestReformed + TabReq[i];
			else //sinon c'est un titre de colonne
			{
				if (udra_in.get( TabReq[i] , row) != null) //si la data n'est pas null, on la formate et on l'ajoute au resultat
					RequestReformed = RequestReformed + udra_in.get( TabReq[i] , row).toString().replace("'", "\'") ;
			}
		}
		return RequestReformed;
	}
	
	
	
	

	
	
	

	
	public static Udra createFromSQLDatabase( String HostName , String Base , String User , String PassWord)
	{
		/* connection a la base de donnees */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		Udra result = new Udra("Tables");
	
			try {
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );

		    
		    /* Creation de l'objet gerant les requetes */
		    Statement statement = connection.createStatement();

		    ResultSet resultQuery = statement.executeQuery( "SHOW tables;" );

		    //recupere le nom des tables
		    while ( resultQuery.next() ) {
		        String Table = resultQuery.getString(1);
		        Udra newUdra = new Udra();
		        
		        newUdra.setName(Table);
		        newUdra.createFromSQLTable(HostName, Base, User, PassWord, Table);
		        
		        result.insertALine(newUdra);    
		    }
		    
			} catch (SQLException e) {
				e.printStackTrace();
			}
		    		    
		    
		return result;
	}
	
	
	

	
	//par d�faut les resultats ne sont pas tri�
	public static Udra createFromSQLTable( Udra udra_in ,String HostName , String Base , String User , String PassWord , String Table)
	{
		return createFromSQLTable( udra_in , null, HostName ,  Base ,  User ,  PassWord ,  Table,  null, null);
	}

	//si l'on demande de trier les resultat, ceux-ci le sont par l'ordre ASC
	public static Udra createFromSQLTable( Udra udra_in ,String HostName , String Base , String User , String PassWord , String Table, String orderBy)
	{
		return createFromSQLTable( udra_in , null, HostName ,  Base ,  User ,  PassWord ,  Table,  orderBy, "ASC");
	}
	
	
	/**
	 * 
	 * @param udra_in
	 * @param confConnector : (optionnel) Si une configuration pr�cise est pass�e on l'utilise sinon on utilise la configuration par d�faut de MySQL <br/>Exemple Oracle : jdbc:oracle:thin:@URLServeur:Port:SID <br/>
	 * @param HostName : (optionnel si Oracle)
	 * @param Base : (optionnel si Oracle)
	 * @param User
	 * @param PassWord
	 * @param Table
	 * @param orderBy
	 * @param sensOrder ordre de tri
	 * @return
	 */

	
	public static Udra createFromSQLTable( Udra udra_in , String confConnector , String HostName , String Base , String User , String PassWord , String Table, String orderBy , String sensOrder)
	{
		/* configuration du connecteur,  */
		String configConnector = (confConnector == null) ? "jdbc:mysql://" + HostName + "/" + Base : confConnector;
	
		try {
			java.sql.Connection connection = DriverManager.getConnection( configConnector, User, PassWord );

		    		    
		    String requeteOderBy = "";
		    if ( sensOrder != null)
		    	requeteOderBy = " ORDER BY " + orderBy + " " + sensOrder;
		    
		    /* Execution de la requete */
		    Statement statement = connection.createStatement();
		    ResultSet ResultContentTable = statement.executeQuery( "SELECT * FROM  " + Table + requeteOderBy);

		    ResultSetMetaData rsmd = ResultContentTable.getMetaData();
		    int columnCount = rsmd.getColumnCount();

		    // G�n�re les colonnes
		    for (int i = 1; i <= columnCount; i++ ) {
		      udra_in.insertAColumn(rsmd.getColumnName(i));
		    }
		    
		    
		    //alimente les lignes
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
					e.printStackTrace();
				}
		    }
		    
		    
		    connection.close();
		    
		} catch ( SQLException e ) { 
			e.printStackTrace();
		}
		return udra_in;
	}
	
	

	
	public static Udra listeTablesOracle( Udra udra_in , String confConnector , String HostName , String Base , String User , String PassWord )
	{
		/* configuration du connecteur,  */
		String configConnector = (confConnector == null) ? "jdbc:mysql://" + HostName + "/" + Base : confConnector;
	
		try {
			java.sql.Connection connection = DriverManager.getConnection( configConnector, User, PassWord );

		    
		    /* Execution de la requete */
		    Statement statement = connection.createStatement();
		    ResultSet ResultContentTable = statement.executeQuery( "SELECT  * FROM  user_tables");

		    ResultSetMetaData rsmd = ResultContentTable.getMetaData();
		    int columnCount = rsmd.getColumnCount();

		    // G�n�re les colonnes
		    for (int i = 1; i <= columnCount; i++ ) {
		      udra_in.insertAColumn(rsmd.getColumnName(i));
		    }
		    
		    
		    //alimente les lignes
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
					e.printStackTrace();
				}
		    }
		    
		    
		} catch ( SQLException e ) { 
			e.printStackTrace();
		}
		return udra_in;
	}
	
	
	
	
	
	
	public  static String formatDB( String in)
	{
		return in.replaceAll("([^a-zA-Z0-9_\\s])","");
	}
	
	
	
	
	
	
	public static void clearDatabase( String HostName , String Base , String User , String PassWord)
	{
		//recupere le nom des tables
		/* connection a la base de donnees */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		ArrayList<Udra> result = new ArrayList<Udra>();
	
			try {
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );

		    
		    /* Creation de l'objet gerant les requetes */
		    Statement statement = connection.createStatement();

		    ResultSet resultQuery = statement.executeQuery( "SHOW tables;" );

		    //recupere le nom des tables
		    while ( resultQuery.next() ) {
		        String Table = resultQuery.getString(1);
		        //supprimme toutes les tables une a une
		        simpleQueryToSQLDatabase ("DROP TABLE IF EXISTS " + Table +";",  HostName ,  Base ,  User ,  PassWord );
				
		    }
			} catch (SQLException e) {
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
		return  saveUdraInDataBase(  udra_in ,HostName , Base , User , PassWord , table , dropOldTable, "id");
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
	 * @param idColonne : specifie la colonne utilis�e comme identifiant
	 * @return
	 */
	public static boolean saveUdraInDataBase(  Udra udra_in ,String HostName , String Base , String User , String PassWord , String table , boolean dropOldTable, String idColonne)
	{
		
		
		//if the table already exist in database, first we drop the old table
		if (dropOldTable)
		{
			simpleQueryToSQLDatabase ("DROP TABLE IF EXISTS " + table +";",  HostName ,  Base ,  User ,  PassWord );
		}
		
		//On envois une requete qui cr� la table si elle n'existait pas et ne fait rien si elle existe
		String createRequest = "CREATE TABLE IF NOT EXISTS " + table + " (  `" + idColonne + "` int(11) NOT NULL AUTO_INCREMENT,   ";
		for (int i = 0 ; i < udra_in.sizeColumn() ; i++)
		{
			//si la colonne id est pr�sente dans l'udra on ne la recr� pas 
			if ( ! udra_in.getTitle().get(i).equalsIgnoreCase(idColonne))
			{
					createRequest = createRequest + formatDB(udra_in.getTitle().get(i)) + " varchar(1024) ";
				if(i < udra_in.sizeColumn() - 1)
					createRequest = createRequest + " , ";
			}
		}
		createRequest = createRequest + " , PRIMARY KEY (`" + idColonne + "`),  UNIQUE KEY `id` (`" + idColonne + "`) );" ;
		simpleQueryToSQLDatabase (createRequest,  HostName ,  Base ,  User ,  PassWord );
		
		
		//And last we put all the udra into the database
		String ReqNameofColumn = "";
		
		for (int i = 0 ; i < udra_in.sizeColumn() ; i++)
		{
			ReqNameofColumn = ReqNameofColumn + "`" + formatDB(udra_in.getTitle().get(i)) + "`";
		if(i < udra_in.sizeColumn() - 1)
			ReqNameofColumn = ReqNameofColumn + " , ";
		}
		
		//on recupere le nom des colonnes pour former une requete qui sera interpretee par QueryFromUdraToSQLDatabase
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
		/* connection a la base de donnees */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
	
		try {

			Class.forName("com.mysql.jdbc.Driver");
			java.sql.Connection connection = DriverManager.getConnection( url, User, PassWord );
		    
		    
		    /* Creation de l'objet gerant les requetes */
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
		/* connection a la base de donnees */
		String url = "jdbc:mysql://" + HostName + "/" + Base;
		 boolean result = false;
	
		 java.sql.Connection connection;
		 
		try {

			Class.forName("com.mysql.jdbc.Driver");
			
			connection = DriverManager.getConnection( url, User, PassWord );
		    
		    
		    /* Creation de l'objet gerant les requetes */
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
		    
		} catch ( Exception e ) { 
			e.printStackTrace();
			return false;
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
}
