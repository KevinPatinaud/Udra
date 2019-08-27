package Library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map.Entry;

import udra.Udra;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Udra_Lib_JSON {

	private static final JsonArray Paths = null;





	public static void createFromJSON_File ( Udra udra_in , String file )
	{
		try {
			
			BufferedReader Buffer = null;
		    String line, content = "";
		    
		    try
		      {
		    	Buffer = new BufferedReader(new FileReader(file));
			    while ((line = Buffer.readLine()) != null)
			    	content += line;
			    Buffer.close();		    
		      }catch(Exception e){}
			
			createFromJSON_String( udra_in , content);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createFromJSON_URL ( Udra udra_in , String URL )
	{
		//test maximum 5 fois de récuperer des données valides de la part du serveur dans le cas on le serveur présente des difficultées de connexion
		boolean ok = false;
		int iCompteur = 0;
		while( ! ok && iCompteur < 5)
		{
			try {
				String content = udra_in.getData_from_Web_URL( URL );
				createFromJSON_String( udra_in , content);
				ok = true;
			} catch (Exception e) {
				e.printStackTrace();
				try {		Thread.sleep(1000);		} catch (InterruptedException e1) {	e1.printStackTrace();	} //espace chaque echec d'une seconde
			}
			iCompteur ++;
		}
	}
	
	
	public  static void createFromJSON_String(Udra udra_in , String JSON_String)
	{
		
		//clear the udra
		udra_in.clear();
		udra_in.insertALine();
		
		JsonObject jsonObject = null;
		
		//certain JSon commence avec le caractère [ on est obligé de l'encapsuler avant de traiter le Json
		if (JSON_String.charAt(0) == '[')
			jsonObject = new JsonParser().parse("{\"data\" : " +  JSON_String + "}").getAsJsonObject();
		else
			jsonObject = new JsonParser().parse( JSON_String ).getAsJsonObject();
		
		Iterator iterator = jsonObject.entrySet().iterator();

		//parcours chaque ligne
		while ( iterator.hasNext() )
		{
			
			Entry<String , JsonElement> actu = (Entry<String, JsonElement>) iterator.next();
			String colonne =  actu.getKey();
			JsonElement jsonElement = actu.getValue();

			udra_in.insertAColumn(colonne);
			
			if ( jsonElement.isJsonArray() )
			{
				udra_in.setAvalue(colonne, 0, getUdraFromJsonArray( udra_in ,  jsonElement.getAsJsonArray() , colonne) );
			}
			else
			{
				if ( ! jsonElement.isJsonNull() && jsonElement != null)
				{
					if ( !jsonElement.isJsonPrimitive() )
					{
						Udra data = new Udra();
						 createFromJSON_String(data , jsonElement.toString());
						udra_in.setAvalue(colonne, 0,data);
					}
					else
					{
						udra_in.setAvalue(colonne, 0, jsonElement.getAsString());
					}
				}
			}
			
		} 
		
		
	}
	
	
	
	
	
	public static Udra getUdraFromJsonArray( Udra udra_in , JsonArray jsonArray, String colonneParent)
	{
		
		Udra nouvUdra = new Udra();
		
		for (int i = 0 ; i < jsonArray.size() ; i++)
		{
			nouvUdra.insertALine();
			
			//si l'élément est un objet JSON, alors on le récupère
			//exemple : "BOUCHON_BDD": "TRUE",
			
			if ( jsonArray.get(i).isJsonObject())
			{
				JsonObject jsObject = jsonArray.get(i).getAsJsonObject();
				
				
				Iterator iterator = jsObject.entrySet().iterator();
				
				while ( iterator.hasNext() )
				{
					Entry<String , JsonElement> actu = (Entry<String, JsonElement>) iterator.next();
					String colonne =  actu.getKey();
					JsonElement jsonElement = actu.getValue();
					
					//if the colonne doesn't exist 
					if ( nouvUdra.get_the_index_of_title_from_his_Name(colonne) == -1)
					{
						nouvUdra.insertAColumn(colonne);
					}
					
					
					if ( jsonElement.isJsonArray() )
					{
						nouvUdra.setAvalue(colonne, i, getUdraFromJsonArray( udra_in , jsonElement.getAsJsonArray() , colonne) );
					}
					else
					{
						if ( ! jsonElement.isJsonNull() && jsonElement != null)
						{
							if ( !jsonElement.isJsonPrimitive() )
							{
								// !!! Certains élément ne sont pas traités
							//	System.out.println(jsonElement);
							}
							else
							{
								nouvUdra.setAvalue(colonne, i, jsonElement.getAsString());
							}
						}
					}
				}
			}
			
			//si lélément est un tableau JSON, exemple ""VALEURS_TRADEES" : ["ETH", "BTC"],"
			else if ( jsonArray.get(i).isJsonPrimitive())
			{
				String nomColonne = colonneParent;
				if ( nouvUdra.get_the_index_of_title_from_his_Name(nomColonne) < 0)
					nouvUdra.insertAColumn(nomColonne);
				
				nouvUdra.setAvalue(nomColonne , nouvUdra.sizeRow() - 1 , jsonArray.get(i).getAsJsonPrimitive().getAsString());
				
			}
			
			
		}
		
		return nouvUdra;
	}
	
	
	
	
}
