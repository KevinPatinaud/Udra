package Library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import udra.Udra;

public class Udra_Lib_Internet {


	
	   public static String getData_from_Web_URL(String URL)
	   {
		   boolean ok = false;
		   
		   //essais maximum 5 fois de récuperer les données du site
		   for (int i = 0 ; i < 5 && ! ok ; i++)
		   {
			   try{
			   //on récupère le contenu HTML de la page web
				String ContenuPageHTML = "";
			    String s;
			    
			    //donne un user agent ce qui permet de faire sauter la sécurité de certains sites
			    System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36"); 
			    BufferedReader r = new BufferedReader(new InputStreamReader(new URL(URL).openStream()));
			  
				while ((s = r.readLine()) != null) {
					ContenuPageHTML = ContenuPageHTML + s;
				}
				
				//quitte la fonction si les données sont valides
				ok = true;
				return ContenuPageHTML;
				
			   }catch(Exception e){
				   e.printStackTrace();
				   try {	Thread.sleep(1000);		} catch (InterruptedException e1) {	e1.printStackTrace();	} //chaque echec est espacé d'une seconde
			   }
		   }
		   
		   return null;
	   }
	   
	   
	

		
	   public static ArrayList<String>  getData_from_Web_URL_ArrayListString(String URL)
	   {
		   try{
		   //on récupère le contenu HTML de la page web
			 ArrayList<String> ContenuPageHTML = new ArrayList<String>();
		    String s;
		    
		    //donne un user agent ce qui permet de faire sauter la sécurité de certains sites
		    System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36"); 
		    BufferedReader r = new BufferedReader(new InputStreamReader(new URL(URL).openStream()));
		  
		    
			while ((s = r.readLine()) != null) {
				ContenuPageHTML.add(s);
			}
			
			return ContenuPageHTML;
			
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   
		   return null;
	   }
	   
	   
	
	
	
	public static ArrayList<String> request_to_Web_URL ( Udra udra_in , String Request) throws Exception 
	{
		
		ArrayList<String> ResultTab = new ArrayList<String>();
		
		
		for (int line = 0 ; line < udra_in.sizeRow()  ; line ++ )
		{

			String RequestReformed = udra_in.replaceRequest(Request , line);
			

			RequestReformed = RequestReformed.replaceAll("\\s", "%20");
			
			//connection à l'URL
	
			String Result = "";
						try {
						URL	monURL = new URL(RequestReformed);
			
					//	System.out.println (RequestReformed) ;
						
						
						URLConnection connection = monURL.openConnection();
						InputStream flux = connection.getInputStream();
						
						int donneesALire = connection.getContentLength();

						for(;donneesALire != 0; donneesALire--)
							Result = Result + (char)flux.read();

						// Fermeture de la connection
						flux.close();
						
				
						} catch (MalformedURLException e) {						} catch (IOException e) {						}
		
		
		
						ResultTab.add(Result);
		
		
		}
		
		return ResultTab;
		
	}
	
	
	
	
	
	

	public static String convertToHTMLTable ( Udra udra_in )  
	{
		String tableauResult = "<table>";
		
		//copie les titres
		tableauResult += "<tr>";
		for (int i = 0 ; i < udra_in.sizeColumn() ; i++)
		{
			tableauResult += "<th>" + udra_in.getTitle().get(i) + "</th>";
		}
		tableauResult += "</tr>";
	  
		//copie les données
		for (int iligne = 0 ; iligne < udra_in.sizeRow() ; iligne++)
		{
			tableauResult += "<tr>";
			for (int icolonne = 0 ; icolonne < udra_in.sizeColumn() ; icolonne++)
			{
				tableauResult += "<td>";
				
				if (udra_in.isNumber(icolonne , iligne ))
				{
					//si il a y un nombre après la virgule on prend sous forme de double sinon de int
					if (udra_in.getDbl(icolonne , iligne ) != udra_in.getDbl(icolonne , iligne ).intValue())
					{
						tableauResult += udra_in.getDbl(icolonne , iligne );
					}
					else
					{
						tableauResult += udra_in.getDbl(icolonne , iligne ).intValue();
					}
					
				}
				else
				{
					if( udra_in.get(icolonne , iligne ) != null)
						tableauResult += udra_in.get(icolonne , iligne );
				}
				

				tableauResult += "</td>";
			}
			tableauResult += "</tr>";
		}
		
		
		tableauResult += "</table>";
		
		return tableauResult;
		
	}
	
	
	
	
	
	
	
	
}
